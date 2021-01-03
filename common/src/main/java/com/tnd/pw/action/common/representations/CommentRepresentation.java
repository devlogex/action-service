package com.tnd.pw.action.common.representations;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class CommentRepresentation implements Serializable {
    private static final long serialVersionUID = 1L;

    @SerializedName("id")
    private String id;
    @SerializedName("name")
    private String name;
    @SerializedName("belong_id")
    private String belongId;
    @SerializedName("workspace_id")
    private String workspaceId;
    @SerializedName("content")
    private String content;
    @SerializedName("files")
    private String files;
    @SerializedName("created_at")
    private String createdAt;
    @SerializedName("created_by")
    private String createdBy;
}
