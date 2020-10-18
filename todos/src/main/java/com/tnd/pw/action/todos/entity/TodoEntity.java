package com.tnd.pw.action.todos.entity;

import com.google.gson.annotations.SerializedName;
import lombok.*;

import java.io.Serializable;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class TodoEntity implements Serializable {
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
    private Integer type;
    @SerializedName("description")
    private String description;
    @SerializedName("files")
    private String files;
    @SerializedName("due_date")
    private Long dueDate;
    @SerializedName("state")
    private Integer state;
    @SerializedName("completed_at")
    private Long completedAt;
    @SerializedName("created_at")
    private Long createdAt;
    @SerializedName("created_by")
    private Long createdBy;
}