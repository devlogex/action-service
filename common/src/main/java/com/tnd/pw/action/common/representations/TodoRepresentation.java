package com.tnd.pw.action.common.representations;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
public class TodoRepresentation implements Serializable {
    private static final long serialVersionUID = 1L;

    @SerializedName("id")
    private Long id;
    @SerializedName("belong_id")
    private Long belongId;
    @SerializedName("workspace_id")
    private Long workspaceId;
    @SerializedName("name")
    private String name;
    @SerializedName("type")
    private String type;
    @SerializedName("description")
    private String description;
    @SerializedName("files")
    private String files;
    @SerializedName("due_date")
    private Long dueDate;
    @SerializedName("state")
    private String state;
    @SerializedName("completed_at")
    private Long completedAt;
    @SerializedName("created_at")
    private Long createdAt;
    @SerializedName("created_by")
    private Long createdBy;
    @SerializedName("list_assign")
    private List<TodoAssignRep> assignReps;
    @SerializedName("list_comments")
    private List<CommentRepresentation> commentReps;
}
