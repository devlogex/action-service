package com.tnd.pw.action.runner.config;

import com.tnd.pw.action.api.ActionApiClient;
import com.tnd.pw.action.comments.dao.CommentDao;
import com.tnd.pw.action.comments.dao.impl.CommentDaoImpl;
import com.tnd.pw.action.comments.service.CommentService;
import com.tnd.pw.action.comments.service.impl.CommentServiceImpl;
import com.tnd.pw.action.dbservice.DBServiceApiClient;
import com.tnd.pw.action.dbservice.DataHelper;
import com.tnd.pw.action.runner.handler.ActionHandler;
import com.tnd.pw.action.runner.handler.CommentHandler;
import com.tnd.pw.action.runner.handler.TodoHandler;
import com.tnd.pw.action.runner.service.CommentServiceHandler;
import com.tnd.pw.action.runner.service.TodoServiceHandler;
import com.tnd.pw.action.runner.service.impl.CommentServiceHandlerImpl;
import com.tnd.pw.action.runner.service.impl.TodoServiceHandlerImpl;
import com.tnd.pw.action.todos.dao.TodoAssignDao;
import com.tnd.pw.action.todos.dao.TodoDao;
import com.tnd.pw.action.todos.dao.impl.TodoAssignDaoImpl;
import com.tnd.pw.action.todos.dao.impl.TodoDaoImpl;
import com.tnd.pw.action.todos.service.TodoService;
import com.tnd.pw.action.todos.service.impl.TodoServiceImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:application.properties")
public class ActionConfig {
    @Value("${db.url}")
    private String db_url;

    @Bean
    public ActionApiClient apiClient() {
        return new ActionApiClient();
    }

    @Bean
    public DBServiceApiClient dbServiceApiClient() {
        return new DBServiceApiClient();
    }

    @Bean
    public DataHelper dataHelper(DBServiceApiClient dbServiceApiClient) {
        return new DataHelper(db_url, dbServiceApiClient);
    }

    @Bean
    public CommentDao commentDao() {
        return new CommentDaoImpl();
    }

    @Bean
    public CommentService commentService() {
        return new CommentServiceImpl();
    }

    @Bean
    public TodoDao todoDao() {
        return new TodoDaoImpl();
    }

    @Bean
    public TodoAssignDao todoAssignDao() {
        return new TodoAssignDaoImpl();
    }

    @Bean
    public TodoService todoService() {
        return new TodoServiceImpl();
    }

    @Bean
    public TodoServiceHandler todoServiceHandler() {
        return new TodoServiceHandlerImpl();
    }

    @Bean
    public CommentServiceHandler commentServiceHandler() {
        return new CommentServiceHandlerImpl();
    }

    @Bean
    public TodoHandler todoHandler() {
        return new TodoHandler();
    }

    @Bean
    public CommentHandler commentHandler() {
        return new CommentHandler();
    }

    @Bean
    public ActionHandler actionHandler() {
        return new ActionHandler();
    }
}
