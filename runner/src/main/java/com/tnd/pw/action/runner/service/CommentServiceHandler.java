package com.tnd.pw.action.runner.service;

import com.tnd.dbservice.common.exception.DBServiceException;
import com.tnd.pw.action.comments.exception.CommentNotFoundException;
import com.tnd.pw.action.common.representations.CommentRepresentation;
import com.tnd.pw.action.common.representations.CsActionRepresentation;
import com.tnd.pw.action.common.requests.ActionRequest;
import com.tnd.pw.action.common.requests.UserRequest;
import com.tnd.pw.action.runner.exception.ConfigServiceFailedException;
import com.tnd.pw.action.runner.exception.NoPermissionException;

public interface CommentServiceHandler {
    CommentRepresentation addComment(ActionRequest request) throws DBServiceException, ConfigServiceFailedException;
    CommentRepresentation removeComment(ActionRequest request) throws DBServiceException, CommentNotFoundException, NoPermissionException;
    CsActionRepresentation getComments(ActionRequest request) throws DBServiceException, ConfigServiceFailedException;

    CsActionRepresentation getCommentOfUser(UserRequest request) throws DBServiceException, ConfigServiceFailedException;
}
