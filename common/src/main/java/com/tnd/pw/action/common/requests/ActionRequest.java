package com.tnd.pw.action.common.requests;

import com.google.gson.annotations.SerializedName;
import com.tnd.common.api.common.base.authens.ProductTokenRequest;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ActionRequest extends ProductTokenRequest {

    @SerializedName("content")
    private String content;
    @SerializedName("files")
    private String files;
    @SerializedName("list_id")
    private List<Long> ids;

    @SerializedName("name")
    private String name;
    @SerializedName("type")
    private String type;
    @SerializedName("description")
    private String description;
    @SerializedName("due_date")
    private Long dueDate;
    @SerializedName("state")
    private Integer state;
    @SerializedName("assignees")
    private List<Long> assignees;
    @SerializedName("action")
    private String action;

}
