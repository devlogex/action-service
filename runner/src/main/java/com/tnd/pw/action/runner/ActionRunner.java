package com.tnd.pw.action.runner;

import com.tnd.com.ioc.SpringApplicationContext;
import com.tnd.common.api.server.CommonServer;
import com.tnd.pw.action.runner.config.ActionConfig;
import com.tnd.pw.action.runner.handler.ActionHandler;
import com.tnd.pw.action.runner.handler.CommentHandler;
import com.tnd.pw.action.runner.handler.TodoHandler;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ActionRunner {

    public static void main(String args[]) throws Exception {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.register(ActionConfig.class);
        context.refresh();
        SpringApplicationContext.setShareApplicationContext(context);

        CommonServer commonServer = new CommonServer();
        commonServer.register(SpringApplicationContext.getBean(TodoHandler.class));
        commonServer.register(SpringApplicationContext.getBean(CommentHandler.class));
        commonServer.register(SpringApplicationContext.getBean(ActionHandler.class));

        String port = System.getenv("PORT");
        if (port == null) {
            commonServer.initServlet(8004);
        } else {
            commonServer.initServlet(Integer.parseInt(port));
        }
        commonServer.startServer();
    }
}
