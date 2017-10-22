package services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import models.Department;

import java.io.IOException;
import java.io.Writer;
import java.util.Collection;

public class SerializationService {
    public static void serializeCollection(Collection<Department> objects, Writer output) throws IOException {
        ObjectMapper mapper = new ObjectMapper()
                .registerModule(new JavaTimeModule());
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        mapper.writeValue(output, objects);
        output.flush();
    }
}
