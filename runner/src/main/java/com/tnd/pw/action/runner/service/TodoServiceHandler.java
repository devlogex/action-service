package com.tnd.pw.action.runner.service;

import com.tnd.dbservice.common.exception.DBServiceException;
import com.tnd.pw.action.common.representations.TodoRepresentation;
import com.tnd.pw.action.common.representations.CsActionRepresentation;
import com.tnd.pw.action.common.representations.TodoRepresentation;
import com.tnd.pw.action.common.requests.ActionRequest;
import com.tnd.pw.action.common.requests.UserRequest;
import com.tnd.pw.action.runner.exception.NoPermissionException;
import com.tnd.pw.action.todos.exception.InconsistentStateException;
import com.tnd.pw.action.todos.exception.TodoAssignNotFoundException;
import com.tnd.pw.action.todos.exception.TodoNotFoundException;

import java.io.IOException;

public interface TodoServiceHandler {
    TodoRepresentation addTodo(ActionRequest request) throws IOException, DBServiceException;
    TodoRepresentation removeTodo(ActionRequest request) throws DBServiceException, IOException, TodoNotFoundException;
    CsActionRepresentation getTodos(ActionRequest request) throws DBServiceException, IOException;

    CsActionRepresentation getTodoOfUser(UserRequest request) throws DBServiceException, IOException;

    TodoRepresentation updateTodo(ActionRequest request) throws DBServiceException, TodoNotFoundException, IOException, TodoAssignNotFoundException;

    TodoRepresentation verifyTodo(ActionRequest request) throws InconsistentStateException, IOException, DBServiceException, TodoAssignNotFoundException, TodoNotFoundException;
}
