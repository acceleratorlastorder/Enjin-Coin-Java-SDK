package com.enjin.coin.sdk.service.platform.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import com.enjin.coin.sdk.config.Config;
import com.enjin.coin.sdk.service.BaseService;
import com.enjin.coin.sdk.service.platform.PlatformService;
import com.enjin.coin.sdk.util.Constants;
import com.enjin.coin.sdk.util.ObjectUtils;
import com.enjin.coin.sdk.util.StringUtils;
import com.enjin.coin.sdk.vo.platform.GetPlatformAuthRequestVO;
import com.enjin.coin.sdk.vo.platform.GetPlatformAuthResponseVO;

/**
 * Platform service interface.
 */
public class PlatformServiceImpl extends BaseService implements PlatformService {

    /**
     * Logger used by this class.
     */
    private static final Logger LOGGER = Logger.getLogger(PlatformServiceImpl.class.getName());

    /**
     * Class constructor.
     *
     * @param config - the config to use
     */
    public PlatformServiceImpl(final Config config) {
        super(config);
    }

    /**
     * Method to get the auth details.
     *
     * @param platformAuthRequestVO - get platform auth request vo
     * @return - GetPlatformAuthResponseVO
     */
    @Override
    public GetPlatformAuthResponseVO getAuth(final GetPlatformAuthRequestVO platformAuthRequestVO) {
        GetPlatformAuthResponseVO response = null;

        if (ObjectUtils.isNull(platformAuthRequestVO)) {
            LOGGER.warning("Platform.auth request is null.");
            return response;
        }

        if (StringUtils.isEmpty(platformAuthRequestVO.getAuth())) {
            LOGGER.warning("Platform.auth - auth is null or empty.");
            return response;
        }

        Map<String, Object> params = new HashMap<>();
        params.put("auth", platformAuthRequestVO.getAuth().get());

        // Construct new request
        String method = Constants.METHOD_PLATFORM_AUTH;

        response = (GetPlatformAuthResponseVO) getJsonRpcUtils().sendJsonRpcRequest(getPlatformUrl(), GetPlatformAuthResponseVO.class, method, params);

        return response;
    }
}
