package com.tnd.pw.action.todos.dao;


import com.tnd.dbservice.common.exception.DBServiceException;
import com.tnd.pw.action.todos.entity.TodoEntity;
import com.tnd.pw.action.todos.exception.TodoNotFoundException;

import java.io.IOException;
import java.util.List;

public interface TodoDao {
    void create(TodoEntity entity) throws IOException, DBServiceException;
    List<TodoEntity> get(TodoEntity entity) throws TodoNotFoundException, IOException, DBServiceException;
    List<TodoEntity> get(List<Long> ids) throws IOException, DBServiceException, TodoNotFoundException;
    void update(TodoEntity entity) throws IOException, DBServiceException;
    void remove(TodoEntity entity) throws IOException, DBServiceException;
    void remove(List<Long> ids) throws IOException, DBServiceException;
}
