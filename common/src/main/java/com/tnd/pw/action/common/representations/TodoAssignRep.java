package com.tnd.pw.action.common.representations;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class TodoAssignRep implements Serializable {
    private static final long serialVersionUID = 1L;

    @SerializedName("id")
    private Long id;
    @SerializedName("todo_id")
    private Long todoId;
    @SerializedName("user_id")
    private Long userId;
    @SerializedName("state")
    private String state;
    @SerializedName("verified_at")
    private Long verifiedAt;
}
