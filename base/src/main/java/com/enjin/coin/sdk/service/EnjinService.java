package com.enjin.coin.sdk.service;

import com.enjin.coin.sdk.config.EnjinConfig;

import java.util.logging.Logger;

public class EnjinService {

    private static final Logger LOGGER = Logger.getLogger(EnjinService.class.getName());

    private EnjinConfig enjinConfig;
    private EventsService eventsService;
    private IdentitiesService identitiesService;
    private TokensService tokensService;
    private TransactionRequestsService transactionRequestsService;

    /**
     * Class constructor
     * @param enjinConfig - enjinConfig to use
     */
    public EnjinService(EnjinConfig enjinConfig) {
        if (enjinConfig == null) {
            LOGGER.warning("The enjinConfig passed in is null");
            return;
        }

        this.enjinConfig = enjinConfig;
    }

    /**
     * Method to get the eventsService
     *
     * @return - EventsService
     */
    public EventsService getEventsService() {
        if (eventsService == null) {
            eventsService = new EventsService(enjinConfig);
        }
        return eventsService;
    }

    /**
     * Method to get the identitiesService
     *
     * @return - IdentitiesService
     */
    public IdentitiesService getIdentitiesService() {
        if (identitiesService == null) {
            identitiesService = new IdentitiesService(enjinConfig);
        }
        return identitiesService;
    }

    /**
     * Method to get the tokensService
     *
     * @return - TokensService
     */
    public TokensService getTokensService() {
        if (tokensService == null) {
            tokensService = new TokensService(enjinConfig);
        }
        return tokensService;
    }

    /**
     * Method to get the transactionRequestsService
     *
     * @return - TransactionRequestsService
     */
    public TransactionRequestsService getTransactionRequestsService() {
        if (transactionRequestsService == null) {
            transactionRequestsService = new TransactionRequestsService(enjinConfig);
        }
        return transactionRequestsService;
    }
}
