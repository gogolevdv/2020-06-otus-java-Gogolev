package ru.otus;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import ru.otus.core.dao.UserDao;
import ru.otus.core.model.AddressDataSet;
import ru.otus.core.model.PhoneDataSet;
import ru.otus.core.model.User;
import ru.otus.core.service.DBServiceUser;
import ru.otus.core.service.DbServiceUserImpl;
import ru.otus.core.sessionmanager.SessionManager;
import ru.otus.flyway.MigrationsExecutor;
import ru.otus.flyway.MigrationsExecutorFlyway;
import ru.otus.hibernate.HibernateUtils;
import ru.otus.hibernate.dao.UserDaoHibernate;
import ru.otus.hibernate.sessionmanager.SessionManagerHibernate;

@Configuration
public class HibernateConfig {
    public static final String HIBERNATE_CFG_FILE = "WEB-INF/hibernate.cfg.xml";

    @Autowired
    public MigrationsExecutor migrationsExecutorFlyway(){

        MigrationsExecutor migrationsExecutor = new MigrationsExecutorFlyway(HIBERNATE_CFG_FILE);
        migrationsExecutor.executeMigrations();

        return migrationsExecutor;
    }

    @Bean
    public SessionFactory sessionFactory() {
        return HibernateUtils.buildSessionFactory(HIBERNATE_CFG_FILE, User.class, AddressDataSet.class, PhoneDataSet.class);
    }

//    @Bean
//    public SessionManager sessionManagerHibernate() {
//        return new SessionManagerHibernate(sessionFactory());
//    }
//
//    @Bean
//    public UserDao userDao() {
//        return new UserDaoHibernate((SessionManagerHibernate) sessionManagerHibernate());
//    }
//
//    @Bean
//    public DBServiceUser dbServiceUser() {
//        return new DbServiceUserImpl(userDao());
//    }

}
