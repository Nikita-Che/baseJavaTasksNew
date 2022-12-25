package com.urise.webapp.sql;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.StorageException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SqlHelper {

    public interface CustomRunnable  {
        void run(PreparedStatement preparedStatement) throws SQLException;
    }

    public static void execute(ConnectionFactory connectionFactory, String sql, CustomRunnable runnable) {
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            runnable.run(ps);
        } catch (SQLException e) {
            if (e.getSQLState().equals("23505")) {
                throw new ExistStorageException("");
            }
            throw new StorageException(e);
        }
    }
}




