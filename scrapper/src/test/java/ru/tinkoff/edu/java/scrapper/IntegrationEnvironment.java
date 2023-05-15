package ru.tinkoff.edu.java.scrapper;

import liquibase.Contexts;
import liquibase.LabelExpression;
import liquibase.Liquibase;
import liquibase.database.core.PostgresDatabase;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.DirectoryResourceAccessor;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.JdbcDatabaseContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;
import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Testcontainers
public abstract class IntegrationEnvironment {
    static final JdbcDatabaseContainer<?> POSTGRESQL_CONTAINER;

    static {
        POSTGRESQL_CONTAINER = new PostgreSQLContainer<>("postgres:15")
            .withDatabaseName("scrapper")
            .withUsername("user")
            .withPassword("1234");
        POSTGRESQL_CONTAINER.start();
        runMigrations();
    }

    private static void runMigrations() {
        Path changelogPath = new File(".").toPath().toAbsolutePath()
            .getParent()
            .getParent()
            .resolve("migrations");
        try {
            Connection connection = openConnection();
            DirectoryResourceAccessor changelogDir = new DirectoryResourceAccessor(changelogPath);
            PostgresDatabase db = new PostgresDatabase();
            db.setConnection(new JdbcConnection(connection));
            Liquibase liquibase = new Liquibase("master.xml", changelogDir, db);
            liquibase.update(new Contexts(), new LabelExpression());
        } catch (SQLException | FileNotFoundException | LiquibaseException e) {
            throw new RuntimeException(e);
        }
    }

    public static Connection openConnection() throws SQLException {
        return DriverManager.getConnection(
            POSTGRESQL_CONTAINER.getJdbcUrl(),
            POSTGRESQL_CONTAINER.getUsername(),
            POSTGRESQL_CONTAINER.getPassword()
        );
    }

    @DynamicPropertySource
    static void jdbcProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", POSTGRESQL_CONTAINER::getJdbcUrl);
        registry.add("spring.datasource.username", POSTGRESQL_CONTAINER::getUsername);
        registry.add("spring.datasource.password", POSTGRESQL_CONTAINER::getPassword);
    }
}
