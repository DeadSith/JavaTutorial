package other_labs.serializers;

import other_labs.Department;
import other_labs.DepartmentBuilder;
import other_labs.Faculty;

import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;

public class DepartmentTextSerializer implements Serializer<Department>{
    @Override
    public void serialize(Department object, Writer output) throws IOException {
        //PrintWriter writer = new PrintWriter(output, "UTF-8");
        output.write(generateString(object));
        output.flush();
        output.close();
    }

    @Override
    public void serializeCollection(Collection<Department> objects, Writer output) throws IOException {
        //PrintWriter writer = new PrintWriter(output, "UTF-8");
        for (Department department : objects) {
            output.write(generateString(department));
            output.write("\n");
        }
        output.flush();
        output.close();
    }

    @Override
    public Department deserialize(InputStream input) throws IOException {
        Scanner scanner = new Scanner(input, "UTF-8");
        String inputText = scanner.useDelimiter("\\A").next();
        scanner.close();
        return new DepartmentBuilder().fromString(inputText).build();
    }

    @Override
    public Collection<Department> deserializeCollection(InputStream input) throws IOException {
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
