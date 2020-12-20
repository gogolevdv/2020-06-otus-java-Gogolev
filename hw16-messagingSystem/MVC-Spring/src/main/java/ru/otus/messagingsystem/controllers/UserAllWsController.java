package ru.otus.messagingsystem.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import ru.otus.messagingsystem.core.model.User;
import ru.otus.messagingsystem.front.FrontendService;

@Controller
public class UserAllWsController {
    private static final Logger logger = LoggerFactory.getLogger(UserAllWsController.class);

    private final SimpMessagingTemplate template;
    private final FrontendService frontendService;

    public UserAllWsController(SimpMessagingTemplate template, FrontendService frontendService) {
        this.template = template;
        this.frontendService = frontendService;
    }

    @MessageMapping("/user/list")
    public void getAllUsers() {

        frontendService.getAllUsers(userData -> {
            this.template.convertAndSend("/topic/userList", userData);
        });
    }

    @MessageMapping("/user/save")
    public void saveUser(User user) {
        logger.info("saveUser: user = " + user.toString());
        frontendService.saveUser(user, status -> {
            this.template.convertAndSend("/topic/saveUserOperationStatus", status);
        });
    }
}
