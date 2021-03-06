package com.enjin.coin.sdk.service.notifications.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import com.enjin.coin.sdk.config.Config;
import com.enjin.coin.sdk.config.Notification;
import com.enjin.coin.sdk.enums.NotificationType;
import com.enjin.coin.sdk.service.BaseService;
import com.enjin.coin.sdk.service.notifications.EventMatcher;
import com.enjin.coin.sdk.service.notifications.NotificationListener;
import com.enjin.coin.sdk.service.notifications.NotificationListenerRegistration;
import com.enjin.coin.sdk.service.notifications.NotificationsService;
import com.enjin.coin.sdk.service.notifications.ThirdPartyNotificationService;
import com.enjin.coin.sdk.service.platform.PlatformService;
import com.enjin.coin.sdk.util.BooleanUtils;
import com.enjin.coin.sdk.util.ObjectUtils;
import com.enjin.coin.sdk.util.StringUtils;
import com.enjin.coin.sdk.vo.platform.GetPlatformAuthDetailsResponseVO;
import com.enjin.coin.sdk.vo.platform.GetPlatformAuthRequestVO;
import com.enjin.coin.sdk.vo.platform.GetPlatformAuthResponseVO;
import com.enjin.coin.sdk.vo.platform.ImmutableGetPlatformAuthRequestVO;

/**
 * <p>
 * NotificationsService - Synchronous.
 * </p>
 */
public class NotificationsServiceImpl extends BaseService implements NotificationsService {

    /**
     * Logger used by this class.
     */
    private static final Logger LOGGER = Logger.getLogger(NotificationsServiceImpl.class.getName());

    /**
     * Local variable for the third party notification service.
     */
    private ThirdPartyNotificationService thirdPartyNotificationService;

    /**
     * Local variable holding all the notification listeners.
     */
    private List<NotificationListenerRegistration> notificationListeners = new ArrayList<>();

    /**
     * Local variable for the platformService.
     */
    private PlatformService platformService;

    /**
     * Local config variable.
     */
    private Config config;

    /**
     * Local auth variable.
     */
    private String auth;

    /**
     * Class constructor.
     *
     * @param config - the config to use
     */
    public NotificationsServiceImpl(final Config config) {
        super(config);
        this.config = config;
    }

    /**
     * Method to initialize the notifications service.
     *
     * @return boolean
     */
    @Override
    public boolean initNotificationsService(final String auth) {
        this.auth = auth;

        //Call out to the reinitialize method in order to initialize the pusher notifications
        return reInitNotificationsService();
    }


    /**
     * Method to re-initialize the notifications service.
     *
     * @return boolean
     */
    @Override
    public boolean reInitNotificationsService() {
        boolean initResult = false;


        if (StringUtils.isEmpty(this.auth)) {
            LOGGER.warning("auth cannot be null or empty");
            return initResult;
        }

        platformService = getPlatformService(config);

        GetPlatformAuthRequestVO platformAuthRequestVO = ImmutableGetPlatformAuthRequestVO.builder()
                .setAuth(auth)
                .build();

        GetPlatformAuthResponseVO platformAuthResponseVO = platformService.getAuth(platformAuthRequestVO);
        if (platformAuthResponseVO == null || BooleanUtils.isFalse(platformAuthResponseVO.getPlatformAuthNotificationDetails().isPresent())) {
            LOGGER.warning("Returned getPlatformAuthResponseVO is null or notificationDetails is not present");
            return initResult;
        }

        Optional<GetPlatformAuthDetailsResponseVO> platformAuthDetailsResponseVOOptional = platformAuthResponseVO.getPlatformAuthNotificationDetails();
        GetPlatformAuthDetailsResponseVO platformAuthDetailsResponseVO = platformAuthDetailsResponseVOOptional.get();

        // Setup the thirdPartyNotificationService to use the pusher service.
        if (thirdPartyNotificationService == null) {
            thirdPartyNotificationService = new PusherNotificationServiceImpl(new Notification());
        }

        boolean initPusherResult = thirdPartyNotificationService.initializeNotificationService(platformAuthDetailsResponseVO);
        if (BooleanUtils.isNotTrue(initPusherResult)) {
            LOGGER.warning("A problem occured initializing the pusher library");
            return initResult;
        }

        return initPusherResult;
    }

    /**
     * Method to configure a listener.
     *
     * @param listener notificationListener to configer
     * @return NotificationListenerRegistration
     */
    @SuppressWarnings("rawtypes")
    @Override
    public NotificationListenerRegistration.RegistrationListenerConfiguration configureListener(final NotificationListener listener) {
        return NotificationListenerRegistration.configure(this, listener);
    }

    /**
     * Method to add a notification listener.
     *
     * @param listener - listener to add
     * @return NotificationListenerRegistration
     */
    @Override
    public synchronized NotificationListenerRegistration addNotificationListener(final NotificationListener listener) {
        NotificationListenerRegistration registration = null;
        if (ObjectUtils.isNull(listener)) {
            LOGGER.warning("Could not add a NotificationListener because it was null.");
            return null;
        } else {
            long count = notificationListeners.stream().filter(r -> r.getListener() == listener).count();

            if (count == 0) {
                registration = NotificationListenerRegistration.configure(this, listener).register();
            } else {
                LOGGER.warning("Could not add a NotificationListener because it was already registered.");
            }
        }
        return registration;
    }

    /**
     * Method to add a notification listener.
     *
     * @param listener the listener to add
     * @param eventMatcher to match against
     * @return NotificationListenerRegistration
     */
    @Override
    public NotificationListenerRegistration addNotificationListener(final NotificationListener listener, final EventMatcher eventMatcher) {
        return configureListener(listener).withMatcher(eventMatcher).register();
    }

    /**
     * Method to configure a listener for allowed types.
     *
     * @param listener to configure
     * @param allowed types
     * @return NotificationListenerRegistration
     */
    @Override
    public NotificationListenerRegistration addAllowedTypesNotificationListener(final NotificationListener listener, final NotificationType... allowed) {
        return configureListener(listener).withAllowedEvents(allowed).register();
    }

    /**
     * Method to configure a listener for ignore types.
     *
     * @param listener to configure
     * @param ignored types
     * @return NotificationListenerRegistration
     */
    @Override
    public NotificationListenerRegistration addIgnoredTypesNotificationListener(final NotificationListener listener, final NotificationType... ignored) {
        return configureListener(listener).withIgnoredEvents(ignored).register();
    }

    /**
     * Method to remove a notification listener.
     *
     * @param listener - listener to remove
     */
    @Override
    public synchronized void removeNotificationListener(final NotificationListener listener) {
        if (ObjectUtils.isNull(listener)) {
            LOGGER.warning("Could not remove a NotificationListener because it was null.");
            return;
        }

        List<NotificationListenerRegistration> matching = notificationListeners.stream().filter(registration -> registration.getListener() == listener)
                .collect(Collectors.toList());

        if (matching.size() > 0) {
            matching.forEach(registration -> removeNotificationListenerRegistration(registration));
            // thirdPartyNotificationService.setNotificationListeners(notificationListeners);
        } else {
            LOGGER.warning("Could not remove a NotificationListener because it wasn't already registered.");
        }
    }

    /**
     * Method to register a notificationListener.
     *
     * @param registration to add
     */
    @Override
    public void addNotificationListenerRegistration(final NotificationListenerRegistration registration) {
        if (registration != null) {
            notificationListeners.add(registration);
            thirdPartyNotificationService.setNotificationListeners(notificationListeners);
        } else {
            LOGGER.warning("Could not add a NotificationListenerRegistration because it was null.");
        }
    }

    /**
     * Method to remove a notificationListener.
     *
     * @param registration to remove
     */
    @Override
    public void removeNotificationListenerRegistration(final NotificationListenerRegistration registration) {
        if (registration != null) {
            notificationListeners.remove(registration);
            thirdPartyNotificationService.setNotificationListeners(notificationListeners);
        } else {
            LOGGER.warning("Could not add a NotificationListenerRegistration because it was null.");
        }
    }
}
