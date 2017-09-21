package lab4.serializers;

import lab4.Faculty;
import lab4.FacultyBuilder;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;

public class FacultyTextSerializer implements Serializer<Faculty> {
    @Override
    public void serialize(Faculty object, File output) throws IOException {
        PrintWriter writer = new PrintWriter(output, "UTF-8");
        writer.write(generateString(object));
        writer.flush();
        writer.close();
    }

    @Override
    public void serializeCollection(Collection<Faculty> objects, File output) throws IOException {
        PrintWriter writer = new PrintWriter(output, "UTF-8");
        for (Faculty faculty : objects) {
            writer.write(generateString(faculty));
            writer.write("/");
        }
        writer.flush();
        writer.close();
    }

    @Override
    public Faculty deserialize(File input) throws IOException {
        Scanner scanner = new Scanner(input, "UTF-8");
        String inputText = scanner.useDelimiter("\\A").next();
        scanner.close();
        return new FacultyBuilder().fromString(inputText).build();
    }

    @Override
    public Collection<Faculty> deserializeCollection(File input) throws IOException {
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
