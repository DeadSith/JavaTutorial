package other_labs.serializers;

import other_labs.Faculty;
import other_labs.FacultyBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;

public class FacultyTextSerializer implements Serializer<Faculty> {
    @Override
    public void serialize(Faculty object, Writer output) throws IOException {
        output.write(generateString(object));
        output.flush();
        output.close();
    }

    @Override
    public void serializeCollection(Collection<Faculty> objects, Writer output) throws IOException {
        //PrintWriter writer = new PrintWriter(output, "UTF-8");
        for (Faculty faculty : objects) {
            output.write(generateString(faculty));
            output.write("/");
        }
        output.flush();
        output.close();
    }

    @Override
    public Faculty deserialize(InputStream input) throws IOException {
        Scanner scanner = new Scanner(input, "UTF-8");
        String inputText = scanner.useDelimiter("\\A").next();
        scanner.close();
        return new FacultyBuilder().fromString(inputText).build();
    }

    @Override
    public Collection<Faculty> deserializeCollection(InputStream input) throws IOException {
        Scanner scanner = new Scanner(input, "UTF-8");
        String inputText = scanner.useDelimiter("\\A").next();
        ArrayList<Faculty> faculties = new ArrayList<>();
        for (String s : inputText.split("/")) {
            if (!s.isEmpty())
                faculties.add(new FacultyBuilder().fromString(s).build());
        }
        scanner.close();
        return faculties;
    }

    static String generateString(Faculty faculty) {
        StringBuilder sb = new StringBuilder();
        sb.append("Name: ");
        sb.append(faculty.getName());
        sb.append(";Created: ");
        sb.append(faculty.getCreationDate().toString());
        sb.append(";Teachers: ");
        for (String s : faculty.getTeachers()) {
            sb.append(s);
            sb.append(",");
        }
        sb.replace(sb.length() - 1, sb.length(), ";");
        sb.append("Subjects: ");
        for (String s : faculty.getSubjects()) {
            sb.append(s);
            sb.append(",");
        }
        sb.replace(sb.length() - 1, sb.length(), ";");
        return sb.toString();
    }
}
