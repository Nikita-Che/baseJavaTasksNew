package com.urise.webapp.storage;

import com.urise.webapp.Config;

import java.io.File;

class SqlStorageTest extends AbstractStorageTest {

    File file = Config.get().getStorageDir();

    protected SqlStorageTest() {
        super(new SqlStorage("jdbc:postgresql://localhost:5432/resumes", "postgres", "qw"));
    }
}