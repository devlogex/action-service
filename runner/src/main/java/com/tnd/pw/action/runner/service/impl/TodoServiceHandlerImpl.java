package com.tnd.pw.action.runner.service.impl;

import com.tnd.common.api.common.base.BaseRequest;
import com.tnd.common.api.common.base.BaseResponse;
import com.tnd.dbservice.common.exception.DBServiceException;
import com.tnd.pw.action.api.ActionApiClient;
import com.tnd.pw.action.api.requests.ConfigServiceRequest;
import com.tnd.pw.action.api.responses.ConfigServiceResponse;
import com.tnd.pw.action.api.responses.UserRepresentation;
import com.tnd.pw.action.comments.entity.CommentEntity;
import com.tnd.pw.action.common.constants.VerifyTodo;
import com.tnd.pw.action.common.representations.CsActionRepresentation;
import com.tnd.pw.action.common.representations.TodoRepresentation;
import com.tnd.pw.action.common.requests.ActionRequest;
import com.tnd.pw.action.common.requests.UserRequest;
import com.tnd.pw.action.common.utils.GsonUtils;
import com.tnd.pw.action.common.utils.RepresentationBuilder;
import com.tnd.pw.action.runner.exception.CallApiFailException;
import com.tnd.pw.action.runner.service.TodoServiceHandler;
import com.tnd.pw.action.todos.constants.AssignState;
import com.tnd.pw.action.todos.constants.TodoState;
import com.tnd.pw.action.todos.constants.TodoType;
import com.tnd.pw.action.todos.entity.TodoAssignEntity;
import com.tnd.pw.action.todos.entity.TodoEntity;
import com.tnd.pw.action.todos.exception.InconsistentStateException;
import com.tnd.pw.action.todos.exception.TodoAssignNotFoundException;
import com.tnd.pw.action.todos.exception.TodoNotFoundException;
import com.tnd.pw.action.todos.service.TodoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

public class TodoServiceHandlerImpl implements TodoServiceHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(TodoServiceHandlerImpl.class);

    @Autowired
    private TodoService todoService;
    @Autowired
    private ActionApiClient apiClient;

    @Override
    public TodoRepresentation addTodo(ActionRequest request) throws IOException, DBServiceException {
        TodoEntity todo = todoService.createTodo(
                TodoEntity.builder()
                        .belongId(request.getId())
                        .workspaceId(request.getPayload().getWorkspaceId())
                        .name(request.getName())
                        .type(TodoType.valueOf(request.getType()).ordinal())
                        .description(request.getDescription())
                        .files(request.getFiles())
                        .dueDate(request.getDueDate())
                        .createdBy(request.getPayload().getUserId())
                        .build()
        );
        List<TodoAssignEntity> todoAssigns = new ArrayList<>();
        if(request.getAssignees() != null) {
            for(Long assignId: request.getAssignees()) {
                TodoAssignEntity todoAssign = todoService.createTodoAssign(
                        TodoAssignEntity.builder()
                                .todoId(todo.getId())
                                .userId(assignId)
                                .workspaceId(todo.getWorkspaceId())
                                .type(todo.getType())
                                .build());
                todoAssigns.add(todoAssign);
            }
        }
        return RepresentationBuilder.buildTodoRep(todo,todoAssigns);
    }

    @Override
    public TodoRepresentation removeTodo(ActionRequest request) throws DBServiceException, IOException, TodoNotFoundException {
        if(request.getId() != null) {
            todoService.removeTodoAssign(TodoAssignEntity.builder().todoId(request.getId()).build());
            todoService.removeTodo(TodoEntity.builder().id(request.getId()).build());
        } else if(request.getBelongId()!= null) {
            List<TodoEntity> todos = todoService.getTodo(TodoEntity.builder().belongId(request.getBelongId()).build());
            List<Long> todoIds = todos.stream().map(TodoEntity::getId).collect(Collectors.toList());
            todoService.removeTodoAssignByTodoIds(todoIds);
            todoService.removeTodo(TodoEntity.builder().belongId(request.getBelongId()).build());
        }
        return null;
    }

    @Override
    public CsActionRepresentation getTodos(ActionRequest request) throws DBServiceException, IOException {
        List<TodoEntity> todos = null;
        try {
            todos = todoService.getTodo(
                    TodoEntity.builder()
                            .belongId(request.getId())
                            .build()
            );
            List<Long> todoIds = todos.stream().map(TodoEntity::getId).collect(Collectors.toList());
            List<TodoAssignEntity> todoAssigns = todoService.getTodoAssign(todoIds);
            return RepresentationBuilder.buildListTodoRep(todos, todoAssigns);

        } catch (TodoAssignNotFoundException e) {
            LOGGER.info("TodoAssignNotFoundException");
            return RepresentationBuilder.buildListTodoRep(todos, null);
        } catch (TodoNotFoundException e) {
            LOGGER.info("TodoNotFoundException");
            return null;
        }
    }

    @Override
    public CsActionRepresentation getTodoOfUser(UserRequest request) throws DBServiceException, IOException {
        List<TodoEntity> todos = null;
        try {
            List<TodoAssignEntity> todoAssigns = todoService.getTodoAssign(
                    TodoAssignEntity.builder()
                            .userId(request.getPayload().getUserId())
                            .workspaceId(request.getPayload().getWorkspaceId())
                            .build()
            );
            List<Long> todoIds = todoAssigns.stream().map(TodoAssignEntity::getTodoId).distinct().collect(Collectors.toList());
            todos = todoService.getTodo(todoIds);
            return RepresentationBuilder.buildListTodoRep(todos, todoAssigns);
        } catch (TodoAssignNotFoundException e) {
            LOGGER.info("TodoAssignNotFoundException");
            return null;
        } catch (TodoNotFoundException e) {
            LOGGER.info("TodoNotFoundException");
            return null;
        }
    }

    @Override
    public TodoRepresentation updateTodo(ActionRequest request) throws DBServiceException, TodoNotFoundException, IOException, TodoAssignNotFoundException {
        TodoEntity todoEntity = todoService.getTodo(
                TodoEntity.builder()
                        .id(request.getId())
                        .build()
        ).get(0);
        if(request.getDescription() != null) {
            todoEntity.setDescription(request.getDescription());
        }
        if(request.getDueDate() != null) {
            todoEntity.setDueDate(request.getDueDate());
        }
        if(request.getName() != null) {
            todoEntity.setName(request.getName());
        }
        if(request.getFiles() != null) {
            todoEntity.setFiles(request.getFiles());
        }
        if(request.getAssignees() != null) {
            try {
                List<TodoAssignEntity> todoAssigns = todoService.getTodoAssign(
                        TodoAssignEntity.builder()
                                .todoId(todoEntity.getId())
                                .build()
                );
                List<Long> userIds = todoAssigns.stream().map(TodoAssignEntity::getUserId).collect(Collectors.toList());
                HashSet<Long> oldIds = new HashSet<>();
                HashSet<Long> newIds = new HashSet<>();
                newIds.addAll(request.getAssignees());
                oldIds.addAll(userIds);
                for(Long id: newIds) {
                    if(!oldIds.contains(id)) {
                        todoService.createTodoAssign(
                                TodoAssignEntity.builder()
                                        .todoId(todoEntity.getId())
                                        .workspaceId(todoEntity.getWorkspaceId())
                                        .userId(id)
                                        .type(todoEntity.getType())
                                        .build()
                        );
                    }
                }
                List<Long> removeIds = new ArrayList<>();
                for(Long id: oldIds) {
                    if(!newIds.contains(id)) {
                        removeIds.add(id);
                    }
                }
                if(removeIds.size() > 0) {
                    todoService.removeTodoAssign(removeIds);
                }
            } catch (TodoAssignNotFoundException e) {
                for(Long id: request.getAssignees()) {
                    todoService.createTodoAssign(
                            TodoAssignEntity.builder()
                                    .todoId(todoEntity.getId())
                                    .workspaceId(todoEntity.getWorkspaceId())
                                    .userId(id)
                                    .type(todoEntity.getType())
                                    .build()
                    );
                }
            }
            if(todoEntity.getState() == TodoState.COMPLETE.ordinal()) {
                todoEntity.setState(TodoState.PENDING.ordinal());
            }
        }
        todoService.updateTodo(todoEntity);
        List<TodoAssignEntity> assignEntities = todoService.getTodoAssign(TodoAssignEntity.builder().todoId(todoEntity.getId()).build());
        return RepresentationBuilder.buildTodoRep(todoEntity, assignEntities);
    }

    @Override
    public TodoRepresentation verifyTodo(ActionRequest request) throws InconsistentStateException, IOException, DBServiceException, TodoNotFoundException, TodoAssignNotFoundException {
        TodoAssignEntity todoAssign = todoService.getTodoAssign(
                TodoAssignEntity.builder()
                        .id(request.getId())
                        .build()
        ).get(0);
        VerifyTodo action = VerifyTodo.valueOf(request.getAction());
        switch (action) {
            case APPROVE:
                todoAssign.approval();
                break;
            case COMPLETE:
                todoAssign.complete();
                break;
            case REJECT:
                todoAssign.reject();
                break;
            case APPROVE_WITH_CHANGE:
                todoAssign.approvalWithChange();
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + request.getAction());
        }
        todoService.updateTodoAssign(todoAssign);
        TodoEntity todoEntity = todoService.getTodo(
                TodoEntity.builder()
                        .id(todoAssign.getTodoId())
                        .build()
        ).get(0);
        List<TodoAssignEntity> assignEntities = todoService.getTodoAssign(
                    TodoAssignEntity.builder()
                            .todoId(todoEntity.getId())
                            .build()
        );
        long countPending = assignEntities.stream().filter(assign->assign.getState()==AssignState.PENDING.ordinal()).count();
        if(countPending == 0 && todoEntity.getState() == TodoState.PENDING.ordinal()) {
            todoEntity.setState(TodoState.COMPLETE.ordinal());
            todoService.updateTodo(todoEntity);
        }

        return RepresentationBuilder.buildTodoRep(todoEntity, assignEntities);
    }

}
