package ru.otus.webServer.servlet;


import ru.otus.core.dao.UserDao;
import ru.otus.core.service.DBServiceUser;
import ru.otus.webServer.services.TemplateProcessor;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class UsersServlet extends HttpServlet {

    private static final String USERS_PAGE_TEMPLATE = "users.html";
    private static final String TEMPLATE_ATTR_RANDOM_USER = "randomUser";

    private final UserDao userDao;
    private final TemplateProcessor templateProcessor;
    private final DBServiceUser dbServiceUser;

    public UsersServlet(TemplateProcessor templateProcessor, UserDao userDao,DBServiceUser dbServiceUser) {
        this.templateProcessor = templateProcessor;
        this.userDao = userDao;
        this.dbServiceUser=dbServiceUser;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse response) throws IOException {
        Map<String, Object> paramsMap = new HashMap<>();
        var randomUser = dbServiceUser.getRandomUser().get();
//        var randomUser = dbServiceUser.getUser(2).get();
        paramsMap.put(TEMPLATE_ATTR_RANDOM_USER,randomUser);
//        userDao.findRandomUser().ifPresent(randomUser -> paramsMap.put(TEMPLATE_ATTR_RANDOM_USER, randomUser));

        response.setContentType("text/html");
        response.getWriter().println(templateProcessor.getPage(USERS_PAGE_TEMPLATE, paramsMap));
    }

}
