package com.tnd.pw.action.runner.service.impl;

import com.tnd.common.api.common.base.BaseRequest;
import com.tnd.common.api.common.base.BaseResponse;
import com.tnd.dbservice.common.exception.DBServiceException;
import com.tnd.pw.action.api.ActionApiClient;
import com.tnd.pw.action.api.requests.ConfigServiceRequest;
import com.tnd.pw.action.api.responses.ConfigServiceResponse;
import com.tnd.pw.action.api.responses.UserRepresentation;
import com.tnd.pw.action.comments.entity.CommentEntity;
import com.tnd.pw.action.comments.exception.CommentNotFoundException;
import com.tnd.pw.action.comments.service.CommentService;
import com.tnd.pw.action.common.representations.CommentRepresentation;
import com.tnd.pw.action.common.representations.CsActionRepresentation;
import com.tnd.pw.action.common.requests.ActionRequest;
import com.tnd.pw.action.common.requests.UserRequest;
import com.tnd.pw.action.common.utils.GsonUtils;
import com.tnd.pw.action.common.utils.RepresentationBuilder;
import com.tnd.pw.action.runner.exception.CallApiFailException;
import com.tnd.pw.action.runner.exception.NoPermissionException;
import com.tnd.pw.action.runner.service.CommentServiceHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CommentServiceHandlerImpl implements CommentServiceHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(CommentServiceHandlerImpl.class);

    @Autowired
    private CommentService commentService;
    @Autowired
    private ActionApiClient apiClient;

    @Override
    public CommentRepresentation addComment(ActionRequest request) throws IOException, DBServiceException, CallApiFailException {
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

    private String getName(CommentEntity comment, BaseRequest request) throws IOException, CallApiFailException {
        ConfigServiceRequest configServiceRequest = ConfigServiceRequest.builder()
                .id(comment.getCreatedBy())
                .build();
        configServiceRequest.setToken(request.getToken());
        BaseResponse<ConfigServiceResponse> response = apiClient.getUserProfile(
                configServiceRequest
        );
        if(response.getStatusCode() != 200) {
            throw new CallApiFailException("Call config-service to get user_profile failed !");
        }
        UserRepresentation userProfile = GsonUtils.getGson().fromJson(GsonUtils.convertToString(response.getData()), ConfigServiceResponse.class).getUserProfiles().get(0);
        String name = String.format("%s %s",userProfile.getFirstName(), userProfile.getLastName());
        return name;
    }

    @Override
    public CommentRepresentation removeComment(ActionRequest request) throws DBServiceException, IOException, CommentNotFoundException, NoPermissionException {
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
    public CsActionRepresentation getComments(ActionRequest request) throws DBServiceException, IOException, CallApiFailException {
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
    public CsActionRepresentation getCommentOfUser(UserRequest request) throws DBServiceException, IOException, CallApiFailException {
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
