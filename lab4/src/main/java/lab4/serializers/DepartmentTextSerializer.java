package lab4.serializers;

import lab4.Department;
import lab4.DepartmentBuilder;
import lab4.Faculty;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;

public class DepartmentTextSerializer implements Serializer<Department>{
    @Override
    public void serialize(Department object, File output) throws IOException {
        PrintWriter writer = new PrintWriter(output, "UTF-8");
        writer.write(generateString(object));
        writer.flush();
        writer.close();
    }

    @Override
    public void serializeCollection(Collection<Department> objects, File output) throws IOException {
        PrintWriter writer = new PrintWriter(output, "UTF-8");
        for (Department department : objects) {
            writer.write(generateString(department));
            writer.write("\n");
        }
        writer.flush();
        writer.close();
    }

    @Override
    public Department deserialize(File input) throws IOException {
        Scanner scanner = new Scanner(input, "UTF-8");
        String inputText = scanner.useDelimiter("\\A").next();
        scanner.close();
        return new DepartmentBuilder().fromString(inputText).build();
    }

    @Override
    public Collection<Department> deserializeCollection(File input) throws IOException {
        Scanner scanner = new Scanner(input, "UTF-8");
        String inputText = scanner.useDelimiter("\\A").next();
        ArrayList<Department> departments = new ArrayList<>();
        for (String s : inputText.split("\n")) {
            if (!s.isEmpty())
                departments.add(new DepartmentBuilder().fromString(s).build());
        }
        scanner.close();
        return departments;
    }

    static String generateString(Department department){
        StringBuilder sb = new StringBuilder();
        sb.append("Name: ");
        sb.append(department.getName());
        sb.append(";Created: ");
        sb.append(department.getCreationDate().toString());
        sb.append(";Number: ");
        sb.append(department.getPhoneNumber());
        sb.append(";Faculties: ");
        for(Faculty f: department.getSortedFaculties()){
            sb.append(FacultyTextSerializer.generateString(f));
            sb.append("/");
        }
        return sb.toString();
    }
}
