package lab4.serializers;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import lab4.Faculty;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.List;

public class FacultyXMLSerizalizer implements Serializer<Faculty> {
    @Override
    public void serialize(Faculty object, File output) throws IOException {
        XmlMapper mapper = new XmlMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        mapper.writeValue(output, object);
    }

    @Override
    public void serializeCollection(Collection<Faculty> objects, File output) throws IOException {
        XmlMapper mapper = new XmlMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        mapper.writeValue(output, objects);
    }

    @Override
    public Faculty deserialize(File input) throws IOException {
        XmlMapper mapper = new XmlMapper();
        return mapper.readValue(input,Faculty.class);
    }

    @Override
    public Collection<Faculty> deserializeCollection(File input) throws IOException {
        XmlMapper mapper = new XmlMapper();
        return mapper.readValue(input,mapper.getTypeFactory().constructCollectionType(List.class, Faculty.class));    }
}
