package com.urise.webapp.storage;

import com.urise.webapp.ConfigOriginal;

class SqlStorageOriginalTest extends AbstractStorageTest {
    protected SqlStorageOriginalTest() {
        super(ConfigOriginal.get().storage);
    }
}