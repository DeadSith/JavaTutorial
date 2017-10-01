package other_labs.serializers;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import other_labs.Department;

import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.util.Collection;
import java.util.List;

public class DepartmentXMLSerializer implements Serializer<Department> {
    @Override
    public void serialize(Department object, Writer output) throws IOException {
        XmlMapper mapper = new XmlMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        mapper.writeValue(output, object);
    }

    @Override
    public void serializeCollection(Collection<Department> objects, Writer output) throws IOException {
        XmlMapper mapper = new XmlMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        mapper.writeValue(output, objects);
    }

    @Override
    public Department deserialize(InputStream input) throws IOException {
        XmlMapper mapper = new XmlMapper();
        return mapper.readValue(input,Department.class);
    }

    @Override
    public Collection<Department> deserializeCollection(InputStream input) throws IOException {
        XmlMapper mapper = new XmlMapper();
        return mapper.readValue(input,mapper.getTypeFactory().constructCollectionType(List.class, Department.class));
    }
}
