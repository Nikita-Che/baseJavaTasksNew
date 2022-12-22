package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.sql.SQLException;
import java.util.List;

/**
 * Array based storage for Resumes
 */
public interface Storage {

    void clear() throws SQLException;

    void save(Resume r) throws SQLException;

    void update(Resume r) throws SQLException;

    Resume get(String uuid);

    void delete(String uuid) throws SQLException;

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    List<Resume> getAllSorted();

    int size();
}