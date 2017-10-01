package other_labs.serializers;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import other_labs.Faculty;

import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.util.Collection;
import java.util.List;

public class FacultyXMLSerizalizer implements Serializer<Faculty> {
    @Override
    public void serialize(Faculty object, Writer output) throws IOException {
        XmlMapper mapper = new XmlMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        mapper.writeValue(output, object);
    }

    @Override
    public void serializeCollection(Collection<Faculty> objects, Writer output) throws IOException {
        XmlMapper mapper = new XmlMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        mapper.writeValue(output, objects);
    }

    @Override
    public Faculty deserialize(InputStream input) throws IOException {
        XmlMapper mapper = new XmlMapper();
        return mapper.readValue(input,Faculty.class);
    }

    @Override
    public Collection<Faculty> deserializeCollection(InputStream input) throws IOException {
        XmlMapper mapper = new XmlMapper();
        return mapper.readValue(input,mapper.getTypeFactory().constructCollectionType(List.class, Faculty.class));    }
}
