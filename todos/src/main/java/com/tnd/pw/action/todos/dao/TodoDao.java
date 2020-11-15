package com.tnd.pw.action.todos.dao;


import com.tnd.dbservice.common.exception.DBServiceException;
import com.tnd.pw.action.todos.entity.TodoEntity;
import com.tnd.pw.action.todos.exception.TodoNotFoundException;

import java.io.IOException;
import java.util.List;

public interface TodoDao {
    void create(TodoEntity entity) throws DBServiceException;
    List<TodoEntity> get(TodoEntity entity) throws TodoNotFoundException, DBServiceException;
    List<TodoEntity> get(List<Long> ids) throws DBServiceException, TodoNotFoundException;
    List<TodoEntity> getByBelongIds(List<Long> ids) throws TodoNotFoundException, DBServiceException;
    void update(TodoEntity entity) throws DBServiceException;
    void remove(TodoEntity entity) throws DBServiceException;
    void remove(List<Long> ids) throws DBServiceException;

}
