package com.tnd.pw.action.todos.dao.impl;

import com.tnd.dbservice.common.exception.DBServiceException;
import com.tnd.pw.action.dbservice.DataHelper;
import com.tnd.pw.action.todos.dao.TodoAssignDao;
import com.tnd.pw.action.todos.dao.TodoDao;
import com.tnd.pw.action.todos.entity.TodoAssignEntity;
import com.tnd.pw.action.todos.entity.TodoEntity;
import com.tnd.pw.action.todos.exception.TodoAssignNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.util.List;

public class TodoAssignDaoImpl implements TodoAssignDao {
    @Autowired
    private DataHelper dataHelper;

    private static final String SQL_CREATE =
            "INSERT INTO todo_assign(id, todo_id, user_id, workspace_id, type, state) " +
                    "values(%d, %d, %d, %d, %d, %d)";
    private static final String SQL_SELECT_BY_ID =
            "SELECT * FROM todo_assign WHERE id = %d";
    private static final String SQL_SELECT_LIST_BY_TODO_ID =
            "SELECT * FROM todo_assign WHERE todo_id IN (%s)";
    private static final String SQL_SELECT_BY_TODO_ID =
            "SELECT * FROM todo_assign WHERE todo_id = %d";
    private static final String SQL_SELECT_BY_TODO_ID_AND_STATE =
            "SELECT * FROM todo_assign WHERE todo_id = %d AND state = %d";
    private static final String SQL_SELECT_BY_USER_ID_AND_WORKSPACE_ID =
            "SELECT * FROM todo_assign WHERE user_id = %d AND workspace_id = %d";
    private static final String SQL_UPDATE =
            "UPDATE todo_assign SET state = %d " +
                    "WHERE todo_id = %d AND user_id = %d";
    private static final String SQL_DELETE =
            "DELETE FROM todo_assign WHERE id = %d";
    private static final String SQL_DELETE_LIST =
            "DELETE FROM todo_assign WHERE id IN (%s)";
    private static final String SQL_DELETE_BY_LIST_TODO_ID =
            "DELETE FROM todo_assign WHERE todo_id IN (%s)";


    @Override
    public void create(TodoAssignEntity entity) throws IOException, DBServiceException {
        String query = String.format(SQL_CREATE, entity.getId(), entity.getTodoId(), entity.getUserId(),
                entity.getWorkspaceId(), entity.getType(), entity.getState());
        dataHelper.executeSQL(query);
    }

    @Override
    public List<TodoAssignEntity> get(TodoAssignEntity entity) throws IOException, DBServiceException, TodoAssignNotFoundException {
        String query;
        if(entity.getId() != null) {
            query = String.format(SQL_SELECT_BY_ID, entity.getId());
        }
        else if(entity.getTodoId() != null && entity.getState() != null) {
            query = String.format(SQL_SELECT_BY_TODO_ID_AND_STATE, entity.getTodoId(), entity.getState());
        }
        else if(entity.getTodoId() != null) {
            query = String.format(SQL_SELECT_BY_TODO_ID, entity.getTodoId());
        }
        else {
            query = String.format(SQL_SELECT_BY_USER_ID_AND_WORKSPACE_ID,
                    entity.getUserId(),
                    entity.getWorkspaceId());
        }
        List<TodoAssignEntity> entities = dataHelper.querySQL(query, TodoAssignEntity.class);
        if(CollectionUtils.isEmpty(entities)) {
            throw new TodoAssignNotFoundException();
        }
        return entities;
    }

    @Override
    public List<TodoAssignEntity> get(List<Long> todoIds) throws IOException, DBServiceException, TodoAssignNotFoundException {
        String listId = "";
        for (int i=0;i<todoIds.size() - 1; i++) {
            listId += todoIds.get(i) + ",";
        }
        listId += todoIds.get(todoIds.size()-1);
        String query = String.format(SQL_SELECT_LIST_BY_TODO_ID, listId);
        List<TodoAssignEntity> entities = dataHelper.querySQL(query, TodoAssignEntity.class);
        if(CollectionUtils.isEmpty(entities)) {
            throw new TodoAssignNotFoundException();
        }
        return entities;
    }

    @Override
    public void update(TodoAssignEntity entity) throws IOException, DBServiceException {
        String query = String.format(SQL_UPDATE, entity.getState(),
                entity.getTodoId(), entity.getUserId());
        dataHelper.executeSQL(query);
    }

    @Override
    public void remove(TodoAssignEntity entity) throws IOException, DBServiceException {
        String query = String.format(SQL_DELETE, entity.getId());
        dataHelper.executeSQL(query);
    }

    @Override
    public void remove(List<Long> ids) throws IOException, DBServiceException {
        String listId = "";
        for (int i=0;i<ids.size() - 1; i++) {
            listId += ids.get(i) + ",";
        }
        listId += ids.get(ids.size()-1);
        String query = String.format(SQL_DELETE_LIST, listId);
        dataHelper.executeSQL(query);
    }

    @Override
    public void removeByTodoIds(List<Long> ids) throws IOException, DBServiceException {
        String listId = "";
        for (int i=0;i<ids.size() - 1; i++) {
            listId += ids.get(i) + ",";
        }
        listId += ids.get(ids.size()-1);
        String query = String.format(SQL_DELETE_BY_LIST_TODO_ID, listId);
        dataHelper.executeSQL(query);
    }
}
