package com.enjin.coin.sdk.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.concurrent.ExecutorService;

import org.junit.Before;
import org.junit.Test;

import com.enjin.coin.sdk.BaseTestHelper;
import com.enjin.coin.sdk.config.Config;
import com.enjin.coin.sdk.config.ImmutableConfig;
import com.enjin.coin.sdk.config.ImmutablePlatform;
import com.enjin.coin.sdk.config.Platform;
import com.enjin.coin.sdk.service.identities.impl.IdentitiesServiceImpl;
import com.enjin.coin.sdk.service.platform.PlatformService;
import com.enjin.coin.sdk.util.JsonRpcUtils;
import com.enjin.coin.sdk.util.http.Protocol;

public class BaseServiceTest {

    BaseService baseAction;
    Config enjinConfig;

    @Before
    public void setUp() {
        enjinConfig = BaseTestHelper.getEnjinConfig();
        baseAction = new IdentitiesServiceImpl(enjinConfig);
    }

    @Test
    public void testContructor_EnjinConfigIsNull() {
        enjinConfig = null;
        baseAction = new IdentitiesServiceImpl(enjinConfig);
        assertThat(baseAction).isNotNull()
                .satisfies(o -> assertThat(o.toString()).isNotEmpty());
    }

    @Test
    public void testContructor1() {
        baseAction = new IdentitiesServiceImpl(enjinConfig);
        assertThat(baseAction).isNotNull()
                .satisfies(o -> assertThat(o.toString()).isNotEmpty());
    }

    @Test
    public void testContructor2() {
        enjinConfig = ImmutableConfig.builder()
                .setTrustedPlatform(ImmutablePlatform.builder()
                        .setHost("localhost")
                        .setPort(8080)
                        .setProtocol(Protocol.HTTP)
                        .build())
                .setInTestMode(true)
                .build();
        baseAction = new IdentitiesServiceImpl(enjinConfig);
        assertThat(baseAction).isNotNull()
                .satisfies(o -> assertThat(o.toString()).isNotEmpty());
    }

    @Test
    public void testGetIdentitiesUrl1() {
        String identitiesURL = baseAction.getIdentitiesUrl();
        assertThat(identitiesURL).isNotEmpty();
    }

    @Test
    public void testGetIdentitiesUrl2() {
        baseAction.setTrustedPlatform(ImmutablePlatform.builder()
                .setHost("localhost")
                .setPort(8081)
                .setProtocol(Protocol.HTTP)
                .build());
        String identitiesURL = baseAction.getIdentitiesUrl();
        assertThat(identitiesURL).isNotEmpty();
    }

    @Test
    public void testGetIdentitiesUrl3() {
        baseAction.setTrustedPlatform(ImmutablePlatform.builder()
                .setHost("localhost")
                .setPort(8081)
                .setProtocol(Protocol.HTTP)
                .build());
        String identitiesURL = baseAction.getIdentitiesUrl();
        assertThat(identitiesURL).isNotEmpty();
    }

    @Test
    public void testGetIdentitiesUrl_TrustedPlatformIsNull() {
        baseAction.setTrustedPlatform(null);
        String identitiesURL = baseAction.getIdentitiesUrl();
        assertThat(identitiesURL).isNotEmpty();
    }

    @Test
    public void testGetTokensUrl1() {
        String tokensURL = baseAction.getTokensUrl();
        assertThat(tokensURL).isNotEmpty();
    }

    @Test
    public void testGetTokensUrl2() {
        baseAction.setTrustedPlatform(ImmutablePlatform.builder()
                .setHost("localhost")
                .setPort(8081)
                .setProtocol(Protocol.HTTP)
                .build());
        String tokensURL = baseAction.getTokensUrl();
        assertThat(tokensURL).isNotEmpty();
    }

    @Test
    public void testGetTokensUrl3() {
        baseAction.setTrustedPlatform(ImmutablePlatform.builder()
                .setHost("localhost")
                .setPort(8081)
                .setProtocol(Protocol.HTTP)
                .build());
        String tokensURL = baseAction.getTokensUrl();
        assertThat(tokensURL).isNotEmpty();
    }

    @Test
    public void testGetTokensUrl_TrustedPlatformIsNull() {
        baseAction.setTrustedPlatform(null);
        String tokensURL = baseAction.getTokensUrl();
        assertThat(tokensURL).isNotEmpty();
    }

    @Test
    public void testGetTransactionRequestsUrl1() {
        String transactionRequestsURL = baseAction.getTransactionRequestsUrl();
        assertThat(transactionRequestsURL).isNotEmpty();
    }

    @Test
    public void testGetTransactionRequestsUrl2() {
        baseAction.setTrustedPlatform(ImmutablePlatform.builder()
                .setHost("localhost")
                .setPort(8081)
                .setProtocol(Protocol.HTTP)
                .build());
        String transactionRequestsURL = baseAction.getTransactionRequestsUrl();
        assertThat(transactionRequestsURL).isNotEmpty();
    }

    @Test
    public void testGetTransactionRequestsUrl3() {
        baseAction.setTrustedPlatform(ImmutablePlatform.builder()
                .setHost("localhost")
                .setPort(8081)
                .setProtocol(Protocol.HTTP)
                .build());
        String transactionRequestsURL = baseAction.getTransactionRequestsUrl();
        assertThat(transactionRequestsURL).isNotEmpty();
    }

    @Test
    public void testGetTransactionRequestsUrl_TrustedPlatformIsNull() {
        baseAction.setTrustedPlatform(null);
        String transactionRequestsURL = baseAction.getTransactionRequestsUrl();
        assertThat(transactionRequestsURL).isNotEmpty();
    }

    @Test
    public void testGetEventsUrl1() {
        String identitiesURL = baseAction.getEventsUrl();
        assertThat(identitiesURL).isNotEmpty();
    }

    @Test
    public void testGetEventsUrl2() {
        baseAction.setTrustedPlatform(ImmutablePlatform.builder()
                .setHost("localhost")
                .setPort(8081)
                .setProtocol(Protocol.HTTP)
                .build());
        String identitiesURL = baseAction.getEventsUrl();
        assertThat(identitiesURL).isNotEmpty();
    }

    @Test
    public void testGetEventsUrl3() {
        baseAction.setTrustedPlatform(ImmutablePlatform.builder()
                .setHost("localhost")
                .setPort(8081)
                .setProtocol(Protocol.HTTP)
                .build());
        String identitiesURL = baseAction.getEventsUrl();
        assertThat(identitiesURL).isNotEmpty();
    }

    @Test
    public void testGetEventsUrl_TrustedPlatformIsNull() {
        baseAction.setTrustedPlatform(null);
        String identitiesURL = baseAction.getEventsUrl();
        assertThat(identitiesURL).isNotEmpty();
    }
    @Test
    public void testGetPlatformUrl1() {
        String identitiesURL = baseAction.getPlatformUrl();
        assertThat(identitiesURL).isNotEmpty();
    }

    @Test
    public void testGetPlatformUrl2() {
        baseAction.setTrustedPlatform(ImmutablePlatform.builder()
                .setHost("localhost")
                .setPort(8081)
                .setProtocol(Protocol.HTTP)
                .build());
        String identitiesURL = baseAction.getPlatformUrl();
        assertThat(identitiesURL).isNotEmpty();
    }

    @Test
    public void testGetPlatformUrl3() {
        baseAction.setTrustedPlatform(ImmutablePlatform.builder()
                .setHost("localhost")
                .setPort(8081)
                .setProtocol(Protocol.HTTP)
                .build());
        String identitiesURL = baseAction.getPlatformUrl();
        assertThat(identitiesURL).isNotEmpty();
    }

    @Test
    public void testGetPlatformUrl_TrustedPlatformIsNull() {
        baseAction.setTrustedPlatform(null);
        String identitiesURL = baseAction.getPlatformUrl();
        assertThat(identitiesURL).isNotEmpty();
    }
    @Test
    public void testGetTrustedPlatform_Default() {
        baseAction.setTrustedPlatform(ImmutablePlatform.builder().build());
        Platform trustedPlatform = baseAction.getTrustedPlatform();
        assertThat(trustedPlatform).isEqualToComparingFieldByField(ImmutablePlatform.builder().build());
    }

    @Test
    public void testGetTrustedPlatform_Success() {
        baseAction.setTrustedPlatform(ImmutablePlatform.builder()
                .setHost("localhost")
                .setPort(8081)
                .setProtocol(Protocol.HTTP)
                .build());
        Platform trustedPlatform = baseAction.getTrustedPlatform();
        assertThat(trustedPlatform).isNotNull();
    }

    @Test
    public void testGetJsonRpcUtils() {
        JsonRpcUtils jsonRpcUtils = baseAction.getJsonRpcUtils();
        assertThat(jsonRpcUtils).isNotNull();
    }

    @Test
    public void testGetExecutorService() {
        ExecutorService executorService = baseAction.getExecutorService();
        assertThat(executorService).isNotNull();
    }


    @Test
    public void testGetPlatformService() {
        PlatformService platformService1 = baseAction.getPlatformService(enjinConfig);
        assertThat(platformService1).isNotNull();

        PlatformService platformService2 = baseAction.getPlatformService(enjinConfig);
        assertThat(platformService2).isNotNull().isSameAs(platformService1);
    }
}
