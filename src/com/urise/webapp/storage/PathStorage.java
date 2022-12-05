package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;
import com.urise.webapp.storage.serializer.SerializerStrategie;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PathStorage extends AbstractStorage<Path> {
    private final Path directory;
    private SerializerStrategie strategie;

    public void setStrategie(SerializerStrategie strategie) {
        this.strategie = strategie;
    }

    protected PathStorage(String dir, SerializerStrategie strategie) {
        directory = Paths.get(dir);
        Objects.requireNonNull(strategie, "strategy must not be null");
        Objects.requireNonNull(directory, "directory must not be null");
        if (!Files.isDirectory(directory) || !Files.isWritable(directory)) {
            throw new IllegalArgumentException(dir + " is not directory or is not writeble");
        }
        this.strategie = strategie;
    }

    @Override
    protected Resume doGet(Path path) {
        try {
            return strategie.doRead(new BufferedInputStream(Files.newInputStream(path)));
        } catch (IOException e) {
            throw new StorageException("Path read Error", path.toString());
        }
    }

    @Override
    protected void doUpdate(Resume resume, Path path) {
        try {
            strategie.doWrite(resume, new BufferedOutputStream(Files.newOutputStream(path)));
        } catch (IOException e) {
            throw new StorageException("File write Error ", resume.getUuid());
        }
    }

    @Override
    protected void doSave(Resume resume, Path path) {
        try {
            Files.createFile(path);
        } catch (IOException e) {
            throw new StorageException("Could`t create File ", resume.getUuid());
        }
        doUpdate(resume, path);
    }

    @Override
    protected void doDelete(Path path) {
        try {
            Files.delete(path);
        } catch (IOException e) {
            throw new StorageException("Path deleted error ", null, e);
        }
    }

    @Override
    protected Path getSearchKey(String uuid) {
        return directory.resolve(uuid);
    }

    @Override
    protected boolean isExist(Path path) {
        return Files.isRegularFile(path);
    }

    @Override
    protected List<Resume> doCopyAll() {
        return getPathList().map(this::doGet).collect(Collectors.toList());
    }

    @Override
    public void clear() {
        getPathList().forEach(this::doDelete);
    }

    @Override
    public int size() {
        return (int) getPathList().count();
    }

    private Stream<Path> getPathList() {
        try {
            return Files.list(directory);
        } catch (IOException e) {
            throw new StorageException("Directory read Error", null, e);
        }
    }
}
