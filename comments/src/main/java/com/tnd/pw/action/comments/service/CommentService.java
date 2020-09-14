package com.tnd.pw.action.comments.service;

import com.tnd.dbservice.common.exception.DBServiceException;
import com.tnd.pw.action.comments.entity.CommentEntity;
import com.tnd.pw.action.comments.exception.CommentNotFoundException;

import java.io.IOException;
import java.util.List;

public interface CommentService {
    CommentEntity createComment(CommentEntity entity) throws IOException, DBServiceException;
    List<CommentEntity> getComment(CommentEntity entity) throws DBServiceException, CommentNotFoundException, IOException;
    List<CommentEntity> getComments(List<Long> ids) throws DBServiceException, CommentNotFoundException, IOException;
    void removeComment(CommentEntity entity) throws IOException, DBServiceException;
    void removeComment(List<Long> ids) throws IOException, DBServiceException;
}
