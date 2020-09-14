package com.tnd.pw.action.todos.service.impl;

import com.tnd.common.api.common.Utils.GenUID;
import com.tnd.dbservice.common.exception.DBServiceException;
import com.tnd.pw.action.todos.constants.AssignState;
import com.tnd.pw.action.todos.constants.TodoState;
import com.tnd.pw.action.todos.dao.TodoAssignDao;
import com.tnd.pw.action.todos.dao.TodoDao;
import com.tnd.pw.action.todos.entity.TodoAssignEntity;
import com.tnd.pw.action.todos.entity.TodoEntity;
import com.tnd.pw.action.todos.exception.TodoAssignNotFoundException;
import com.tnd.pw.action.todos.exception.TodoNotFoundException;
import com.tnd.pw.action.todos.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.List;

public class TodoServiceImpl implements TodoService {

    @Autowired
    private TodoDao todoDao;
    @Autowired
    private TodoAssignDao todoAssignDao;

    @Override
    public TodoEntity createTodo(TodoEntity entity) throws IOException, DBServiceException {
        entity.setId(GenUID.genIdByParent(entity.getBelongId()));
        entity.setCreatedAt(System.currentTimeMillis());
        entity.setState(TodoState.PENDING.ordinal());
        todoDao.create(entity);
        return entity;
    }

    @Override
    public List<TodoEntity> getTodo(TodoEntity entity) throws DBServiceException, TodoNotFoundException, IOException {
        return todoDao.get(entity);
    }

    @Override
    public List<TodoEntity> getTodo(List<Long> ids) throws DBServiceException, TodoNotFoundException, IOException {
        return todoDao.get(ids);
    }

    @Override
    public void updateTodo(TodoEntity entity) throws IOException, DBServiceException {
        todoDao.update(entity);
    }

    @Override
    public void removeTodo(TodoEntity entity) throws IOException, DBServiceException {
        todoDao.remove(entity);
    }

    @Override
    public void removeTodo(List<Long> ids) throws IOException, DBServiceException {
        todoDao.remove(ids);
    }

    @Override
    public TodoAssignEntity createTodoAssign(TodoAssignEntity entity) throws IOException, DBServiceException {
        entity.setId(GenUID.genIdByParent(entity.getTodoId()));
        entity.create();
        todoAssignDao.create(entity);
        return entity;
    }

    @Override
    public List<TodoAssignEntity> getTodoAssign(TodoAssignEntity entity) throws DBServiceException, TodoAssignNotFoundException, IOException {
        return todoAssignDao.get(entity);
    }

    @Override
    public List<TodoAssignEntity> getTodoAssign(List<Long> todoIds) throws DBServiceException, TodoAssignNotFoundException, IOException {
        return todoAssignDao.get(todoIds);
    }

    @Override
    public void updateTodoAssign(TodoAssignEntity entity) throws IOException, DBServiceException {
        todoAssignDao.update(entity);
    }

    @Override
    public void removeTodoAssign(TodoAssignEntity entity) throws IOException, DBServiceException {
        todoAssignDao.remove(entity);
    }

    @Override
    public void removeTodoAssign(List<Long> ids) throws IOException, DBServiceException {
        todoAssignDao.remove(ids);
    }

    @Override
    public void removeTodoAssignByTodoIds(List<Long> ids) throws IOException, DBServiceException {
        todoAssignDao.removeByTodoIds(ids);
    }
}
