package lab4.serializers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lab4.Department;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.List;

public class DepartmentXMLSerializer implements Serializer<Department> {
    @Override
    public void serialize(Department object, File output) throws IOException {
        XmlMapper mapper = new XmlMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        mapper.writeValue(output, object);
    }

    @Override
    public void serializeCollection(Collection<Department> objects, File output) throws IOException {
        XmlMapper mapper = new XmlMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        mapper.writeValue(output, objects);
    }

    @Override
    public Department deserialize(File input) throws IOException {
        XmlMapper mapper = new XmlMapper();
        return mapper.readValue(input,Department.class);
    }

    @Override
    public Collection<Department> deserializeCollection(File input) throws IOException {
        XmlMapper mapper = new XmlMapper();
        return mapper.readValue(input,mapper.getTypeFactory().constructCollectionType(List.class, Department.class));
    }
}
