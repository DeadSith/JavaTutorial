package handlers;

import database.DepartmentContext;
import database.FacultyContext;
import models.Department;
import models.Faculty;
import models.FacultyBuilder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FacultyHandler extends HttpServlet {
    Pattern getPattern = Pattern.compile("/faculty/(\\d+)");
    Pattern addPattern = Pattern.compile("/faculty/add/(\\d+)");
    Pattern editPattern = Pattern.compile("/faculty/edit/(\\d+)");
    Pattern deletePattern = Pattern.compile("/faculty/delete/(\\d+)");

    public void init() throws ServletException {
        // Do required initialization
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Set response content type
        response.setContentType("text/html");
        String path = request.getRequestURI();
        PrintWriter out = response.getWriter();
        GeneralWriter.writeStart(out);
        Matcher match = getPattern.matcher(path);
        if (match.matches()) {
            writeFaculty(out, Integer.parseInt(match.group(1)));
            return;
        }
        match = addPattern.matcher(path);
        if (match.matches()) {
            writeAddForm(out, Integer.parseInt(match.group(1)));
            return;
        }
        match = editPattern.matcher(path);
        if (match.matches()) {
            writeEditForm(out, Integer.parseInt(match.group(1)));
            return;
        }
        response.sendRedirect("");
    }

    private void writeFaculty(PrintWriter writer, int id) {
        try {
            Faculty f = FacultyContext.getFaculty(id);
            Department d = FacultyContext.getParentDepartment(id);
            writer.write("<a class=\"btn btn-warning\" role=\"button\" href=\"/faculty/edit/" + id + "\">Edit</a>" +
                    "<form action=\"/faculty/delete/" + id + "\" method=post style=\"display:inline;\">" +
                    "<button class=\"btn btn-danger\" type=\"submit\" role=\"button\">Delete</button></form>" +
                    "<dl class=\"row\">" +
                    "<dt class=\"col-sm-3\">Name: </dt>\n" +
                    "<dd class=\"col-sm-9\">" + f.getName() + "</dd>" +
                    "<dt class=\"col-sm-3\">Created at: </dt>\n" +
                    "<dd class=\"col-sm-9\">" + f.getCreationDate() + "</dd>" +
                    "<dt class=\"col-sm-3\">Department: </dt>\n" +
                    "<dd class=\"col-sm-9\"><a href=\"/department/" + d.getId() + "\">" + d.getName() + "</a></dd>" +
                    "<dt class=\"col-sm-3\">Current teachers: </dt>" +
                    "<dd class=\"col-sm-9\"><ul>");
            for (String t : f.getTeachers()) {
                writer.write("<li>" + t + "</li>");
            }
            writer.write("</ul></dd>" +
                    "<dt class=\"col-sm-3\">Current subjects: </dt>" +
                    "<dd class=\"col-sm-9\"><ul>");
            for (String t : f.getSubjects()) {
                writer.write("<li>" + t + "</li>");
            }
            writer.write("</ul></dd>\n</dl>");
        } catch (Exception ignored) {
            writer.write("<div class=\"alert alert-danger\" role=\"alert\">There is no faculty with id " + id + "!</div>");
        }
        GeneralWriter.writeEnd(writer);
    }

    private void writeAddForm(PrintWriter writer, int id) {
        writer.write("<form action=\"/faculty/add/" + id + "\" method=\"post\">\n" +
                "  <div class=\"form-group row\">\n" +
                "    <label for=\"name\" class=\"col-sm-2 col-form-label\">Name: </label>\n" +
                "    <div class=\"col-sm-10\">\n<input type=\"text\" class=\"form-control\" name=\"name\"  placeholder=\"Enter name\">\n</div>\n" +
                "  </div>\n" +
                "  <div class=\"form-group row\">\n" +
                "    <label for=\"creationDate\" class=\"col-sm-2 col-form-label\">Creation date: </label>\n" +
                "    <div class=\"col-sm-10\">\n<input type=\"date\" class=\"form-control\" name=\"creationDate\">\n</div>\n" +
                "  </div>\n" +
                "  <div class=\"form-group row\">\n" +
                "    <label for=\"teachers\" class=\"col-sm-2 col-form-label\">Teachers(separate with new line): </label>\n" +
                "    <div class=\"col-sm-10\">\n<textarea class=\"form-control\" name=\"teachers\" rows=\"4\"></textarea>\n</div>\n" +
                "  </div>\n" +
                "  <div class=\"form-group row\">\n" +
                "    <label for=\"subjects\" class=\"col-sm-2 col-form-label\">Subjects(separate with new line): </label>\n" +
                "    <div class=\"col-sm-10\">\n<textarea class=\"form-control\" name=\"subjects\" rows=\"4\"></textarea>\n</div>\n" +
                "  </div>\n" +
                "  <button type=\"submit\" class=\"btn btn-primary\">Submit</button>\n" +
                "</form>");
        GeneralWriter.writeEnd(writer);
    }

    private void writeEditForm(PrintWriter writer, int id) {
        try {
            Faculty f = FacultyContext.getFaculty(id);
            writer.write("<form action=\"/faculty/edit/" + id + "\" method=\"post\">\n" +
                    "  <div class=\"form-group row\">\n" +
                    "    <label for=\"name\" class=\"col-sm-2 col-form-label\">Name: </label>\n" +
                    "    <div class=\"col-sm-10\">\n<input type=\"text\" class=\"form-control\" name=\"name\"  value=\"" + f.getName() + "\">\n</div>\n" +
                    "  </div>\n" +
                    "  <div class=\"form-group row\">\n" +
                    "    <label for=\"teachers\" class=\"col-sm-2 col-form-label\">Teachers(separate with new line): </label>\n" +
                    "    <div class=\"col-sm-10\">\n<textarea class=\"form-control\" name=\"teachers\" rows=\"4\">");
            for (String t : f.getTeachers()) {
                writer.write(t + "&#13;&#10;");
            }
            writer.write("</textarea>\n</div>\n" +
                    "  </div>\n" +
                    "  <div class=\"form-group row\">\n" +
                    "    <label for=\"subjects\" class=\"col-sm-2 col-form-label\">Subjects(separate with new line): </label>\n" +
                    "    <div class=\"col-sm-10\">\n<textarea class=\"form-control\" name=\"subjects\" rows=\"4\">");
            for (String s : f.getSubjects()) {
                writer.write(s + "&#13;&#10;");
            }
            writer.write("</textarea>\n</div>\n" +
                    "  </div>\n" +
                    "  <button type=\"submit\" class=\"btn btn-primary\">Submit</button>\n" +
                    "</form>");
        } catch (Exception ignored) {
            writer.write("<div class=\"alert alert-danger\" role=\"alert\">There is no faculty with id " + id + "!</div>");
        }
        GeneralWriter.writeEnd(writer);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String path = request.getRequestURI();
        Matcher match = editPattern.matcher(path);
        if (match.matches()) {
            if (editFaculty(request, Integer.parseInt(match.group(1))))
                response.sendRedirect("/faculty/" + match.group(1));
            else response.sendRedirect("/error");
            return;
        }
        match = addPattern.matcher(path);
        if (match.matches()) {
            int i = addFaculty(request, Integer.parseInt(match.group(1)));
            if (i != 0)
                response.sendRedirect("/faculty/" + i);
            else response.sendRedirect("/error");
            return;
        }
        match = deletePattern.matcher(path);
        if (match.matches()) {
            deleteDepartment(Integer.parseInt(match.group(1)));
        }
        response.sendRedirect("/");
    }

    private int addFaculty(HttpServletRequest request, int departmentId) {
        try {
            Department d = DepartmentContext.getDepartment(departmentId);
            TreeSet<String> teachers = new TreeSet<>();
            TreeSet<String> subjects = new TreeSet<>();
            for (String s : request.getParameter("teachers").split("\n")) {
                if (!s.isEmpty())
                    teachers.add(s);
            }
            for (String s : request.getParameter("subjects").split("\n")) {
                if (!s.isEmpty())
                    subjects.add(s);
            }
            Faculty f = new FacultyBuilder()
                    .setDepartment(d)
                    .setName(request.getParameter("name"))
                    .setCreationDate(LocalDate.parse(request.getParameter("creationDate")))
                    .setSubjects(subjects)
                    .setTeachers(teachers)
                    .build();
            FacultyContext.addFaculty(f);
            return f.getId();
        } catch (Exception ignored) {
            return 0;
        }
    }

    private boolean editFaculty(HttpServletRequest request, int id) {
        try {
            Faculty f = FacultyContext.getFaculty(id);
            f.clearTeachersSubjects();
            for (String s : request.getParameter("teachers").split("\n")) {
                if (!s.isEmpty() && !s.equals("\r")) {
                    s = s.replace("\r", "");
                    f.addTeacher(s);
                }
            }
            for (String s : request.getParameter("subjects").split("\n")) {
                if (!s.isEmpty() && !s.equals("\r")) {
                    s = s.replace("\r", "");
                    f.addSubject(s);
                }
            }
            f.setName(request.getParameter("name"));
            return FacultyContext.updateFaculty(f);
        } catch (Exception ignored) {
            return false;
        }
    }

    private void deleteDepartment(int id) {
        try {
            FacultyContext.deleteFaculty(id);
        } catch (Exception ignored) {
        }
    }

    public void destroy() {
        // do nothing.
    }
}
