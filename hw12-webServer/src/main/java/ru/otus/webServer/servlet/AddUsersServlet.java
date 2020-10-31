package ru.otus.webServer.servlet;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.core.model.User;
import ru.otus.core.service.DBServiceUser;
import ru.otus.webServer.services.TemplateProcessor;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class AddUsersServlet extends HttpServlet {
    private static Logger logger = LoggerFactory.getLogger(AddUsersServlet.class);

    private static final String ADD_USERS_PAGE_TEMPLATE = "add_user.html";
    private static final String TEMPLATE_ATTR_USER = "user";
    private static final int ID_PATH_PARAM_POSITION = 1;

    private final TemplateProcessor templateProcessor;
    private final DBServiceUser dbServiceUser;

    public AddUsersServlet(TemplateProcessor templateProcessor, DBServiceUser dbServiceUser) {
        this.templateProcessor = templateProcessor;
        this.dbServiceUser = dbServiceUser;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse response) throws IOException {
        User user = new User();
        Map<String, Object> paramsMap = new HashMap<>();
        long id = extractIdFromRequest(req, ID_PATH_PARAM_POSITION);
        dbServiceUser.getUser(id);
        paramsMap.put(TEMPLATE_ATTR_USER, user);

        response.setContentType("text/html");
        response.getWriter().println(templateProcessor.getPage(ADD_USERS_PAGE_TEMPLATE, paramsMap));
    }

    public static long extractIdFromRequest(HttpServletRequest request, int IdPathParamPosition) {
        String id = String.valueOf(-1);

        try {
            String[] path = request.getPathInfo().split("/");
            id = (path.length > 1) ? path[IdPathParamPosition] : String.valueOf(-1);
        } catch (Exception e) {
        }
        return Long.parseLong(id);
    }

}
