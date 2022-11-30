package com.urise.webapp.exception;

public class NotExistStorageException extends StorageException {
    public NotExistStorageException(String uuid) {
        super("Такое резюме " + uuid + " НЕ существует", uuid);
    }
}
