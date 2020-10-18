package com.tnd.pw.action.sdk.impl;

import com.tnd.common.api.client.service.AbstractService;
import com.tnd.common.api.common.base.BaseResponse;
import com.tnd.pw.action.common.constants.Methods;
import com.tnd.pw.action.common.representations.CsActionRepresentation;
import com.tnd.pw.action.common.requests.ActionRequest;
import com.tnd.pw.action.sdk.ActionServiceSdkClient;

public class ActionServiceSdkClientImpl extends AbstractService implements ActionServiceSdkClient {

    public ActionServiceSdkClientImpl(String host, int port, int appId) {
        super(host, port, appId);
    }

    @Override
    public BaseResponse<CsActionRepresentation> getTodoComments(Long belongId) {
        ActionRequest request = new ActionRequest();
        request.setId(belongId);
        return client.sendRequest(Methods.GET_TODO_COMMENT, request);
    }
}
