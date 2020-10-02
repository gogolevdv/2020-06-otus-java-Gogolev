package ru.otus;

import org.flywaydb.core.Flyway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.core.dao.AccountDao;
import ru.otus.core.model.Account;
import ru.otus.core.service.DbServiceAccountImpl;
import ru.otus.h2.DataSourceH2;
import ru.otus.jdbc.DbExecutorImpl;
import ru.otus.jdbc.dao.AccountDaoJdbcMapper;
import ru.otus.jdbc.mapper.EntityClassMetaDataImpl;
import ru.otus.jdbc.mapper.EntitySQLMetaData;
import ru.otus.jdbc.mapper.EntitySQLMetaDataImpl;
import ru.otus.jdbc.mapper.JdbcMapperImpl;
import ru.otus.jdbc.sessionmanager.SessionManagerJdbc;

import javax.sql.DataSource;
import java.util.Optional;

public class HomeWork {
    private static final Logger logger = LoggerFactory.getLogger(HomeWork.class);

    public static void main(String[] args) {
        var dataSource = new DataSourceH2();
        flywayMigrations(dataSource);
        var sessionManager = new SessionManagerJdbc(dataSource);

        // Работа с пользователем
//        DbExecutorImpl<User> dbExecutor = new DbExecutorImpl<>();
//        var entityClassMetaData = new EntityClassMetaDataImpl<>(User.class);
//        EntitySQLMetaData sqlMetaDat = new EntitySQLMetaDataImpl<>(entityClassMetaData);
//        JdbcMapperImpl<User> jdbcMapperUser = new JdbcMapperImpl<>(sessionManager, dbExecutor,sqlMetaDat,entityClassMetaData);
//
//        UserDao userDao = new UserDaoJdbcMapper( jdbcMapperUser );

        DbExecutorImpl<Account> dbExecutor = new DbExecutorImpl<>();
        var entityClassMetaData = new EntityClassMetaDataImpl<>(Account.class);
        var sqlMetaDat = new EntitySQLMetaDataImpl<>(entityClassMetaData);
        JdbcMapperImpl<Account> jdbcMapperAccount = new JdbcMapperImpl<>(sessionManager, dbExecutor, sqlMetaDat, entityClassMetaData);

        AccountDao accountDao = new AccountDaoJdbcMapper(jdbcMapperAccount);

// Код дальше должен остаться, т.е. userDao должен использоваться
//        var dbServiceUser = new DbServiceUserImpl(userDao);
//        var id = dbServiceUser.saveUser(new User(0, "dbServiceUser", 35));
//        Optional<User> user = dbServiceUser.getUser(id);
//
//        user.ifPresentOrElse(
//                crUser -> logger.info("created user, name:{}", crUser),
//                () -> logger.info("user was not created")
//        );

        var dbServiceAccount = new DbServiceAccountImpl(accountDao);
        var no = dbServiceAccount.saveAccount(new Account(0, "dbCreditAccount", 123.345));
        Optional<Account> account = dbServiceAccount.getAccount(no);

        account.ifPresentOrElse(
                crAccount -> logger.info("created account, no:{}", crAccount),
                () -> logger.info("account was not created")
        );


// Работа со счетом

    }

    private static void flywayMigrations(DataSource dataSource) {
        logger.info("db migration started...");
        var flyway = Flyway.configure()
                .dataSource(dataSource)
                .locations("classpath:/db/schemas")
                .load();
        flyway.migrate();
        logger.info("db migration finished.");
        logger.info("***");
    }

}
