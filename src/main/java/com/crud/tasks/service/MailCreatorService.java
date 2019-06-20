package com.crud.tasks.service;

import com.crud.tasks.config.AdminConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.ArrayList;
import java.util.List;


@Service
public class MailCreatorService {
    @Autowired
    private AdminConfig adminConfig;

    @Autowired
    @Qualifier("templateEngine")
    private TemplateEngine templateEngine;

    public String buildTrelloCardEmail(String message) {
        List<String> functionality = new ArrayList<>();
        functionality.add("You can manage you tasks");
        functionality.add("Provides connection with Trello Account");
        functionality.add("Application allows sending tasks to Trello");

        Context context = new Context();
        context.setVariable("message", message);
        context.setVariable("tasks_url", "http://localhost:8888/tasks_frontend/");
        context.setVariable("button", "Visit webiste");
        context.setVariable("admin_name", adminConfig.getAdminName());
        context.setVariable("goodbye", "Have a nice day " + adminConfig.getAdminName());
        context.setVariable("preview", "Kodilla CRUD App Informator");
        context.setVariable("company", adminConfig.getCompanyName() + " " + adminConfig.getCompanyEmail());
        context.setVariable("show_button", false);
        context.setVariable("is_friend", true);
        context.setVariable("admin_config", adminConfig);
        context.setVariable("application_functionality", functionality);

        return templateEngine.process("mail/created-trello-card-mail", context);
    }

    public String buildDailyNumberOfTasksemail(String message){
        Context context = new Context();
        context.setVariable("message", message);
        context.setVariable("tasks_url", "http://localhost:8888/tasks_frontend/");
        context.setVariable("button", "Visit webiste");
        context.setVariable("goodbye", "Have a nice day " + adminConfig.getAdminName());
        context.setVariable("preview", "Kodilla CRUD App Informator");
        context.setVariable("admin_config", adminConfig);

        return templateEngine.process("mail/number-of-tasks-daily-mail", context);
    }
}
