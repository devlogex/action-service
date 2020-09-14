package com.tnd.pw.action.runner.handler;

import com.tnd.common.api.common.base.BaseResponse;
import com.tnd.common.api.server.BaseHandler;
import com.tnd.common.api.server.service.annotation.HandlerService;
import com.tnd.common.api.server.service.annotation.HandlerServiceClass;
import com.tnd.dbservice.common.exception.DBServiceException;
import com.tnd.pw.action.common.representations.CsActionRepresentation;
import com.tnd.pw.action.common.representations.TodoRepresentation;
import com.tnd.pw.action.common.requests.ActionRequest;
import com.tnd.pw.action.common.utils.GsonUtils;
import com.tnd.pw.action.runner.exception.CallApiFailException;
import com.tnd.pw.action.runner.service.CommentServiceHandler;
import com.tnd.pw.action.runner.service.TodoServiceHandler;
import com.tnd.pw.action.todos.exception.TodoNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

@HandlerServiceClass
public class ActionHandler implements BaseHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(TodoHandler.class);

    @Autowired
    private TodoServiceHandler todoServiceHandler;
    @Autowired
    private CommentServiceHandler commentServiceHandler;

    @HandlerService(path = "/action/todo_comment", protocol = "GET")
    public BaseResponse<CsActionRepresentation> getTodoComment(ActionRequest request) throws DBServiceException, IOException, CallApiFailException {
        LOGGER.info("[ActionHandler] getTodoComment() - request: {}", GsonUtils.convertToString(request));
        CsActionRepresentation todoRep = todoServiceHandler.getTodos(request);
        CsActionRepresentation commentRep = commentServiceHandler.getComments(request);
        CsActionRepresentation response = new CsActionRepresentation();
        if(todoRep != null) {
            response.setTodoReps(todoRep.getTodoReps());
        }
        if(commentRep != null) {
            response.setCommentReps(commentRep.getCommentReps());
        }
        LOGGER.info("[ActionHandler] getTodoComment() - response: {}", GsonUtils.convertToString(response));
        return new BaseResponse<>(response);
    }
}
