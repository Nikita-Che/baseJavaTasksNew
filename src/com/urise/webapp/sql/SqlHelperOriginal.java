package com.urise.webapp.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SqlHelperOriginal {
    private final ConnectionFactory connectionFactory;

    public SqlHelperOriginal(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public void executeOriginal(String sql) {
        executeOriginal(sql, PreparedStatement::execute);
    }

    public <T> T executeOriginal(String sql, SqlExecutorOriginal<T> executorOriginal) {
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            return executorOriginal.execute(ps);
        } catch (SQLException e) {
            throw ExceptionUtil.convertException(e);
        }
    }

}
