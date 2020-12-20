package ru.otus.messagingsystem.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import ru.otus.messagingsystem.front.FrontendService;

@Controller
public class UserWsController {
    private static final Logger logger = LoggerFactory.getLogger(UserWsController.class);
    private final SimpMessagingTemplate template;
    private final FrontendService frontendService;

    public UserWsController(SimpMessagingTemplate template, FrontendService frontendService) {
        this.template = template;
        this.frontendService = frontendService;
    }

    @MessageMapping("/message.{id}")
    public void getUser(@DestinationVariable int id) {
        logger.info("Controller message id : {}", id);
        frontendService.getUserData(id, userData -> {
            this.template.convertAndSend("/topic/response", userData);
        });
    }
}
