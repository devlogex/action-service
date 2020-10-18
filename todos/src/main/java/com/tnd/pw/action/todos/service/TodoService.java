package com.tnd.pw.action.todos.service;

import com.tnd.dbservice.common.exception.DBServiceException;
import com.tnd.pw.action.todos.entity.TodoAssignEntity;
import com.tnd.pw.action.todos.entity.TodoEntity;
import com.tnd.pw.action.todos.exception.TodoAssignNotFoundException;
import com.tnd.pw.action.todos.exception.TodoNotFoundException;

import java.io.IOException;
import java.util.List;

public interface TodoService {
    TodoEntity createTodo(TodoEntity entity) throws DBServiceException;
    List<TodoEntity> getTodo(TodoEntity entity) throws DBServiceException, TodoNotFoundException;
    List<TodoEntity> getTodo(List<Long> ids) throws DBServiceException, TodoNotFoundException;
    void updateTodo(TodoEntity entity) throws DBServiceException;
    void removeTodo(TodoEntity entity) throws DBServiceException;
    void removeTodo(List<Long> ids) throws DBServiceException;

    TodoAssignEntity createTodoAssign(TodoAssignEntity entity) throws DBServiceException;
    List<TodoAssignEntity> getTodoAssign(TodoAssignEntity entity) throws DBServiceException, TodoAssignNotFoundException;
    List<TodoAssignEntity> getTodoAssign(List<Long> todoIds) throws DBServiceException, TodoAssignNotFoundException;
    void updateTodoAssign(TodoAssignEntity entity) throws DBServiceException;
    void removeTodoAssign(TodoAssignEntity entity) throws DBServiceException;
    void removeTodoAssign(List<Long> ids) throws DBServiceException;
    void removeTodoAssignByTodoIds(List<Long> ids) throws DBServiceException;
    void removeTodoAssignByUserIds(List<Long> userIds) throws DBServiceException;
}
