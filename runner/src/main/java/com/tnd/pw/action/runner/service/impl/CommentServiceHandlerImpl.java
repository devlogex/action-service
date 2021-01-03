package com.tnd.pw.action.runner.service.impl;

import com.tnd.common.api.common.base.BaseRequest;
import com.tnd.common.api.common.base.BaseResponse;
import com.tnd.dbservice.common.exception.DBServiceException;
import com.tnd.pw.action.comments.entity.CommentEntity;
import com.tnd.pw.action.comments.exception.CommentNotFoundException;
import com.tnd.pw.action.comments.service.CommentService;
import com.tnd.pw.action.common.representations.CommentRepresentation;
import com.tnd.pw.action.common.representations.CsActionRepresentation;
import com.tnd.pw.action.common.requests.ActionRequest;
import com.tnd.pw.action.common.requests.UserRequest;
import com.tnd.pw.action.common.utils.RepresentationBuilder;
import com.tnd.pw.action.runner.exception.ConfigServiceFailedException;
import com.tnd.pw.action.runner.exception.NoPermissionException;
import com.tnd.pw.action.runner.service.CommentServiceHandler;
import com.tnd.pw.config.common.representations.CsUserRepresentation;
import com.tnd.pw.config.common.representations.UserRepresentation;
import com.tnd.pw.config.sdk.ConfigServiceSdkClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class CommentServiceHandlerImpl implements CommentServiceHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(CommentServiceHandlerImpl.class);

    @Autowired
    private CommentService commentService;
    @Autowired
    private ConfigServiceSdkClient configServiceSdkClient;

    @Override
    public CommentRepresentation addComment(ActionRequest request) throws DBServiceException, ConfigServiceFailedException {
        CommentEntity comment = commentService.createComment(
                CommentEntity.builder()
                        .belongId(request.getId())
                        .workspaceId(request.getPayload().getWorkspaceId())
                        .content(request.getContent())
                        .files(request.getFiles())
                        .createdBy(request.getPayload().getUserId())
                        .build()
        );

        String name = getName(comment, request);
        return RepresentationBuilder.buildCommentRep(comment, name);
    }

    private String getName(CommentEntity comment, BaseRequest request) throws ConfigServiceFailedException {
        BaseResponse<CsUserRepresentation> response = configServiceSdkClient.getUserProfile(comment.getCreatedBy());
        if(response.getResponseCode() < 1) {
            throw new ConfigServiceFailedException("Call config-service to get user_profile failed !");
        }
        UserRepresentation userProfile = response.getData().getUserProfiles().get(0);
        String name = String.format("%s %s",userProfile.getFirstName(), userProfile.getLastName());
        return name;
    }

    @Override
    public CommentRepresentation removeComment(ActionRequest request) throws DBServiceException, CommentNotFoundException, NoPermissionException {
        if(request.getId() != null) {
            CommentEntity commentEntity = commentService.getComment(
                    CommentEntity.builder()
                            .id(request.getId())
                            .build()
            ).get(0);
            if (commentEntity.getCreatedBy().compareTo(request.getPayload().getUserId()) != 0) {
                throw new NoPermissionException("This comment is not your !");
            }
            commentService.removeComment(commentEntity);
        } else if(request.getBelongId() != null) {
            commentService.removeComment(CommentEntity.builder().belongId(request.getBelongId()).build());
        }
        return null;
    }

    @Override
    public CsActionRepresentation getComments(ActionRequest request) throws DBServiceException, ConfigServiceFailedException {
        List<CommentRepresentation> commentReps = new ArrayList<>();
        try {
            List<CommentEntity> comments = commentService.getComment(
                    CommentEntity.builder().belongId(request.getId()).build()
            );
            for(CommentEntity comment: comments) {
                commentReps.add(RepresentationBuilder.buildCommentRep(comment, getName(comment, request)));
            }
        } catch (CommentNotFoundException e) {
            return new CsActionRepresentation(commentReps);
        }
        return new CsActionRepresentation(commentReps);
    }

    @Override
    public CsActionRepresentation getCommentOfUser(UserRequest request) throws DBServiceException, ConfigServiceFailedException {
        List<CommentRepresentation> commentReps = new ArrayList<>();
        try {
            List<CommentEntity> comments = commentService.getComment(
                    CommentEntity.builder()
                            .workspaceId(request.getPayload().getWorkspaceId())
                            .createdBy(request.getPayload().getUserId())
                            .build()
            );
            for(CommentEntity comment: comments) {
                commentReps.add(RepresentationBuilder.buildCommentRep(comment, getName(comment, request)));
            }
        } catch (CommentNotFoundException e) {
            return new CsActionRepresentation(commentReps);
        }
        return new CsActionRepresentation(commentReps);
    }
}
