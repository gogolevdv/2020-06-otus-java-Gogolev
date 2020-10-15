package ru.otus;

import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.cachehw.HwCache;
import ru.otus.cachehw.MyCache;
import ru.otus.core.dao.UserDao;
import ru.otus.core.model.AddressDataSet;
import ru.otus.core.model.User;
import ru.otus.core.service.DBServiceUser;
import ru.otus.core.service.DbServiceUserImpl;
import ru.otus.core.service.DbServiceUserImplCache;
import ru.otus.flyway.MigrationsExecutor;
import ru.otus.flyway.MigrationsExecutorFlyway;
import ru.otus.hibernate.HibernateUtils;
import ru.otus.hibernate.dao.UserDaoHibernate;
import ru.otus.hibernate.sessionmanager.SessionManagerHibernate;

import java.util.*;

public class HomeWork {
    private static final Logger logger = LoggerFactory.getLogger(HomeWork.class);

    public static final String HIBERNATE_CFG_FILE = "hibernate.cfg.xml";

    public static void main(String[] args) {
        MigrationsExecutor migrationsExecutor = new MigrationsExecutorFlyway(HIBERNATE_CFG_FILE);
        migrationsExecutor.executeMigrations();

        List<Long> allId = new ArrayList<>();
        List<User> allUserList = new ArrayList<>();

        Long id;

        // Все главное см в тестах
        SessionFactory sessionFactory = HibernateUtils.buildSessionFactory(HIBERNATE_CFG_FILE, User.class, AddressDataSet.class);

        SessionManagerHibernate sessionManager = new SessionManagerHibernate(sessionFactory);
        UserDao userDao = new UserDaoHibernate(sessionManager);

        HwCache myCache = new MyCache();


        // with cache
//        DBServiceUser dbServiceUser = new DbServiceUserImplCache(userDao, myCache);

        // without cache
        DBServiceUser dbServiceUser = new DbServiceUserImpl(userDao);

        User user1 = new User();
        allUserList.add(user1);
        User user2 = new User();
        allUserList.add(user2);
        User user3 = new User();
        allUserList.add(user3);
        User user4 = new User();
        allUserList.add(user4);
        User user5 = new User();
        allUserList.add(user5);
        User user6 = new User();
        allUserList.add(user6);
        User user7 = new User();
        allUserList.add(user7);
        User user8 = new User();
        allUserList.add(user8);
        User user9 = new User();
        allUserList.add(user9);
        User user10 = new User();
        allUserList.add(user10);
        User user11 = new User();
        allUserList.add(user11);
        User user12 = new User();
        allUserList.add(user12);
        User user13 = new User();
        allUserList.add(user13);
        User user14 = new User();
        allUserList.add(user14);
        User user15 = new User();
        allUserList.add(user15);
        User user16 = new User();
        allUserList.add(user16);
        User user17 = new User();
        allUserList.add(user17);
        User user18 = new User();
        allUserList.add(user18);
        User user19 = new User();
        allUserList.add(user19);
        User user20 = new User();
        allUserList.add(user20);

        Random random = new Random();
        for (User user : allUserList) {
            user.setName(String.valueOf(user));
            user.setAge(random.nextInt(10));
            user.setAddress(new AddressDataSet(String.valueOf(random.nextInt(100)), user.getId()));
        }


        for (User user : allUserList) {
            id = dbServiceUser.saveUser(user);
            allId.add(id);
        }


        long begin = System.currentTimeMillis();

        for (long readId : allId) {
            outputUserOptional("User " + readId, dbServiceUser.getUser(readId));
        }


        logger.info("!!!!!!!!   ReadAllUser time:" + (System.currentTimeMillis() - begin) + "  !!!!!!!!!!!!!!!!!!!!");

// Работа со счетом

    }

    private static void outputUserOptional(String header, Optional<User> mayBeUser) {
        logger.info("-----------------------------------------------------------");
        System.out.println(header);
        mayBeUser.ifPresentOrElse(System.out::println, () -> logger.info("User not found"));
    }

}
