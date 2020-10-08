package com.tnd.pw.action.todos.service;

import com.tnd.dbservice.common.exception.DBServiceException;
import com.tnd.pw.action.todos.entity.TodoAssignEntity;
import com.tnd.pw.action.todos.entity.TodoEntity;
import com.tnd.pw.action.todos.exception.TodoAssignNotFoundException;
import com.tnd.pw.action.todos.exception.TodoNotFoundException;

import java.io.IOException;
import java.util.List;

public interface TodoService {
    TodoEntity createTodo(TodoEntity entity) throws IOException, DBServiceException;
    List<TodoEntity> getTodo(TodoEntity entity) throws DBServiceException, TodoNotFoundException, IOException;
    List<TodoEntity> getTodo(List<Long> ids) throws DBServiceException, TodoNotFoundException, IOException;
    void updateTodo(TodoEntity entity) throws IOException, DBServiceException;
    void removeTodo(TodoEntity entity) throws IOException, DBServiceException;
    void removeTodo(List<Long> ids) throws IOException, DBServiceException;

    TodoAssignEntity createTodoAssign(TodoAssignEntity entity) throws IOException, DBServiceException;
    List<TodoAssignEntity> getTodoAssign(TodoAssignEntity entity) throws DBServiceException, TodoAssignNotFoundException, IOException;
    List<TodoAssignEntity> getTodoAssign(List<Long> todoIds) throws DBServiceException, TodoAssignNotFoundException, IOException;
    void updateTodoAssign(TodoAssignEntity entity) throws IOException, DBServiceException;
    void removeTodoAssign(TodoAssignEntity entity) throws IOException, DBServiceException;
    void removeTodoAssign(List<Long> ids) throws IOException, DBServiceException;
    void removeTodoAssignByTodoIds(List<Long> ids) throws IOException, DBServiceException;
    void removeTodoAssignByUserIds(List<Long> userIds) throws IOException, DBServiceException;
}
