package lab4.serializers;

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lab4.Department;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.List;

import com.fasterxml.jackson.databind.*;


public class DepartmentJSONSerializer implements Serializer<Department> {
    @Override
    public void serialize(Department object, File output) throws IOException {
        ObjectMapper mapper = new ObjectMapper()
                .registerModule(new JavaTimeModule());
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        mapper.writeValue(output, object);
    }

    @Override
    public void serializeCollection(Collection<Department> objects, File output) throws IOException {
        ObjectMapper mapper = new ObjectMapper()
                .registerModule(new JavaTimeModule());
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        mapper.writeValue(output, objects);
    }

    @Override
    public Department deserialize(File input) throws IOException {
        ObjectMapper mapper = new ObjectMapper()
                .registerModule(new JavaTimeModule());
        return mapper.readValue(input,Department.class);
    }

    @Override
    public Collection<Department> deserializeCollection(File input) throws IOException {
        ObjectMapper mapper = new ObjectMapper()
                .registerModule(new JavaTimeModule());
        return mapper.readValue(input,mapper.getTypeFactory().constructCollectionType(List.class, Department.class));
    }
}
