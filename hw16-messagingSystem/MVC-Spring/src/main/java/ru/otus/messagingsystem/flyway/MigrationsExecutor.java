package ru.otus.messagingsystem.flyway;

public interface MigrationsExecutor {
    void cleanDb();
    void executeMigrations();
}
