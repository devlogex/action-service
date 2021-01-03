package com.tnd.pw.action.runner.handler;

import com.tnd.common.api.common.base.BaseResponse;
import com.tnd.common.api.server.BaseHandler;
import com.tnd.common.api.server.service.annotation.HandlerService;
import com.tnd.common.api.server.service.annotation.HandlerServiceClass;
import com.tnd.dbservice.common.exception.DBServiceException;
import com.tnd.pw.action.common.constants.Methods;
import com.tnd.pw.action.common.requests.UserRequest;
import com.tnd.pw.action.runner.exception.NoPermissionException;
import com.tnd.pw.action.todos.exception.InconsistentStateException;
import com.tnd.pw.action.todos.exception.TodoAssignNotFoundException;
import com.tnd.pw.action.todos.exception.TodoNotFoundException;
import com.tnd.pw.action.common.representations.TodoRepresentation;
import com.tnd.pw.action.common.representations.CsActionRepresentation;
import com.tnd.pw.action.common.requests.ActionRequest;
import com.tnd.pw.action.common.utils.GsonUtils;
import com.tnd.pw.action.runner.service.TodoServiceHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

@HandlerServiceClass
public class TodoHandler implements BaseHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(TodoHandler.class);

    @Autowired
    private TodoServiceHandler todoServiceHandler;

    @HandlerService(path = "/action/todo/add", protocol = "POST")
    public BaseResponse<TodoRepresentation> addTodo(ActionRequest request) throws DBServiceException {
        LOGGER.info("[TodoHandler] addTodo() - request: {}", GsonUtils.convertToString(request));
        TodoRepresentation response = todoServiceHandler.addTodo(request);
        LOGGER.info("[TodoHandler] addTodo() - response: {}", GsonUtils.convertToString(response));
        return new BaseResponse<>(response);
    }

    @HandlerService(path = "/action/todo/update", protocol = "POST")
    public BaseResponse<TodoRepresentation> updateTodo(ActionRequest request) throws DBServiceException, TodoNotFoundException, TodoAssignNotFoundException {
        LOGGER.info("[TodoHandler] updateTodo() - request: {}", GsonUtils.convertToString(request));
        TodoRepresentation response = todoServiceHandler.updateTodo(request);
        LOGGER.info("[TodoHandler] updateTodo() - response: {}", GsonUtils.convertToString(response));
        return new BaseResponse<>(response);
    }

    @HandlerService(path = "/action/todo/verify", protocol = "POST")
    public BaseResponse<TodoRepresentation> verifyTodo(ActionRequest request) throws DBServiceException, TodoNotFoundException, TodoAssignNotFoundException, InconsistentStateException, NoPermissionException {
        LOGGER.info("[TodoHandler] verifyTodo() - request: {}", GsonUtils.convertToString(request));
        TodoRepresentation response = todoServiceHandler.verifyTodo(request);
        LOGGER.info("[TodoHandler] verifyTodo() - response: {}", GsonUtils.convertToString(response));
        return new BaseResponse<>(response);
    }

    @HandlerService(path = "/action/todo/remove", protocol = "POST")
    public BaseResponse<CsActionRepresentation> removeTodo(ActionRequest request) throws DBServiceException, TodoNotFoundException {
        LOGGER.info("[TodoHandler] removeTodo() - request: {}", GsonUtils.convertToString(request));
        CsActionRepresentation response = todoServiceHandler.removeTodo(request);
        LOGGER.info("[TodoHandler] removeTodo() - response: {}", GsonUtils.convertToString(response));
        return new BaseResponse<>(response);
    }

    @HandlerService(path = "/action/todo", protocol = "GET")
    public BaseResponse<CsActionRepresentation> getTodos(ActionRequest request) throws DBServiceException, TodoNotFoundException {
        LOGGER.info("[TodoHandler] getTodos() - request: {}", GsonUtils.convertToString(request));
        CsActionRepresentation response = todoServiceHandler.getTodos(request);
        LOGGER.info("[TodoHandler] getTodos() - response: {}", GsonUtils.convertToString(response));
        return new BaseResponse<>(response);
    }

    @HandlerService(path = "/action/todo/user", protocol = "GET")
    public BaseResponse<CsActionRepresentation> getTodoOfUser(UserRequest request) throws DBServiceException, TodoNotFoundException {
        LOGGER.info("[TodoHandler] getTodoOfUser() - request: {}", GsonUtils.convertToString(request));
        CsActionRepresentation response = todoServiceHandler.getTodoOfUser(request);
        LOGGER.info("[TodoHandler] getTodoOfUser() - response: {}", GsonUtils.convertToString(response));
        return new BaseResponse<>(response);
    }

    @HandlerService(method = Methods.GET_TODO)
    public BaseResponse<CsActionRepresentation> getTodoLists(ActionRequest request) throws DBServiceException, TodoNotFoundException {
        LOGGER.info("[TodoHandler] getTodoLists() - request: {}", GsonUtils.convertToString(request));
        CsActionRepresentation response = todoServiceHandler.getTodoLists(request);
        LOGGER.info("[TodoHandler] getTodoLists() - response: {}", GsonUtils.convertToString(response));
        return new BaseResponse<>(response);
    }
}
