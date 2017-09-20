package lab4.serializers;

import java.io.File;
import java.io.IOException;
import java.util.Collection;


public interface Serializer<T> {
    public void serialize(T object, File output) throws IOException;

    public void serializeCollection(Collection<T> objects, File output) throws IOException;

    public T deserialize(File input) throws IOException;

    public Collection<T> deserializeCollection(File input) throws IOException;

}
