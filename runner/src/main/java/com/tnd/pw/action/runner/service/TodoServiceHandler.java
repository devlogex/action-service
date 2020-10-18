package com.tnd.pw.action.runner.service;

import com.tnd.dbservice.common.exception.DBServiceException;
import com.tnd.pw.action.common.representations.TodoRepresentation;
import com.tnd.pw.action.common.representations.CsActionRepresentation;
import com.tnd.pw.action.common.requests.ActionRequest;
import com.tnd.pw.action.common.requests.UserRequest;
import com.tnd.pw.action.runner.exception.NoPermissionException;
import com.tnd.pw.action.todos.exception.InconsistentStateException;
import com.tnd.pw.action.todos.exception.TodoAssignNotFoundException;
import com.tnd.pw.action.todos.exception.TodoNotFoundException;

public interface TodoServiceHandler {
    TodoRepresentation addTodo(ActionRequest request) throws DBServiceException;
    CsActionRepresentation removeTodo(ActionRequest request) throws DBServiceException, TodoNotFoundException;
    CsActionRepresentation getTodos(ActionRequest request) throws DBServiceException;

    CsActionRepresentation getTodoOfUser(UserRequest request) throws DBServiceException;

    TodoRepresentation updateTodo(ActionRequest request) throws DBServiceException, TodoNotFoundException, TodoAssignNotFoundException;

    TodoRepresentation verifyTodo(ActionRequest request) throws InconsistentStateException, DBServiceException, TodoAssignNotFoundException, TodoNotFoundException, NoPermissionException;
}
