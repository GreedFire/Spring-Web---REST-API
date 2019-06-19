package com.crud.tasks.service;

import com.crud.tasks.config.AdminConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;


@Service
public class MailCreatorService {
    @Autowired
    private AdminConfig adminConfig;

    @Autowired
    @Qualifier("templateEngine")
    private TemplateEngine templateEngine;

    public String buildTrelloCardEmail(String message) {
        Context context = new Context();
        context.setVariable("message", message);
        context.setVariable("tasks_url", "http://localhost:8888/tasks_frontend/");
        context.setVariable("button", "Visit webiste");
        context.setVariable("admin_name", adminConfig.getAdminName());
        context.setVariable("goodbye", "Have a nice day " + adminConfig.getAdminName());
        context.setVariable("preview", "Kodilla CRUD App Informator");
        context.setVariable("company", adminConfig.getCompanyName() + " " + adminConfig.getCompanyEmail());

        return templateEngine.process("mail/created-trello-card-mail", context);
    }
}
