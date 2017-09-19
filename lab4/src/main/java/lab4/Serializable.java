package lab4;

import java.io.File;
import java.util.Collection;

public interface Serializable<T> {
    public void serialize(T object, File output);

    public void serializeCollection(Collection<T> objects, File output);

    public T deserialize(File input);

    public Collection<T> deserializeCollection(File input);

}
