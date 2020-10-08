package com.tnd.pw.action.todos.dao;

import com.tnd.dbservice.common.exception.DBServiceException;
import com.tnd.pw.action.todos.entity.TodoAssignEntity;
import com.tnd.pw.action.todos.exception.TodoAssignNotFoundException;

import java.io.IOException;
import java.util.List;

public interface TodoAssignDao {
    void create(TodoAssignEntity entity) throws IOException, DBServiceException;
    List<TodoAssignEntity> get(TodoAssignEntity entity) throws IOException, DBServiceException, TodoAssignNotFoundException;
    List<TodoAssignEntity> get(List<Long> todoIds) throws IOException, DBServiceException, TodoAssignNotFoundException;
    void update(TodoAssignEntity entity) throws IOException, DBServiceException;
    void remove(TodoAssignEntity entity) throws IOException, DBServiceException;
    void remove(List<Long> ids) throws IOException, DBServiceException;

    void removeByTodoIds(List<Long> ids) throws IOException, DBServiceException;

    void removeByUserIds(List<Long> ids) throws IOException, DBServiceException;
}
