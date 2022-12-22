package com.urise.webapp.storage;

import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;
import com.urise.webapp.sql.ConnectionFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import static com.urise.webapp.sql.SqlHelper.dataBaseRun;

public class SqlStorage implements Storage {
    public final ConnectionFactory connectionFactory;
    private static final Logger LOG = Logger.getLogger(SqlStorage.class.getName());

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        connectionFactory = () -> DriverManager.getConnection(dbUrl, dbUser, dbPassword);
    }

// with class
//    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
//        connectionFactory = new ConnectionFactory() {
//            @Override
//            public Connection getConnection() throws SQLException {
//                return DriverManager.getConnection(dbUrl, dbUser, dbPassword);
//            }
//        };
//    }

    @Override
    public void clear() throws SQLException {
        LOG.info("cleared");
        String sql = "DELETE FROM resume";
        dataBaseRun(connectionFactory, sql, PreparedStatement::execute);

//        try (Connection conn = connectionFactory.getConnection();
//             PreparedStatement ps = conn.prepareStatement("DELETE FROM resume")) {
//            ps.execute();
//        } catch (SQLException e) {
//            throw new StorageException(e);
//        }
    }

    @Override
    public void save(Resume r) throws SQLException {
        LOG.info("save " + r);
        String sql = "INSERT INTO resume (uuid, full_name) VALUES (?,?)";
        dataBaseRun(connectionFactory, sql, preparedStatement -> {
            preparedStatement.setString(1, r.getUuid());
            preparedStatement.setString(2, r.getFullName());
            preparedStatement.execute();
        });

//        try (Connection conn = connectionFactory.getConnection();
//             PreparedStatement ps = conn.prepareStatement("INSERT INTO resume (uuid, full_name) VALUES (?,?)")) {
//            ps.setString(1, r.getUuid());
//            ps.setString(2, r.getFullName());
//            ps.execute();
//        } catch (SQLException e) {
//            if (e.getSQLState().equals("23505")) {
//                throw new ExistStorageException(r.getUuid());
//            }
//            throw new StorageException(e);
//        }
    }

    @Override
    public void update(Resume r) throws SQLException {
        LOG.info("update " + r);
        String sql = "UPDATE resume SET uuid=? WHERE uuid = ?";
        dataBaseRun(connectionFactory, sql, preparedStatement -> {
            preparedStatement.setString(1, r.getUuid());
            preparedStatement.setString(2, r.getUuid());
            if (preparedStatement.executeUpdate() == 0) {
                throw new NotExistStorageException(r.getUuid());
            }
        });

//        try (Connection conn = connectionFactory.getConnection();
//             PreparedStatement ps = conn.prepareStatement("UPDATE resume SET uuid=? WHERE uuid = ?")) {
//            ps.setString(1, r.getUuid());
//            ps.setString(2, r.getUuid());
//            if (ps.executeUpdate() == 0) {
//                throw new NotExistStorageException(r.getUuid());
//            }
//        } catch (SQLException e) {
//            throw new StorageException(e);
//        }
    }

    @Override
    public void delete(String uuid) throws SQLException {
        LOG.info("delete " + uuid);
        String sql = "DELETE FROM resume r WHERE r.uuid =?";
        dataBaseRun(connectionFactory, sql, preparedStatement -> {
            preparedStatement.setString(1, uuid);
            if (preparedStatement.executeUpdate() == 0) {
                throw new NotExistStorageException(uuid);
            }
        });

//        try (Connection conn = connectionFactory.getConnection();
//             PreparedStatement ps = conn.prepareStatement("DELETE FROM resume r WHERE r.uuid =?")) {
//            ps.setString(1, uuid);
//            if (ps.executeUpdate() == 0) {
//                throw new NotExistStorageException(uuid);
//            }
//        } catch (SQLException e) {
//            throw new StorageException(e);
//        }
    }

    @Override
    public Resume get(String uuid) {
        LOG.info("get " + uuid);
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT * FROM resume r WHERE r.uuid =?")) {
            ps.setString(1, uuid);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                throw new NotExistStorageException(uuid);
            }
            return new Resume(uuid, rs.getString("full_name"));
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }

    @Override
    public List<Resume> getAllSorted() {
        LOG.info("getAllSorted");
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT * from resume ORDER BY full_name")) {
            ResultSet resultSet = ps.executeQuery();
            List<Resume> resumes = new ArrayList<>();
            while (resultSet.next()) {
                resumes.add(new Resume(resultSet.getString("uuid"), resultSet.getString("full_name")));
            }
            return resumes;
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }

    @Override
    public int size() {
        try (Connection conn = connectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT count(resume) from resume")) {
            ResultSet resultSet = ps.executeQuery();
            resultSet.next();
            return resultSet.getInt(1);
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }
}
