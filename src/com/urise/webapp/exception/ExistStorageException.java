package com.urise.webapp.exception;

public class ExistStorageException extends StorageException {
    public ExistStorageException(String uuid) {
        super("Такое резюме " + uuid + " Уже существует", uuid);
    }
}
