package com.tnd.pw.action.sdk;

import com.tnd.common.api.common.base.BaseResponse;
import com.tnd.pw.action.common.representations.CsActionRepresentation;

public interface ActionServiceSdkClient {
    BaseResponse<CsActionRepresentation> getTodoComments(Long belongId);
}
