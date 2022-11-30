package com.urise.webapp.storage.serializer;

import com.urise.webapp.model.Resume;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface SerializerStrategie {
    //Тут интерфейс который описывает поведение стратегии. От него должны быть наследники, а интерфейс надо в конструктор передать к файл и пасстроаджу.

    void doWrite(Resume resume, OutputStream os) throws IOException;

    Resume doRead(InputStream is) throws IOException ;
}
