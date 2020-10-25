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
import ru.otus.webServer.server.UsersWebServerWithBasicSecurity;
import ru.otus.webServer.services.TemplateProcessor;
import ru.otus.webServer.services.TemplateProcessorImpl;

import java.util.*;


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


        User user1 = new User(0, "Вася", 35, "user1", "user1");
        User user2 = new User(0, "Вася_2", 36, "user2", "user2");
        User user3 = new User(0, "Вася_3", 37, "user3", "user3");
        User user4 = new User(0, "Вася_4", 38, "user4", "user4");
        User user5 = new User(0, "Вася_5", 39, "user5", "user5");
        User user6 = new User(0, "Вася_6", 44, "user5", "user5");
        User user7 = new User(0, "Вася_7", 55, "user5", "user5");

        AddressDataSet addressDataSet1 = new AddressDataSet();
        PhoneDataSet phoneDataSet1 = new PhoneDataSet();
        addressDataSet1.setStreet("123");
        Set<PhoneDataSet> phones1 = new HashSet<>();
        phoneDataSet1.setNumber("1234567");
        phoneDataSet1.setNumber("qwqrwer");
        phoneDataSet1.setUser(user1);
        phones1.add(phoneDataSet1);

        user1.setAddress(addressDataSet1);
        user1.setPhone(phones1);

        AddressDataSet addressDataSet2 = new AddressDataSet();
        addressDataSet2.setStreet("234");
        user2.setAddress(addressDataSet2);

        AddressDataSet addressDataSet3 = new AddressDataSet();
        addressDataSet3.setStreet("123");
        user3.setAddress(addressDataSet3);

        AddressDataSet addressDataSet4 = new AddressDataSet();
        addressDataSet4.setStreet("123");
        user4.setAddress(addressDataSet4);

        AddressDataSet addressDataSet5 = new AddressDataSet();
        addressDataSet5.setStreet("123");
        user5.setAddress(addressDataSet5);

        AddressDataSet addressDataSet6 = new AddressDataSet();
        addressDataSet6.setStreet("123");
        user6.setAddress(addressDataSet6);

        AddressDataSet addressDataSet7 = new AddressDataSet();
        addressDataSet7.setStreet("123");
        user7.setAddress(addressDataSet7);


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
        id = dbServiceUser.saveUser(user6);
        id = dbServiceUser.saveUser(user7);

        Gson gson = new GsonBuilder().serializeNulls().setPrettyPrinting().create();
        TemplateProcessor templateProcessor = new TemplateProcessorImpl(TEMPLATES_DIR);

        String hashLoginServiceConfigPath = FileSystemHelper.localFileNameOrResourceNameToFullPath(HASH_LOGIN_SERVICE_CONFIG_NAME);
        LoginService loginService = new HashLoginService(REALM_NAME, hashLoginServiceConfigPath);

        UsersWebServer usersWebServer = new UsersWebServerWithBasicSecurity(WEB_SERVER_PORT,
                loginService, gson, templateProcessor,dbServiceUser);


        usersWebServer.start();
        usersWebServer.join();
    }

    private static void outputUserOptional(String header, Optional<User> mayBeUser) {
        System.out.println("-----------------------------------------------------------");
        System.out.println(header);
        mayBeUser.ifPresentOrElse(System.out::println, () -> logger.info("User not found"));
    }

}
