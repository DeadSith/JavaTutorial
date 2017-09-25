package lab4.serializers;

import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.util.Collection;


public interface Serializer<T> {
    void serialize(T object, Writer output) throws IOException;

    void serializeCollection(Collection<T> objects, Writer output) throws IOException;

    T deserialize(InputStream input) throws IOException;

    Collection<T> deserializeCollection(InputStream input) throws IOException;

}
