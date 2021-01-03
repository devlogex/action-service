package com.tnd.pw.action.todos.dao;

import com.tnd.dbservice.common.exception.DBServiceException;
import com.tnd.pw.action.todos.entity.TodoAssignEntity;
import com.tnd.pw.action.todos.exception.TodoAssignNotFoundException;

import java.io.IOException;
import java.util.List;

public interface TodoAssignDao {
    void create(TodoAssignEntity entity) throws DBServiceException;
    List<TodoAssignEntity> get(TodoAssignEntity entity) throws DBServiceException, TodoAssignNotFoundException;
    List<TodoAssignEntity> get(List<Long> todoIds) throws DBServiceException, TodoAssignNotFoundException;
    void update(TodoAssignEntity entity) throws DBServiceException;
    void remove(TodoAssignEntity entity) throws DBServiceException;
    void remove(List<Long> ids) throws DBServiceException;

    void removeByTodoIds(List<Long> ids) throws DBServiceException;

    void removeByUserIds(List<Long> ids) throws DBServiceException;
}
