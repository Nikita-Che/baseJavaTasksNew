package com.urise.webapp.sql;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface SqlExecutorOriginal <T>{
    T execute(PreparedStatement st) throws SQLException;
}
