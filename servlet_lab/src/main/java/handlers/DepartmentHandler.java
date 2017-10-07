package handlers;

import database.DepartmentContext;
import models.Department;
import models.Faculty;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DepartmentHandler extends HttpServlet {
    List<Department> departments;
    Pattern getPattern = Pattern.compile("/department/(\\d+)");
    Pattern editPattern = Pattern.compile("/department/edit/(\\d+)");
    Pattern deletePattern = Pattern.compile("/department/delete/(\\d+)");


    public void init() throws ServletException {
        try {
            departments = DepartmentContext.getDepartments();
        } catch (Exception ignored) {
            departments = new ArrayList<>();
        }
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Set response content type
        response.setContentType("text/html");
        String path = request.getRequestURI();
        PrintWriter out = response.getWriter();
        GeneralWriter.writeStart(out);
        if (path.equals("/department/add")) {
            writeAddForm(out);
            return;
        }
        Matcher match = getPattern.matcher(path);
        if (match.matches()) {
            writeDepartment(out, Integer.parseInt(match.group(1)));
            return;
        }
        match = editPattern.matcher(path);
        if (match.matches()) {
            writeEditForm(out, Integer.parseInt(match.group(1)));
            return;
        }
        response.sendRedirect("");
    }

    //todo
    private void writeDepartment(PrintWriter writer, int id) {
        try {
            Department d = DepartmentContext.getDepartment(id);
            writer.write("<a class=\"btn btn-warning\" href=\"/department/edit/" + d.getId() + "\">Edit</a>");
            writer.write("<a class=\"btn btn-danger\" href=\"/department/delete/" + id + "\">Delete</a>");
            writer.write("<a class=\"btn btn-success\" href=\"/faculty/add/" + id + "\">Add new faculty</a>");
            if (d.getFacultiesCount() > 0) {
                writer.write("<h3>Current faculties:</h3>");
                writer.write("<ul>");
                for (Faculty f : d.getSortedFaculties()) {
                    writer.write("<li><a href=\"/faculty/" + f.getId() + "\">" + f.getName() + "</a></li>");
                }
                writer.write("</ul>");
            } else
                writer.write("<div class=\"alert alert-warning\" role=\"alert\">This department has no faculties. Add one!</div>");
        } catch (Exception ignored) {
            writer.write("<div class=\"alert alert-danger\" role=\"alert\">There is no department with id " + id + "!</div>");
        }
        GeneralWriter.writeEnd(writer);
    }

    private void writeAddForm(PrintWriter writer) {
        //todo
        GeneralWriter.writeEnd(writer);
    }

    private void writeEditForm(PrintWriter writer, int id) {
        //todo
        GeneralWriter.writeEnd(writer);
    }

    public void destroy() {
        // do nothing.
    }
}
