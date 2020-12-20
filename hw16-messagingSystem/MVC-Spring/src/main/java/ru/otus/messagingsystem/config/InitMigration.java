package ru.otus.messagingsystem.config;

import ru.otus.messagingsystem.flyway.MigrationsExecutor;
import ru.otus.messagingsystem.flyway.MigrationsExecutorFlyway;

public class InitMigration {
    public static final String HIBERNATE_CFG_FILE = "hibernate.cfg.xml";


    public void init(){
        MigrationsExecutor migrationsExecutor = new MigrationsExecutorFlyway(HIBERNATE_CFG_FILE);
        migrationsExecutor.executeMigrations();

    }
}
