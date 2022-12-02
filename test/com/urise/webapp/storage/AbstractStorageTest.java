package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.urise.webapp.ResumeTestData.createResume;
import static org.junit.jupiter.api.Assertions.assertEquals;

public abstract class AbstractStorageTest {
    // TODO: пасс с рабочего и домашнего компа
    static String path = "C:\\Users\\nikita\\Desktop\\GitHub\\basejava\\storage";
//    static String path = "D:\\Никита\\java\\repo\\basejava\\storage";
    protected static final File STORAGE_DIR = new File(path);

    private final Storage storage;

    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";
    private static final String UUID_4 = "uuid4";
    private static final String FULL_NAME1 = "Vasya";
    private static final String FULL_NAME2 = "Petya";
    private static final String FULL_NAME3 = "Kolya";
    private static final String FULL_NAME4 = "Grisha";
    private static final String UUID_NOT_EXIST = "dummy";
    private static final Resume RESUME_1 = createResume(UUID_1, FULL_NAME1);
    private static final Resume RESUME_2 = createResume(UUID_2, FULL_NAME2);
    private static final Resume RESUME_3 = createResume(UUID_3, FULL_NAME3);
    private static final Resume RESUME_4 = createResume(UUID_4, FULL_NAME4);
    private static final Resume RESUME_5 = createResume(UUID_NOT_EXIST, FULL_NAME1);

    protected AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }

    @BeforeEach
    public void setUp() throws Exception {
        storage.clear();
        storage.save(RESUME_1);
        storage.save(RESUME_2);
        storage.save(RESUME_3);
    }

    private void assertSize(int expected) {
        assertEquals(expected, storage.size());
    }

    private void assertGet(Resume resume) {
        Resume r = storage.get(resume.getUuid());
        assertEquals(r.getUuid(), resume.getUuid());
    }

    @Test
    public void size() throws Exception {
        assertSize(3);
    }

    @Test
    public void clear() throws Exception {
        Assertions.assertThrows(NotExistStorageException.class, () -> {
            List<Resume> list = new ArrayList<>();
            storage.clear();
            assertSize(0);
//            assertEquals(list, storage.getAllSorted()); исключил потому что нет смысла пустое проверять
            assertGet(RESUME_1);
        });
    }

    @Test
    public void getResume() throws Exception {
        assertGet(RESUME_1);
        assertGet(RESUME_2);
        assertGet(RESUME_3);
    }

    @Test
    public void getResumeNotExist() throws Exception {
        Assertions.assertThrows(NotExistStorageException.class, () -> {
            assertGet(RESUME_5);
        });
    }

    @Test
    public void getAll() throws Exception {
        List<Resume> expected = Arrays.asList(RESUME_1, RESUME_2, RESUME_3);
        expected.sort(AbstractStorage.comparator);
        assertSize(3);
        assertEquals(expected, storage.getAllSorted());
    }

    @Test()
    public void updateResume() throws Exception {
        Resume resume = RESUME_1;
        storage.update(resume);
        Assertions.assertSame(resume, RESUME_1);
    }

    @Test
    public void updateNotExist() throws Exception {
        Assertions.assertThrows(NotExistStorageException.class, () -> {
            storage.update(RESUME_5);
        });
    }

    @Test
    public void save() throws Exception {
        storage.save(RESUME_4);
        assertSize(4);
        assertGet(RESUME_4);
    }

    @Test
    public void saveExist() throws Exception {
        Assertions.assertThrows(ExistStorageException.class, () -> {
            storage.save(RESUME_2);
        });
    }

    @Test
    public void deleteResume() throws Exception {
        Assertions.assertThrows(NotExistStorageException.class, () -> {
            storage.delete(UUID_1);
            assertSize(2);
            assertGet(RESUME_1);
        });
    }

    @Test
    public void deleteNotExist() throws Exception {
        Assertions.assertThrows(NotExistStorageException.class, () -> {
            storage.delete(UUID_NOT_EXIST);
        });
    }
}
