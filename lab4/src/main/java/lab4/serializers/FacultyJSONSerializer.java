package lab4.serializers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lab4.Faculty;

import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.util.Collection;
import java.util.List;

public class FacultyJSONSerializer implements Serializer<Faculty> {
    @Override
    public void serialize(Faculty object, Writer output) throws IOException {
        ObjectMapper mapper = new ObjectMapper()
                .registerModule(new JavaTimeModule());
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        mapper.writeValue(output, object);
    }

    @Override
    public void serializeCollection(Collection<Faculty> objects, Writer output) throws IOException {
        ObjectMapper mapper = new ObjectMapper()
                .registerModule(new JavaTimeModule());
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        mapper.writeValue(output, objects);
    }

    @Override
    public Faculty deserialize(InputStream input) throws IOException {
        ObjectMapper mapper = new ObjectMapper()
                .registerModule(new JavaTimeModule());
        return mapper.readValue(input, Faculty.class);
    }

    @Override
    public Collection<Faculty> deserializeCollection(InputStream input) throws IOException {
        ObjectMapper mapper = new ObjectMapper()
                .registerModule(new JavaTimeModule());
        return mapper.readValue(input,mapper.getTypeFactory().constructCollectionType(List.class, Faculty.class));
    }
}
