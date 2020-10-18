package com.tnd.pw.action.todos.dao.impl;

import com.tnd.dbservice.common.exception.DBServiceException;
import com.tnd.pw.action.dbservice.DataHelper;
import com.tnd.pw.action.todos.dao.TodoDao;
import com.tnd.pw.action.todos.entity.TodoEntity;
import com.tnd.pw.action.todos.exception.TodoNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.util.List;

public class TodoDaoImpl implements TodoDao {
    @Autowired
    private DataHelper dataHelper;

    private static final String SQL_CREATE =
            "INSERT INTO todo(id, belong_id, workspace_id, name, type, description, files, " +
                    "due_date, state, created_at, created_by) " +
                    "values(%d, %d, %d, '%s', %d, '%s', '%s', %d, %d, %d, %d)";
    private static final String SQL_SELECT_BY_ID =
            "SELECT * FROM todo WHERE id = %d ORDER BY created_at";
    private static final String SQL_SELECT_BY_LIST_ID =
            "SELECT * FROM todo WHERE id IN (%s) ORDER BY created_at";
    private static final String SQL_SELECT_BY_WORKSPACE_ID =
            "SELECT * FROM todo WHERE workspace_id = %d ORDER BY created_at";
    private static final String SQL_SELECT_BY_BELONG_ID =
            "SELECT * FROM todo WHERE belong_id = %d ORDER BY created_at";
    private static final String SQL_UPDATE =
            "UPDATE todo SET name = '%s', description = '%s', files = '%s', due_date = %d, state = %d, completed_at = %d " +
                    "WHERE id = %d";
    private static final String SQL_DELETE =
            "DELETE FROM todo WHERE id = %d";
    private static final String SQL_DELETE_BY_BELONG_ID =
            "DELETE FROM todo WHERE belong_id = %d";
    private static final String SQL_DELETE_LIST =
            "DELETE FROM todo WHERE id IN (%s)";


    @Override
    public void create(TodoEntity entity) throws DBServiceException {
        String query = String.format(SQL_CREATE, entity.getId(), entity.getBelongId(), entity.getWorkspaceId(),
                entity.getName(), entity.getType(), entity.getDescription(),entity.getFiles(), entity.getDueDate(),
                entity.getState(), entity.getCreatedAt(), entity.getCreatedBy());
        dataHelper.executeSQL(query);
    }

    @Override
    public List<TodoEntity> get(TodoEntity entity) throws TodoNotFoundException, DBServiceException {
        String query;
        if(entity.getId() != null) {
            query = String.format(SQL_SELECT_BY_ID, entity.getId());
        }
        else if(entity.getBelongId() != null) {
            query = String.format(SQL_SELECT_BY_BELONG_ID, entity.getBelongId());
        }
        else {
            query = String.format(SQL_SELECT_BY_WORKSPACE_ID, entity.getWorkspaceId());
        }
        List<TodoEntity> entities = dataHelper.querySQL(query, TodoEntity.class);
        if(CollectionUtils.isEmpty(entities)) {
            throw new TodoNotFoundException();
        }
        return entities;
    }

    @Override
    public List<TodoEntity> get(List<Long> ids) throws DBServiceException, TodoNotFoundException {
        String listId = "";
        for (int i=0;i<ids.size() - 1; i++) {
            listId += ids.get(i) + ",";
        }
        listId += ids.get(ids.size()-1);
        String query = String.format(SQL_SELECT_BY_LIST_ID, listId);
        List<TodoEntity> entities = dataHelper.querySQL(query, TodoEntity.class);
        if(CollectionUtils.isEmpty(entities)) {
            throw new TodoNotFoundException();
        }
        return entities;
    }

    @Override
    public void update(TodoEntity entity) throws DBServiceException {
        String query = String.format(SQL_UPDATE, entity.getName(), entity.getDescription(),entity.getFiles(),
                entity.getDueDate(), entity.getState(), entity.getCompletedAt(), entity.getId());
        dataHelper.executeSQL(query);
    }

    @Override
    public void remove(TodoEntity entity) throws DBServiceException {
        String query = "";
        if(entity.getId() != null) {
            query = String.format(SQL_DELETE, entity.getId());
        } else if(entity.getBelongId() != null) {
            query = String.format(SQL_DELETE_BY_BELONG_ID, entity.getBelongId());
        }
        dataHelper.executeSQL(query);
    }

    @Override
    public void remove(List<Long> ids) throws DBServiceException {
        String listId = "";
        for (int i=0;i<ids.size() - 1; i++) {
            listId += ids.get(i) + ",";
        }
        listId += ids.get(ids.size()-1);
        String query = String.format(SQL_DELETE_LIST, listId);
        dataHelper.executeSQL(query);
    }
}
