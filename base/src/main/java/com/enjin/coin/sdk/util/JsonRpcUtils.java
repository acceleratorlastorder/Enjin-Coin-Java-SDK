package com.enjin.coin.sdk.util;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import java.util.logging.Logger;

import com.enjin.coin.sdk.util.http.ContentType;
import com.thetransactioncompany.jsonrpc2.JSONRPC2Request;
import com.thetransactioncompany.jsonrpc2.JSONRPC2Response;
import com.thetransactioncompany.jsonrpc2.client.JSONRPC2Session;
import com.thetransactioncompany.jsonrpc2.client.JSONRPC2SessionException;
import com.thetransactioncompany.jsonrpc2.client.JSONRPC2SessionOptions;

/**
 * <p>
 * Contains all logic for performing json rpc calls.
 * </p>
 */
public class JsonRpcUtils {

    /**
     * Logger used by this class.
     */
    private static final Logger LOGGER = Logger.getLogger(JsonRpcUtils.class.getName());

    /**
     * Class constructor.
     */
    public JsonRpcUtils() {

    }

    /** Whether we are in test mode or not. **/
    private boolean isInTestMode = false;

    /**
     * Get whether in test mode or not.
     * @return boolean
     */
    public boolean getIsInTestMode() {
        return isInTestMode;
    }

    /**
     * Set whether in test mode or not.
     * @param isInTestMode - true if in test mode / false if not
     */
    public void setIsInTestMode(final boolean isInTestMode) {
        this.isInTestMode = isInTestMode;
    }

    /**
     * Method to send a json rpc request.
     *
     * @param url - url to send request to
     * @param responseClass - class type to convert to
     * @param method - method to call
     * @return an object returned from the json rpc call
     */
    public Object sendJsonRpcRequest(final String url, final Class<?> responseClass, final String method) {
        return sendJsonRpcRequest(url, responseClass, method, null);
    }

    /**
     * Method to send a json rpc request.
     *
     * @param url - url to send request to
     * @param responseClass - class type to convert to
     * @param method - method to call
     * @param params - map with the params to use
     * @return an object returned from the json rpc call
     */
    public Object sendJsonRpcRequest(final String url, final Class<?> responseClass, final String method, final Map<String, Object> params) {
        Object responseObject = null;

        if (StringUtils.isEmpty(url) || responseClass == null || StringUtils.isEmpty(method)) {
            LOGGER.warning("url or method passed in are null or empty or the responseClass is null");
            return responseObject;
        }

        try {
            // Creating a new session to a JSON-RPC 2.0 web service at a specified URL
            // The JSON-RPC 2.0 server URL
            URL serverURL = new URL(url);

            // Create new JSON-RPC 2.0 client session
            JSONRPC2Session jsonRpcSession = new JSONRPC2Session(serverURL);
            JSONRPC2SessionOptions jsonRPC2SessionOptions = jsonRpcSession.getOptions();
            jsonRPC2SessionOptions.setRequestContentType(ContentType.TEXT_JSON);
            jsonRPC2SessionOptions
                    .setAllowedResponseContentTypes(new String[] {ContentType.TEXT_JSON, ContentType.APPLICATION_JSON, ContentType.APPLICATION_JSON_RPC });
            jsonRpcSession.setOptions(jsonRPC2SessionOptions);

            String requestId = Utils.generateRandomId(isInTestMode);

            JSONRPC2Request jsonRpcRequest;
            if (MapUtils.isNotEmpty(params)) {
                jsonRpcRequest = new JSONRPC2Request(method, params, requestId);
            } else {
                jsonRpcRequest = new JSONRPC2Request(method, requestId);
            }

            LOGGER.fine(String.format("jsonRpcRequest:%s", jsonRpcRequest.toJSONString()));
            // Send request - exception here
            JSONRPC2Response jsonRpcResponse = jsonRpcSession.send(jsonRpcRequest);

            // Print response result / error
            if (jsonRpcResponse != null && jsonRpcResponse.indicatesSuccess()) {
                String responseString = jsonRpcResponse.getResult().toString();
                LOGGER.fine(String.format("responseString:%s", responseString));
                responseObject = JsonUtils.convertJsonToObject(GsonUtils.GSON, responseString, responseClass);
            } else if (jsonRpcResponse != null) {
                LOGGER.warning(String.format("Error Message:%s", jsonRpcResponse.getError().getMessage()));
            } else {
                LOGGER.warning("Error has occured - jsonRpcResponse is null");
            }

        } catch (MalformedURLException e) {
            LOGGER.warning(String.format("A MalformedURLException has occured. Exception: %s", StringUtils.exceptionToString(e)));
        } catch (JSONRPC2SessionException e) {
            LOGGER.warning(String.format("A JSONRPC2SessionException has occured. Exception: %s", StringUtils.exceptionToString(e)));
        }

        return responseObject;
    }

}
