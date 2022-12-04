import com.urise.webapp.storage.PathStorage;
import com.urise.webapp.storage.serializer.JsonStreamSerializer;

public class JsonPathStorageTest extends AbstractStorageTest {
    public JsonPathStorageTest() {
        super(new PathStorage(STORAGE_DIR.getPath(),new JsonStreamSerializer()));
    }
}