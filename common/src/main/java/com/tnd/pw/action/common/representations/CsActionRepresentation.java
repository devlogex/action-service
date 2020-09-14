package com.tnd.pw.action.common.representations;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CsActionRepresentation  implements Serializable {
    private static final long serialVersionUID = 1L;

    @SerializedName("list_comments")
    private List<CommentRepresentation> commentReps;
    @SerializedName("list_todos")
    private List<TodoRepresentation> todoReps;

    public CsActionRepresentation(List<CommentRepresentation> commentReps) {
        this.commentReps = commentReps;
    }
}
