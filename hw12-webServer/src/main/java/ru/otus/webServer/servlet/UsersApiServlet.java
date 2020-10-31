package ru.otus.webServer.servlet;

import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.core.model.User;
import ru.otus.core.service.DBServiceUser;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;

import static ru.otus.webServer.servlet.AddUsersServlet.extractIdFromRequest;

public class UsersApiServlet extends HttpServlet {
    private static Logger logger = LoggerFactory.getLogger(UsersApiServlet.class);

    private static final String CONTENT_TYPE_JSON = "application/json;charset=UTF-8";
    private static final int ID_PATH_PARAM_POSITION = 1;

    private final DBServiceUser dbServiceUser;
    private final Gson gson;

    public UsersApiServlet(DBServiceUser dbServiceUser, Gson gson) {
        this.dbServiceUser = dbServiceUser;
        this.gson = gson;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        User user = dbServiceUser.getUser(extractIdFromRequest(request, ID_PATH_PARAM_POSITION)).orElse(null);

        response.setContentType(CONTENT_TYPE_JSON);
        ServletOutputStream out = response.getOutputStream();
        out.print(gson.toJson(user));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        StringBuffer jb = new StringBuffer();
        String line = null;

            BufferedReader reader = req.getReader();
            while ((line = reader.readLine()) != null)
                jb.append(line);

        User user = gson.fromJson(jb.toString(), User.class);
        dbServiceUser.saveUser(user);

        resp.getOutputStream().print(gson.toJson(user));
        resp.setStatus(HttpServletResponse.SC_OK);
    }

}
