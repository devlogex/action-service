package com.tnd.pw.action.comments.dao.impl;

import com.tnd.dbservice.common.exception.DBServiceException;
import com.tnd.pw.action.comments.dao.CommentDao;
import com.tnd.pw.action.comments.entity.CommentEntity;
import com.tnd.pw.action.comments.exception.CommentNotFoundException;
import com.tnd.pw.action.dbservice.DataHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.util.List;

public class CommentDaoImpl implements CommentDao {
    @Autowired
    private DataHelper dataHelper;

    private static final String SQL_CREATE =
            "INSERT INTO comment(id, belong_id, workspace_id, content, files, created_at, created_by) " +
                    "values(%d, %d, %d, '%s', '%s', %d, %d)";
    private static final String SQL_SELECT_BY_ID =
            "SELECT * FROM comment WHERE id = %d ORDER BY created_at";
    private static final String SQL_SELECT_BY_LIST_ID =
            "SELECT * FROM comment WHERE id IN (%s) ORDER BY created_at";
    private static final String SQL_SELECT_BY_LIST_BELONG_ID =
            "SELECT * FROM comment WHERE belong_id IN (%s) ORDER BY created_at";
    private static final String SQL_SELECT_BY_BELONG_ID =
            "SELECT * FROM comment WHERE belong_id = %d ORDER BY created_at";
    private static final String SQL_SELECT_BY_CREATED_BY_AND_WORKSPACE_ID =
            "SELECT * FROM comment WHERE created_by = %d AND workspace_id = %d ORDER BY created_at";
    private static final String SQL_DELETE =
            "DELETE FROM comment WHERE id = %d";
    private static final String SQL_DELETE_LIST =
            "DELETE FROM comment WHERE id IN (%s)";
    private static final String SQL_DELETE_BY_BELONG_ID =
            "DELETE FROM comment WHERE belong_id = %d";


    @Override
    public void create(CommentEntity entity) throws DBServiceException {
        String query = String.format(SQL_CREATE, entity.getId(), entity.getBelongId(), entity.getWorkspaceId(),
                entity.getContent(), entity.getFiles(), entity.getCreatedAt(), entity.getCreatedBy());
        dataHelper.executeSQL(query);
    }

    @Override
    public List<CommentEntity> get(CommentEntity entity) throws CommentNotFoundException, DBServiceException {
        String query = "";
        if(entity.getId() != null) {
            query = String.format(SQL_SELECT_BY_ID, entity.getId());
        }
        else if(entity.getBelongId() != null) {
            query = String.format(SQL_SELECT_BY_BELONG_ID, entity.getBelongId());
        }
        else if(entity.getCreatedBy() != null && entity.getWorkspaceId() != null) {
            query = String.format(SQL_SELECT_BY_CREATED_BY_AND_WORKSPACE_ID, entity.getCreatedBy(), entity.getWorkspaceId());
        }
        List<CommentEntity> entities = dataHelper.querySQL(query, CommentEntity.class);
        if(CollectionUtils.isEmpty(entities)) {
            throw new CommentNotFoundException();
        }
        return entities;
    }

    @Override
    public List<CommentEntity> get(List<Long> ids) throws DBServiceException, CommentNotFoundException {
        String listId = "";
        for (int i=0;i<ids.size() - 1; i++) {
            listId += ids.get(i) + ",";
        }
        listId += ids.get(ids.size()-1);
        String query = String.format(SQL_SELECT_BY_LIST_ID, listId);
        List<CommentEntity> entities = dataHelper.querySQL(query, CommentEntity.class);
        if(CollectionUtils.isEmpty(entities)) {
            throw new CommentNotFoundException();
        }
        return entities;
    }

    @Override
    public void remove(CommentEntity entity) throws DBServiceException {
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

    @Override
    public List<CommentEntity> getByBelongIds(List<Long> ids) throws DBServiceException, CommentNotFoundException {
        String listId = "";
        for (int i=0;i<ids.size() - 1; i++) {
            listId += ids.get(i) + ",";
        }
        listId += ids.get(ids.size()-1);
        String query = String.format(SQL_SELECT_BY_LIST_BELONG_ID, listId);
        List<CommentEntity> entities = dataHelper.querySQL(query, CommentEntity.class);
        if(CollectionUtils.isEmpty(entities)) {
            throw new CommentNotFoundException();
        }
        return entities;
    }
}
