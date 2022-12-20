package com.urise.webapp.storage;

import com.urise.webapp.Config;

class SqlStorageTest extends AbstractStorageTest {

    protected SqlStorageTest() {
//        super(new SqlStorage("jdbc:postgresql://localhost:5432/resumes", "postgres", "qw"));
        super(Config.get().storage);
    }
}