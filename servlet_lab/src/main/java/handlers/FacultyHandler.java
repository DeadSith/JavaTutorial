package handlers;

import database.FacultyContext;
import models.Department;
import models.Faculty;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
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
                    "<a class=\"btn btn-danger\" role=\"button\" href=\"/faculty/delete/" + id + "\">Delete</a>" +
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
                "    <label for=\"id\" class=\"col-sm-2 col-form-label\">Id: </label>\n" +
                "    <div class=\"col-sm-10\">\n<input type=\"text\" class=\"form-control\" readonly id=\"id\" value=\"" + id + "\">\n</div>\n" +
                "  </div>\n" +
                "  <div class=\"form-group row\">\n" +
                "    <label for=\"name\" class=\"col-sm-2 col-form-label\">Name: </label>\n" +
                "    <div class=\"col-sm-10\">\n<input type=\"text\" class=\"form-control\" id=\"name\"  placeholder=\"Enter name\">\n</div>\n" +
                "  </div>\n" +
                "  <div class=\"form-group row\">\n" +
                "    <label for=\"creationDate\" class=\"col-sm-2 col-form-label\">Creation date: </label>\n" +
                "    <div class=\"col-sm-10\">\n<input type=\"date\" class=\"form-control\" id=\"creationDate\">\n</div>\n" +
                "  </div>\n" +
                "  <div class=\"form-group row\">\n" +
                "    <label for=\"teachers\" class=\"col-sm-2 col-form-label\">Teachers(separate with new line): </label>\n" +
                "    <div class=\"col-sm-10\">\n<textarea class=\"form-control\" id=\"teachers\" rows=\"4\"></textarea>\n</div>\n" +
                "  </div>\n" +
                "  <div class=\"form-group row\">\n" +
                "    <label for=\"subjects\" class=\"col-sm-2 col-form-label\">Subjects(separate with new line): </label>\n" +
                "    <div class=\"col-sm-10\">\n<textarea class=\"form-control\" id=\"subjects\" rows=\"4\"></textarea>\n</div>\n" +
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
                    "    <div class=\"col-sm-10\">\n<input type=\"text\" class=\"form-control\" id=\"name\"  value=\"" + f.getName() + "\">\n</div>\n" +
                    "  </div>\n" +
                    "  <div class=\"form-group row\">\n" +
                    "    <label for=\"teachers\" class=\"col-sm-2 col-form-label\">Teachers(separate with new line): </label>\n" +
                    "    <div class=\"col-sm-10\">\n<textarea class=\"form-control\" id=\"teachers\" rows=\"4\">");
            for (String t : f.getTeachers()) {
                writer.write(t + "&#13;&#10;");
            }
            writer.write("</textarea>\n</div>\n" +
                    "  </div>\n" +
                    "  <div class=\"form-group row\">\n" +
                    "    <label for=\"subjects\" class=\"col-sm-2 col-form-label\">Subjects(separate with new line): </label>\n" +
                    "    <div class=\"col-sm-10\">\n<textarea class=\"form-control\" id=\"subjects\" rows=\"4\">");
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

    public void destroy() {
        // do nothing.
    }
}
