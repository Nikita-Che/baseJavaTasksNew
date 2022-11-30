package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;
import com.urise.webapp.storage.serializer.SerializerStrategie;

import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileStorage extends AbstractStorage<File> {
    private final File directory;
    private SerializerStrategie strategie;

    public void setStrategie(SerializerStrategie strategie) {
        this.strategie = strategie;
    }

    protected FileStorage(File directory, SerializerStrategie strategie) {
        Objects.requireNonNull(directory, "directory must not be null");
        Objects.requireNonNull(strategie, "strategy must not be null");
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not directory");
        }
//         TODO: 25.11.2022 Как маркировать директорию чтобы можно было писать и читать? не работает в тестах
//        if (!directory.canRead() || directory.canWrite()) {
//            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not readable/writable");
//        }
        this.strategie = strategie;
        this.directory = directory;
    }

    @Override
    protected Resume doGet(File file) {
        try {
            return strategie.doRead(new BufferedInputStream(new FileInputStream(file)));
        } catch (IOException e) {
            throw new StorageException("File read Error", file.getName(), e);
        }
    }

    @Override
    protected void doUpdate(Resume resume, File file) {
        try {
            strategie.doWrite(resume, new BufferedOutputStream(new FileOutputStream(file)));
        } catch (IOException e) {
            throw new StorageException("File write Error", file.getName(), e);
        }
    }

    @Override
    protected void doSave(Resume resume, File file) {
        try {
            file.createNewFile();
        } catch (IOException e) {
            throw new StorageException("Could`t create file " + file.getAbsolutePath(), file.getName(), e);
        }
        doUpdate(resume, file);
    }

    @Override
    protected void doDelete(File file) {
        if (!file.delete()) {
            throw new StorageException("File deleted error ", file.getName());
        }
    }

    @Override
    protected File getSearchKey(String uuid) {
        return new File(directory, uuid);
    }

    @Override
    protected boolean isExist(File file) {
        return file.exists();
    }

    @Override
    protected List<Resume> doCopyAll() {
        return getFileList().map(this::doGet).collect(Collectors.toList());
    }

    @Override
    public void clear() {
        getFileList().forEach(this::doDelete);
    }

    @Override
    public int size() {
        return (int) getFileList().count();
    }

    private Stream<File> getFileList() {
        Stream<File> file = Arrays.stream(directory.listFiles());
        if (file == null) {
            throw new StorageException("Directory read error", null);
        }
        return file;
//        try {
//            return Files.list(directory);
//        } catch (IOException e) {
//            throw new StorageException("Directory read Error", null, e);
//        }
    }
}
