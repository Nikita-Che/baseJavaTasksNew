import com.urise.webapp.storage.PathStorage;
import com.urise.webapp.storage.serializer.XmlStreamSerializer;

public class XmlPathStorageTest extends AbstractStorageTest {
    public XmlPathStorageTest() {
        super(new PathStorage(STORAGE_DIR.getPath(),new XmlStreamSerializer()));
    }
}