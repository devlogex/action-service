package com.tnd.pw.action.sdk;

import com.tnd.common.api.common.base.BaseResponse;
import com.tnd.pw.action.common.representations.CsActionRepresentation;

import java.util.List;

public interface ActionServiceSdkClient {
    BaseResponse<CsActionRepresentation> getTodoComments(Long belongId);
    BaseResponse<CsActionRepresentation> getTodos(List<Long> belongIds);
}
