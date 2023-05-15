package ru.tinkoff.edu.java.scrapper;

import org.junit.Test;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import static org.junit.Assert.assertEquals;

public class MigrationsTest extends IntegrationEnvironment {
    private static final String insert = "INSERT INTO chats(id) VALUES (?)";
    private static final String select = "SELECT * FROM chats";

    @Test
    public void databaseSuccessfullyCreated() {
        try {
            Connection connection = openConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(insert);
            preparedStatement.setInt(1, 1111);
            preparedStatement.executeUpdate();

            preparedStatement = connection.prepareStatement(select);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            assertEquals(resultSet.getString("id"), "1111");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
