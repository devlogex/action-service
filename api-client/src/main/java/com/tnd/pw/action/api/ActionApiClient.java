package com.tnd.pw.action.api;

import com.google.gson.Gson;
import com.tnd.common.api.client.service.AbstractService;
import com.tnd.common.api.common.base.BaseResponse;
import com.tnd.common.api.common.constants.Protocol;
import com.tnd.pw.action.api.constants.ApiUrl;
import com.tnd.pw.action.api.requests.ConfigServiceRequest;
import com.tnd.pw.action.api.responses.ConfigServiceResponse;

import java.io.IOException;

public class ActionApiClient extends AbstractService {
    private static final Gson gson = new Gson();

    public BaseResponse<ConfigServiceResponse> getUserProfile(ConfigServiceRequest request) throws IOException {
        return client.sendRequest(ApiUrl.GET_USER_PROFILE, Protocol.GET.name(), request);
    }
}
