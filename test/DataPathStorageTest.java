import com.urise.webapp.storage.PathStorage;
import com.urise.webapp.storage.serializer.DataStreamSerializer;

public class DataPathStorageTest extends AbstractStorageTest {
    public DataPathStorageTest() {
        super(new PathStorage(STORAGE_DIR.getPath(), new DataStreamSerializer()));
    }
}