package com.urise.webapp.storage;

import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;
import com.urise.webapp.sql.ConnectionFactory;
import com.urise.webapp.sql.SqlHelper;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
    public void clear() {
        LOG.info("cleared");
        String sqlRequest = "DELETE FROM resume";
        dataBaseRun(connectionFactory, sqlRequest, PreparedStatement::execute);

//        try (Connection conn = connectionFactory.getConnection();
//             PreparedStatement ps = conn.prepareStatement("DELETE FROM resume")) {
//            ps.execute();
//        } catch (SQLException e) {
//            throw new StorageException(e);
//        }
    }

    @Override
    public void save(Resume r) {
        LOG.info("save " + r);
        String sqlRequest = "INSERT INTO resume (uuid, full_name) VALUES (?,?)";
        dataBaseRun(connectionFactory, sqlRequest, preparedStatement -> {
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
    public void update(Resume r) {
        LOG.info("update " + r);
        String sqlRequest = "UPDATE resume SET uuid=? WHERE uuid = ?";
        dataBaseRun(connectionFactory, sqlRequest, preparedStatement -> {
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
    public void delete(String uuid) {
        LOG.info("delete " + uuid);
        String sqlRequest = "DELETE FROM resume r WHERE r.uuid =?";
        dataBaseRun(connectionFactory, sqlRequest, preparedStatement -> {
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
        String sqlRequest = "SELECT * FROM resume r WHERE r.uuid =?";
        dataBaseRun(connectionFactory, sqlRequest, preparedStatement -> {
            preparedStatement.setString(1, uuid);
            ResultSet rs = preparedStatement.executeQuery();
            if (!rs.next()) {
                throw new NotExistStorageException(uuid);
            }
        });
        return new Resume(uuid, "full_name"); // TODO: 24.12.2022 Проверить работу. просто строку full_name возвращать нельзя
    }
//        try (Connection conn = connectionFactory.getConnection();
//             PreparedStatement ps = conn.prepareStatement("SELECT * FROM resume r WHERE r.uuid =?")) {
//            ps.setString(1, uuid);
//            ResultSet rs = ps.executeQuery();
//            if (!rs.next()) {
//                throw new NotExistStorageException(uuid);
//            }
//            return new Resume(uuid, rs.getString("full_name"));
//        } catch (SQLException e) {
//            throw new StorageException(e);
//        }

    @Override
    public List<Resume> getAllSorted() {
        LOG.info("getAllSorted");
        String sqlRequest = "SELECT * from resume ORDER BY full_name";
        List<Resume> resumes = new ArrayList<>();
        dataBaseRun(connectionFactory, sqlRequest, preparedStatement -> {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                resumes.add(new Resume(resultSet.getString("uuid"), resultSet.getString("full_name")));
            }
        });
        return resumes;
    }

//        try (Connection conn = connectionFactory.getConnection();
//             PreparedStatement ps = conn.prepareStatement("SELECT * from resume ORDER BY full_name")) {
//            ResultSet resultSet = ps.executeQuery();
//            List<Resume> resumes = new ArrayList<>();
//            while (resultSet.next()) {
//                resumes.add(new Resume(resultSet.getString("uuid"), resultSet.getString("full_name")));
//            }
//            return resumes;
//        } catch (SQLException e) {
//            throw new StorageException(e);
//        }
//    }

    @Override
    public int size() {
        final int[] size = {0};
        String sqlRequest = "SELECT count(resume) from resume";
        dataBaseRun(connectionFactory, sqlRequest, new SqlHelper.CustomRunnable() {
            @Override
            public void run(PreparedStatement preparedStatement) throws SQLException {
                ResultSet resultSet = preparedStatement.executeQuery();
                resultSet.next();
                size[0] = resultSet.getInt(1);
            }
        });
        return size[0];

//        try (Connection conn = connectionFactory.getConnection();
//             PreparedStatement ps = conn.prepareStatement("SELECT count(resume) from resume")) {
//            ResultSet resultSet = ps.executeQuery();
//            resultSet.next();
//            return resultSet.getInt(1);
//        } catch (SQLException e) {
//            throw new StorageException(e);
//        }
    }
}

