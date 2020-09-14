package com.tnd.pw.action.sdk;

import com.google.gson.Gson;
import com.tnd.common.api.client.service.AbstractService;
import com.tnd.common.api.common.base.BaseResponse;
import com.tnd.common.api.common.constants.Protocol;
import com.tnd.pw.action.common.constants.ApiUrl;
import com.tnd.pw.action.common.representations.CsActionRepresentation;
import com.tnd.pw.action.common.requests.ActionRequest;

import java.io.IOException;

public class ActionSdkApi  extends AbstractService {
    private static final Gson gson = new Gson();
    private String domain;

    public ActionSdkApi(String domain) {
        this.domain = domain;
    }

    public BaseResponse<CsActionRepresentation> getTodo(ActionRequest request) throws IOException {
        return client.sendRequest(this.domain + ApiUrl.GET_TODO, Protocol.GET.name(), request);
    }

    public BaseResponse<CsActionRepresentation> getComment(ActionRequest request) throws IOException {
        return client.sendRequest(this.domain + ApiUrl.GET_COMMENT, Protocol.GET.name(), request);
    }

    public BaseResponse<CsActionRepresentation> getCsActionRep(ActionRequest request) throws IOException {
        return client.sendRequest(this.domain + ApiUrl.GET_TODO_COMMENT, Protocol.GET.name(), request);
    }

}
