package lab4.serializers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lab4.Department;

import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.util.Collection;
import java.util.List;


public class DepartmentJSONSerializer implements Serializer<Department> {
    @Override
    public void serialize(Department object, Writer output) throws IOException {
        ObjectMapper mapper = new ObjectMapper()
                .registerModule(new JavaTimeModule());
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        mapper.writeValue(output, object);
    }

    @Override
    public void serializeCollection(Collection<Department> objects, Writer output) throws IOException {
        ObjectMapper mapper = new ObjectMapper()
                .registerModule(new JavaTimeModule());
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        mapper.writeValue(output, objects);
    }

    @Override
    public Department deserialize(InputStream input) throws IOException {
        ObjectMapper mapper = new ObjectMapper()
                .registerModule(new JavaTimeModule());
        return mapper.readValue(input,Department.class);
    }

    @Override
    public Collection<Department> deserializeCollection(InputStream input) throws IOException {
        ObjectMapper mapper = new ObjectMapper()
                .registerModule(new JavaTimeModule());
        return mapper.readValue(input,mapper.getTypeFactory().constructCollectionType(List.class, Department.class));
    }
}
