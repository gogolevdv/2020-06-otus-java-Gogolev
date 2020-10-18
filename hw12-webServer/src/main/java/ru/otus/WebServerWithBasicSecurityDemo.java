package ru.otus;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.eclipse.jetty.security.HashLoginService;
import org.eclipse.jetty.security.LoginService;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.core.dao.UserDao;
import ru.otus.core.model.AddressDataSet;
import ru.otus.core.model.PhoneDataSet;
import ru.otus.core.model.User;
import ru.otus.core.service.DBServiceUser;
import ru.otus.core.service.DbServiceUserImpl;
import ru.otus.flyway.MigrationsExecutor;
import ru.otus.flyway.MigrationsExecutorFlyway;
import ru.otus.hibernate.HibernateUtils;
import ru.otus.hibernate.dao.UserDaoHibernate;
import ru.otus.hibernate.sessionmanager.SessionManagerHibernate;
import ru.otus.webServer.helpers.FileSystemHelper;
import ru.otus.webServer.server.UsersWebServer;
import ru.otus.webServer.server.UsersWebServerSimple;
//import ru.otus.webServer.server.UsersWebServerWithBasicSecurity;
import ru.otus.webServer.services.TemplateProcessor;
import ru.otus.webServer.services.TemplateProcessorImpl;

import java.util.Iterator;
import java.util.Optional;


/*
    Полезные для демо ссылки

    // Стартовая страница
    http://localhost:8080

    // Страница пользователей
    http://localhost:8080/users

    // REST сервис
    http://localhost:8080/api/user/3
*/
public class WebServerWithBasicSecurityDemo {
    private static final int WEB_SERVER_PORT = 8080;
    private static final String TEMPLATES_DIR = "/templates/";
    private static final String HASH_LOGIN_SERVICE_CONFIG_NAME = "realm.properties";
    private static final String REALM_NAME = "AnyRealm";

    private static final Logger logger = LoggerFactory.getLogger(WebServerWithBasicSecurityDemo.class);

    public static final String HIBERNATE_CFG_FILE = "hibernate.cfg.xml";


    public static void main(String[] args) throws Exception {
        MigrationsExecutor migrationsExecutor = new MigrationsExecutorFlyway(HIBERNATE_CFG_FILE);
        migrationsExecutor.executeMigrations();

        SessionFactory sessionFactory = HibernateUtils.buildSessionFactory(HIBERNATE_CFG_FILE, User.class, AddressDataSet.class, PhoneDataSet.class);

        SessionManagerHibernate sessionManager = new SessionManagerHibernate(sessionFactory);
        UserDao userDao = new UserDaoHibernate(sessionManager);
        DBServiceUser dbServiceUser = new DbServiceUserImpl(userDao);

        User user1 = new User(0, "Вася", 35,"user1","user1");
        User user2 = new User(0, "Вася_2", 36,"user2","user2");
        User user3 = new User(0, "Вася_3", 37,"user3","user3");
        User user4 = new User(0, "Вася_4", 38,"user4","user4");
        User user5 = new User(0, "Вася_5", 39,"user5","user5");



        long id = dbServiceUser.saveUser(user1);
        Optional<User> mayBeCreatedUser = dbServiceUser.getUser(id);
        outputUserOptional("Created user", mayBeCreatedUser);

        id = dbServiceUser.saveUser(user2);
        mayBeCreatedUser = dbServiceUser.getUser(id);
        outputUserOptional("Created user", mayBeCreatedUser);

        id = dbServiceUser.saveUser(user3);

        mayBeCreatedUser = dbServiceUser.getUser(id);
        outputUserOptional("Created user", mayBeCreatedUser);

        id = dbServiceUser.saveUser(user4);
        id = dbServiceUser.saveUser(user5);

        Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();
        TemplateProcessor templateProcessor = new TemplateProcessorImpl(TEMPLATES_DIR);

        String hashLoginServiceConfigPath = FileSystemHelper.localFileNameOrResourceNameToFullPath(HASH_LOGIN_SERVICE_CONFIG_NAME);
        LoginService loginService = new HashLoginService(REALM_NAME, hashLoginServiceConfigPath);
        //LoginService loginService = new InMemoryLoginServiceImpl(userDao);

//        UsersWebServer usersWebServer = new UsersWebServerWithBasicSecurity(WEB_SERVER_PORT,
//                loginService, userDao, gson, templateProcessor);

        mayBeCreatedUser = dbServiceUser.getUser(1);

        outputUserOptional("Created user: !!!!", mayBeCreatedUser);

        UsersWebServer usersWebServer = new UsersWebServerSimple(WEB_SERVER_PORT,
                 userDao, gson, templateProcessor,dbServiceUser);

        usersWebServer.start();
        usersWebServer.join();
    }

    private static void outputUserOptional(String header, Optional<User> mayBeUser) {
        System.out.println("-----------------------------------------------------------");
        System.out.println(header);
        mayBeUser.ifPresentOrElse(System.out::println, () -> logger.info("User not found"));
    }

}
