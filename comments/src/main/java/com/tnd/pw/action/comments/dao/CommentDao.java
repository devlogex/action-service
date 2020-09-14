package com.tnd.pw.action.comments.dao;

import com.tnd.dbservice.common.exception.DBServiceException;
import com.tnd.pw.action.comments.entity.CommentEntity;
import com.tnd.pw.action.comments.exception.CommentNotFoundException;

import java.io.IOException;
import java.util.List;

public interface CommentDao {
    void create(CommentEntity entity) throws IOException, DBServiceException;
    List<CommentEntity> get(CommentEntity entity) throws CommentNotFoundException, IOException, DBServiceException;
    List<CommentEntity> get(List<Long> ids) throws IOException, DBServiceException, CommentNotFoundException;
    void remove(CommentEntity entity) throws IOException, DBServiceException;
    void remove(List<Long> ids) throws IOException, DBServiceException;
}
