package com.enjin.coin.sdk.service;

import java.util.logging.Logger;

import com.enjin.coin.sdk.config.Config;
import com.enjin.coin.sdk.service.events.EventsService;
import com.enjin.coin.sdk.service.events.impl.EventsServiceImpl;
import com.enjin.coin.sdk.service.identities.IdentitiesService;
import com.enjin.coin.sdk.service.identities.impl.IdentitiesServiceImpl;
import com.enjin.coin.sdk.service.notifications.NotificationsService;
import com.enjin.coin.sdk.service.notifications.impl.NotificationsServiceImpl;
import com.enjin.coin.sdk.service.platform.PlatformService;
import com.enjin.coin.sdk.service.platform.impl.PlatformServiceImpl;
import com.enjin.coin.sdk.service.tokens.TokensService;
import com.enjin.coin.sdk.service.tokens.impl.TokensServiceImpl;
import com.enjin.coin.sdk.service.transactionrequests.TransactionRequestsService;
import com.enjin.coin.sdk.service.transactionrequests.impl.TransactionRequestsServiceImpl;
import com.enjin.coin.sdk.util.ObjectUtils;

/**
 * <p>Enjin Coin Client - Synchronous.</p>
 * <p>All services will be instantiated from this class that will be called in a synchronous fashion.</p>
 */
public class EnjinCoinClient implements EnjinCoin {

    /**
     * Logger used by this class.
     */
    private static final Logger LOGGER = Logger.getLogger(EnjinCoinClient.class.getName());

    /**
     * SDK configuration for this client.
     */
    private Config config;
    /**
     * Events sync service.
     */
    private EventsService eventsService;
    /**
     * Identities sync service.
     */
    private IdentitiesService identitiesService;
    /**
     * Tokens sync service.
     */
    private TokensService tokensService;
    /**
     * TransactionRequests sync service.
     */
    private TransactionRequestsService transactionRequestsService;

    /**
     * Notifications sync service.
     */
    private NotificationsService notificationsService;

    /**
     * Platform sync service.
     */
    private PlatformService platformService;

    /**
     * Class constructor.
     *
     * @param config - config to use
     */
    public EnjinCoinClient(final Config config) {
        if (ObjectUtils.isNull(config)) {
            LOGGER.warning("The supplied config is null.");
            return;
        }

        this.config = config;
    }

    /**
     * Method to get the EventService.
     *
     * @return - EventsService
     */
    @Override
    public EventsService getEventsService() {
        if (eventsService == null) {
            eventsService = new EventsServiceImpl(config);
        }
        return eventsService;
    }

    /**
     * Method to get the IdentitiesService.
     *
     * @return - IdentitiesService
     */
    @Override
    public IdentitiesService getIdentitiesService() {
        if (identitiesService == null) {
            identitiesService = new IdentitiesServiceImpl(config);
        }
        return identitiesService;
    }

    /**
     * Method to get the TokensService.
     *
     * @return - TokensService
     */
    @Override
    public TokensService getTokensService() {
        if (tokensService == null) {
            tokensService = new TokensServiceImpl(config);
        }
        return tokensService;
    }

    /**
     * Method to get the TransactionRequestsService.
     *
     * @return - TransactionRequestsService
     */
    @Override
    public TransactionRequestsService getTransactionRequestsService() {
        if (transactionRequestsService == null) {
            transactionRequestsService = new TransactionRequestsServiceImpl(config);
        }
        return transactionRequestsService;
    }

    /**
     * Method to get the notificationsService.
     * @return NotificationsService
     */
    @Override
    public NotificationsService getNotificationsService() {
        if (notificationsService == null) {
            notificationsService = new NotificationsServiceImpl(config);
        }
        return notificationsService;
    }

    /**
     * Method to get the platformService.
     * @return PlatformService
     */
    @Override
    public PlatformService getPlatformService() {
        if (platformService == null) {
            platformService = new PlatformServiceImpl(config);
        }
        return platformService;
    }
}
