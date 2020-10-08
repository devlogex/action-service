package com.tnd.pw.action.common.utils;

import com.tnd.pw.action.comments.entity.CommentEntity;
import com.tnd.pw.action.common.representations.CommentRepresentation;
import com.tnd.pw.action.common.representations.CsActionRepresentation;
import com.tnd.pw.action.common.representations.TodoAssignRep;
import com.tnd.pw.action.common.representations.TodoRepresentation;
import com.tnd.pw.action.todos.constants.AssignState;
import com.tnd.pw.action.todos.constants.TodoState;
import com.tnd.pw.action.todos.constants.TodoType;
import com.tnd.pw.action.todos.entity.TodoAssignEntity;
import com.tnd.pw.action.todos.entity.TodoEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class RepresentationBuilder {

    public static CommentRepresentation buildCommentRep(CommentEntity comment, String name) {
        CommentRepresentation representation = new CommentRepresentation();
        representation.setId(String.valueOf(comment.getId()));
        representation.setBelongId(String.valueOf(comment.getBelongId()));
        representation.setWorkspaceId(String.valueOf(comment.getWorkspaceId()));
        representation.setContent(comment.getContent());
        representation.setFiles(comment.getFiles());
        representation.setName(name);
        representation.setCreatedAt(String.valueOf(comment.getCreatedAt()));
        representation.setCreatedBy(String.valueOf(comment.getCreatedBy()));
        return representation;
    }

    public static TodoRepresentation buildTodoRep(TodoEntity todo, List<TodoAssignEntity> todoAssigns) {
        TodoRepresentation rep = new TodoRepresentation();
        rep.setId(todo.getId());
        rep.setBelongId(todo.getBelongId());
        rep.setDescription(todo.getDescription());
        rep.setFiles(todo.getFiles());
        rep.setDueDate(todo.getDueDate());
        rep.setName(todo.getName());
        rep.setType(TodoType.values()[todo.getType()].name());
        rep.setWorkspaceId(todo.getWorkspaceId());
        rep.setState(TodoState.values()[todo.getState()].name());
        rep.setCreatedAt(todo.getCreatedAt());
        rep.setCreatedBy(todo.getCreatedBy());
        List<TodoAssignRep> todoAssignReps = new ArrayList<>();
        if(todoAssigns != null) {
            for (TodoAssignEntity entity : todoAssigns) {
                todoAssignReps.add(buildTodoAssignRep(entity));
            }
        }
        rep.setAssignReps(todoAssignReps);
        return rep;
    }

    private static TodoAssignRep buildTodoAssignRep(TodoAssignEntity entity) {
        TodoAssignRep rep = new TodoAssignRep();
        rep.setId(entity.getId());
        rep.setState(AssignState.values()[entity.getState()].name());
        rep.setTodoId(entity.getTodoId());
        rep.setUserId(entity.getUserId());
        rep.setVerifiedAt(entity.getVerifiedAt());
        return rep;
    }

    public static CsActionRepresentation buildListTodoRep(List<TodoEntity> todos, List<TodoAssignEntity> todoAssigns) {
        List<TodoRepresentation> todoReps = new ArrayList<>();
        if(todoAssigns == null)
            todoAssigns = new ArrayList<>();
        for(TodoEntity todo: todos) {
            List<TodoAssignEntity> assigns = todoAssigns.stream().filter(assign -> assign.getTodoId().compareTo(todo.getId()) == 0).collect(Collectors.toList());
            todoReps.add(buildTodoRep(todo, assigns));
        }
        CsActionRepresentation representation = new CsActionRepresentation();
        representation.setTodoReps(todoReps);
        return representation;
    }
}
