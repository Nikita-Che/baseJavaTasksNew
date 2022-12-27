package com.urise.webapp.storage;

import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;
import com.urise.webapp.sql.SqlHelperOriginal;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class SqlStorageOriginal implements Storage {
    private static final Logger LOG = Logger.getLogger(SqlStorageOriginal.class.getName());
    public SqlHelperOriginal sqlHelperOriginal;

    public SqlStorageOriginal(String dbUrl, String dbUser, String dbPassword) {
        sqlHelperOriginal = new SqlHelperOriginal(() ->
                DriverManager.getConnection(dbUrl, dbUser, dbPassword));
    }

    @Override
    public void clear() {
        LOG.info("cleared");
        sqlHelperOriginal.executeOriginal("DELETE FROM resume");
    }

    @Override
    public Resume get(String uuid) {
        LOG.info("get " + uuid);
        return sqlHelperOriginal.executeOriginal("SELECT * FROM resume r WHERE r.uuid =?", ps -> {
            ps.setString(1, uuid);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
                throw new NotExistStorageException(uuid);
            }
            return new Resume(uuid, rs.getString("full_name"));
        });
    }

    @Override
    public void update(Resume r) {
        LOG.info("update " + r);
        sqlHelperOriginal.<Void>executeOriginal("UPDATE resume SET full_name=? WHERE uuid = ?", ps -> {
            ps.setString(1, r.getFullName());
            ps.setString(2, r.getUuid());
            if (ps.executeUpdate() == 0) {
                throw new NotExistStorageException(r.getUuid());
            }
            return null;
        });
    }

    @Override
    public void save(Resume r) {
        LOG.info("save " + r);
        sqlHelperOriginal.<Void>executeOriginal("INSERT INTO resume (uuid, full_name) VALUES (?,?)", ps -> {
            ps.setString(1, r.getUuid());
            ps.setString(2, r.getFullName());
            ps.execute();
            return null;
        });
    }

    @Override
    public void delete(String uuid) {
        LOG.info("delete " + uuid);
        sqlHelperOriginal.executeOriginal("DELETE FROM resume WHERE uuid =?", ps -> {
            ps.setString(1, uuid);
            if (ps.executeUpdate() == 0) {
                throw new NotExistStorageException(uuid);
            }
            return null;
        });
    }

    @Override
    public List<Resume> getAllSorted() {
        LOG.info("getAllSorted");
        List<Resume> resumes = new ArrayList<>();
        return sqlHelperOriginal.executeOriginal("SELECT * from resume ORDER BY full_name, uuid", ps -> {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                resumes.add(new Resume(rs.getString("uuid"), rs.getString("full_name")));
            }
            return resumes;
        });
    }

    @Override
    public int size() {
        return sqlHelperOriginal.executeOriginal("SELECT count(resume) from resume", ps -> {
            ResultSet rs = ps.executeQuery();
            return rs.next() ? rs.getInt(1) : 0;
        });
    }
}

