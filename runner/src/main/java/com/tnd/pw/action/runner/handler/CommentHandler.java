package com.tnd.pw.action.runner.handler;

import com.tnd.common.api.common.base.BaseResponse;
import com.tnd.common.api.server.BaseHandler;
import com.tnd.common.api.server.service.annotation.HandlerService;
import com.tnd.common.api.server.service.annotation.HandlerServiceClass;
import com.tnd.dbservice.common.exception.DBServiceException;
import com.tnd.pw.action.comments.exception.CommentNotFoundException;
import com.tnd.pw.action.common.representations.CommentRepresentation;
import com.tnd.pw.action.common.representations.CsActionRepresentation;
import com.tnd.pw.action.common.requests.ActionRequest;
import com.tnd.pw.action.common.requests.UserRequest;
import com.tnd.pw.action.common.utils.GsonUtils;
import com.tnd.pw.action.runner.exception.CallApiFailException;
import com.tnd.pw.action.runner.exception.NoPermissionException;
import com.tnd.pw.action.runner.service.CommentServiceHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

@HandlerServiceClass
public class CommentHandler implements BaseHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(CommentHandler.class);

    @Autowired
    private CommentServiceHandler commentServiceHandler;

    @HandlerService(path = "/action/comment/add", protocol = "POST")
    public BaseResponse<CommentRepresentation> addComment(ActionRequest request) throws DBServiceException, IOException, CallApiFailException {
        LOGGER.info("[CommentHandler] addComment() - request: {}", GsonUtils.convertToString(request));
        CommentRepresentation response = commentServiceHandler.addComment(request);
        LOGGER.info("[CommentHandler] addComment() - response: {}", GsonUtils.convertToString(response));
        return new BaseResponse<>(response);
    }

    @HandlerService(path = "/action/comment/remove", protocol = "POST")
    public BaseResponse<CommentRepresentation> removeComment(ActionRequest request) throws DBServiceException, IOException, CommentNotFoundException, NoPermissionException {
        LOGGER.info("[CommentHandler] removeComment() - request: {}", GsonUtils.convertToString(request));
        CommentRepresentation response = commentServiceHandler.removeComment(request);
        LOGGER.info("[CommentHandler] removeComment() - response: {}", GsonUtils.convertToString(response));
        return new BaseResponse<>(response);
    }

    @HandlerService(path = "/action/comment", protocol = "GET")
    public BaseResponse<CsActionRepresentation> getComments(ActionRequest request) throws DBServiceException, IOException, CallApiFailException {
        LOGGER.info("[CommentHandler] getComments() - request: {}", GsonUtils.convertToString(request));
        CsActionRepresentation response = commentServiceHandler.getComments(request);
        LOGGER.info("[CommentHandler] getComments() - response: {}", GsonUtils.convertToString(response));
        return new BaseResponse<>(response);
    }

    @HandlerService(path = "/action/comment/user", protocol = "GET")
    public BaseResponse<CsActionRepresentation> getCommentOfUser(UserRequest request) throws DBServiceException, IOException, CallApiFailException {
        LOGGER.info("[CommentHandler] getCommentOfUser() - request: {}", GsonUtils.convertToString(request));
        CsActionRepresentation response = commentServiceHandler.getCommentOfUser(request);
        LOGGER.info("[CommentHandler] getCommentOfUser() - response: {}", GsonUtils.convertToString(response));
        return new BaseResponse<>(response);
    }
}
