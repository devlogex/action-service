package com.tnd.pw.action.runner.config;

import com.tnd.dbservice.sdk.api.DBServiceSdkClient;
import com.tnd.dbservice.sdk.api.impl.DBServiceSdkClientImpl;
import com.tnd.pw.action.comments.dao.CommentDao;
import com.tnd.pw.action.comments.dao.impl.CommentDaoImpl;
import com.tnd.pw.action.comments.service.CommentService;
import com.tnd.pw.action.comments.service.impl.CommentServiceImpl;
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
import com.tnd.pw.config.sdk.ConfigServiceSdkClient;
import com.tnd.pw.config.sdk.impl.ConfigServiceSdkClientImpl;
import com.tnd.pw.development.sdk.DevServiceSdkClient;
import com.tnd.pw.development.sdk.impl.DevServiceSdkClientImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
@PropertySource("classpath:application.properties")
public class ActionConfig {
    public static ExecutorService executor = Executors.newFixedThreadPool(5);
    @Value("${db.host}")
    private String db_host;
    @Value("${db.port}")
    private String db_port;
    @Value("${config.service.host}")
    private String config_service_host;
    @Value("${config.service.port}")
    private String config_service_port;
    @Value("${dev.service.host}")
    private String dev_service_host;
    @Value("${dev.service.port}")
    private String dev_service_port;

    @Bean
    public ConfigServiceSdkClient configServiceSdkClient() {
        return new ConfigServiceSdkClientImpl(config_service_host, Integer.parseInt(config_service_port), 2);
    }

    @Bean
    public DevServiceSdkClient devServiceSdkClient() {
        return new DevServiceSdkClientImpl(dev_service_host, Integer.parseInt(dev_service_port), 2);
    }

    @Bean
    public DBServiceSdkClient dbServiceSdkClient() {
        return new DBServiceSdkClientImpl(db_host,Integer.parseInt(db_port), 1);
    }

    @Bean
    public DataHelper dataHelper(DBServiceSdkClient dbServiceSdkClient) {
        return new DataHelper(dbServiceSdkClient);
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
