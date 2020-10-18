package com.tnd.pw.action.comments.service.impl;

import com.tnd.common.api.common.Utils.GenUID;
import com.tnd.dbservice.common.exception.DBServiceException;
import com.tnd.pw.action.comments.dao.CommentDao;
import com.tnd.pw.action.comments.entity.CommentEntity;
import com.tnd.pw.action.comments.exception.CommentNotFoundException;
import com.tnd.pw.action.comments.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.List;

public class CommentServiceImpl  implements CommentService {

    @Autowired
    private CommentDao commentDao;

    @Override
    public CommentEntity createComment(CommentEntity entity) throws DBServiceException {
        entity.setId(GenUID.genIdByParent(entity.getBelongId()));
        entity.setCreatedAt(System.currentTimeMillis());
        if(entity.getFiles() == null) {
            entity.setFiles("");
        }
        commentDao.create(entity);
        return entity;
    }

    @Override
    public List<CommentEntity> getComment(CommentEntity entity) throws DBServiceException, CommentNotFoundException {
        return commentDao.get(entity);
    }

    @Override
    public List<CommentEntity> getComments(List<Long> ids) throws DBServiceException, CommentNotFoundException {
        return commentDao.get(ids);
    }

    @Override
    public void removeComment(CommentEntity entity) throws DBServiceException {
        commentDao.remove(entity);
    }

    @Override
    public void removeComment(List<Long> ids) throws DBServiceException {
        commentDao.remove(ids);
    }

    @Override
    public List<CommentEntity> getCommentByBelongIds(List<Long> belongIds) throws DBServiceException, CommentNotFoundException {
        return commentDao.getByBelongIds(belongIds);
    }
}
