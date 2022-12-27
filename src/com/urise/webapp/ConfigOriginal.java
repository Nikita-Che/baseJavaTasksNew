package com.urise.webapp;

import com.urise.webapp.storage.SqlStorageOriginal;
import com.urise.webapp.storage.Storage;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigOriginal {
    private static final ConfigOriginal INSTANCE = new ConfigOriginal();
    protected static final File PROPS = new File("./config/resumes.properties");
    private final File storageDir;
    public Properties props = new Properties();
    public final Storage storage;

    public static ConfigOriginal get() {
        return INSTANCE;
    }

    private ConfigOriginal() {
        try (InputStream is = new FileInputStream("./config/resumes.properties")) {
            props.load(is);
            storageDir = new File(props.getProperty("storage.dir"));
            storage = new SqlStorageOriginal(props.getProperty("db.url"), props.getProperty("db.user"), props.getProperty("db.password"));
        } catch (IOException e) {
            throw new IllegalStateException("Invalid config file! " + PROPS.getAbsolutePath());
        }
    }

    public File getStorageDir() {
        return storageDir;
    }
}
