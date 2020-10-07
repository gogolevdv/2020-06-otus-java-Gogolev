package ru.otus;

import org.flywaydb.core.Flyway;
import org.hibernate.SessionFactory;
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

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class HomeWork {
    private static final Logger logger = LoggerFactory.getLogger(HomeWork.class);

    public static final String HIBERNATE_CFG_FILE = "hibernate.cfg.xml";

    public static void main(String[] args) {
        MigrationsExecutor migrationsExecutor = new MigrationsExecutorFlyway(HIBERNATE_CFG_FILE);
        migrationsExecutor.executeMigrations();

        // Все главное см в тестах
        SessionFactory sessionFactory = HibernateUtils.buildSessionFactory(HIBERNATE_CFG_FILE, User.class, AddressDataSet.class, PhoneDataSet.class);

        SessionManagerHibernate sessionManager = new SessionManagerHibernate(sessionFactory);
        UserDao userDao = new UserDaoHibernate(sessionManager);
        DBServiceUser dbServiceUser = new DbServiceUserImpl(userDao);

        User user1 = new User(0, "Вася", 35);
        User user2 = new User(1L, "А! Нет. Это же совсем не Вася", 36);

        user1.setAddress(new AddressDataSet("123",user1.getId()));
        user2.setAddress(new AddressDataSet("567",user2.getId()));

        AddressDataSet addr2 = new AddressDataSet();
        addr2.setStreet("567");
        addr2.setUser(user2);
        user2.setAddress(addr2);

        List<PhoneDataSet> phList_1 = new ArrayList<>();
        List<PhoneDataSet> phList_2 = new ArrayList<>();

        PhoneDataSet ph1 = new PhoneDataSet();
        ph1.setNumber("765");
        ph1.setUser(user1);
        phList_1.add(ph1);
        user1.setPhone(phList_1);

         PhoneDataSet ph2 = new PhoneDataSet();
        ph2.setNumber("842");
        ph2.setUser(user2);
        phList_2.add(ph2);
        user2.setPhone(phList_2);

        long id = dbServiceUser.saveUser(user1);

        Optional<User> mayBeCreatedUser = dbServiceUser.getUser(id);

        id = dbServiceUser.saveUser(user2);

        Optional<User> mayBeUpdatedUser = dbServiceUser.getUser(id);

        outputUserOptional("Created user", mayBeCreatedUser);
        outputUserOptional("Updated user", mayBeUpdatedUser);

// Работа со счетом

    }

    private static void outputUserOptional(String header, Optional<User> mayBeUser) {
        System.out.println("-----------------------------------------------------------");
        System.out.println(header);
        mayBeUser.ifPresentOrElse(System.out::println, () -> logger.info("User not found"));
    }

}
