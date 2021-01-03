package com.tnd.pw.action.todos.entity;

import com.google.gson.annotations.SerializedName;
import com.tnd.pw.action.todos.constants.AssignState;
import com.tnd.pw.action.todos.constants.TodoType;
import com.tnd.pw.action.todos.exception.InconsistentStateException;
import lombok.*;

import java.io.Serializable;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class TodoAssignEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Setter
    @SerializedName("id")
    private Long id;
    @Setter
    @SerializedName("todo_id")
    private Long todoId;
    @Setter
    @SerializedName("user_id")
    private Long userId;
    @Setter
    @SerializedName("workspace_id")
    private Long workspaceId;
    @Setter
    @SerializedName("type")
    private Integer type;
    @SerializedName("state")
    private Integer state;
    @Setter
    @SerializedName("verified_at")
    private Long verifiedAt;

    public void complete() throws InconsistentStateException {
        if(this.type != TodoType.TODO.ordinal() || this.state != AssignState.PENDING.ordinal()) {
            throw new InconsistentStateException();
        }
        this.state = AssignState.COMPLETE.ordinal();
    }

    public void reject() throws InconsistentStateException {
        if(this.type != TodoType.APPROVAL.ordinal() || this.state != AssignState.PENDING.ordinal()) {
            throw new InconsistentStateException();
        }
        this.state = AssignState.REJECT.ordinal();
    }

    public void approval() throws InconsistentStateException {
        if(this.type != TodoType.APPROVAL.ordinal() || this.state != AssignState.PENDING.ordinal()) {
            throw new InconsistentStateException();
        }
        this.state = AssignState.APPROVAL.ordinal();
    }

    public void approvalWithChange() throws InconsistentStateException {
        if(this.type != TodoType.APPROVAL.ordinal() || this.state != AssignState.PENDING.ordinal()) {
            throw new InconsistentStateException();
        }
        this.state = AssignState.APPROVAL_WITH_CHANGE.ordinal();
    }

    public void create() {
        this.state = AssignState.PENDING.ordinal();
    }
}
