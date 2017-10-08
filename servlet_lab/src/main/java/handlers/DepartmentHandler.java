package handlers;

import database.DepartmentContext;
import models.Department;
import models.DepartmentBuilder;
import models.Faculty;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
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
        response.sendRedirect("/");
    }

    private void writeDepartment(PrintWriter writer, int id) {
        try {
            Department d = DepartmentContext.getDepartment(id);
            writer.write("<a class=\"btn btn-primary\" role=\"button\" href=\"/faculty/add/" + d.getId() + "\">Add new faculty</a>" +
                    "<a class=\"btn btn-warning\" role=\"button\" href=\"/department/edit/" + id + "\">Edit</a>" +
                    "<form action=\"/department/delete/" + id + "\" method=post style=\"display:inline;\">" +
                    "<button class=\"btn btn-danger\" type=\"submit\" role=\"button\">Delete</button></form>" +
                    "<dl class=\"row\">" +
                    "<dt class=\"col-sm-3\">Name: </dt>\n" +
                    "<dd class=\"col-sm-9\">" + d.getName() + "</dd>" +
                    "<dt class=\"col-sm-3\">Phone number: </dt>\n" +
                    "<dd class=\"col-sm-9\">" + d.getPhoneNumber() + "</dd>" +
                    "<dt class=\"col-sm-3\">Created at: </dt>\n" +
                    "<dd class=\"col-sm-9\">" + d.getCreationDate() + "</dd>");
            if (d.getFacultiesCount() > 0) {
                writer.write("<dt class=\"col-sm-3\">Current faculties: </dt>");
                writer.write("<dd class=\"col-sm-9\"><ul>");
                for (Faculty f : d.getSortedFaculties()) {
                    writer.write("<li><a href=\"/faculty/" + f.getId() + "\">" + f.getName() + "</a></li>");
                }
                writer.write("</ul></dd>");
                writer.write("</dl>");
            } else
                writer.write("<div class=\"alert alert-warning\" role=\"alert\">This department has no faculties. Add one!</div>");
        } catch (Exception ignored) {
            writer.write("<div class=\"alert alert-danger\" role=\"alert\">There is no department with id " + id + "!</div>");
        }
        GeneralWriter.writeEnd(writer);
    }

    private void writeAddForm(PrintWriter writer) {
        writer.write("<form action=\"/department/add\" method=\"post\">\n" +
                "  <div class=\"form-group row\">\n" +
                "    <label for=\"name\" class=\"col-sm-2 col-form-label\">Name: </label>\n" +
                "    <div class=\"col-sm-10\">\n<input type=\"text\" class=\"form-control\" name=\"name\"  placeholder=\"Enter name\">\n</div>\n" +
                "  </div>\n" +
                "  <div class=\"form-group row\">\n" +
                "    <label for=\"phoneNumber\" class=\"col-sm-2 col-form-label\">Phone number: </label>\n" +
                "    <div class=\"col-sm-10\">\n<input type=\"tel\" class=\"form-control\" name=\"phoneNumber\" placeholder=\"+38xxxxxxxxxx\">\n</div>\n" +
                "  </div>\n" +
                "  <div class=\"form-group row\">\n" +
                "    <label for=\"creationDate\" class=\"col-sm-2 col-form-label\">Creation date: </label>\n" +
                "    <div class=\"col-sm-10\">\n<input type=\"date\" class=\"form-control\" name=\"creationDate\">\n</div>\n" +
                "  </div>\n" +
                "  <button type=\"submit\" class=\"btn btn-primary\">Submit</button>\n" +
                "</form>");
        GeneralWriter.writeEnd(writer);
    }

    private void writeEditForm(PrintWriter writer, int id) {
        try {
            Department d = DepartmentContext.getDepartment(id);
            writer.write("<form action=\"/department/edit/" + id + "\" method=\"post\">\n" +
                    "  <div class=\"form-group row\">\n" +
                    "    <label for=\"name\" class=\"col-sm-2 col-form-label\">Name: </label>\n" +
                    "    <div class=\"col-sm-10\">\n<input type=\"text\" class=\"form-control\" name=\"name\"  value=\"" + d.getName() + "\">\n</div>\n" +
                    "  </div>\n" +
                    "  <div class=\"form-group row\">\n" +
                    "    <label for=\"phoneNumber\" class=\"col-sm-2 col-form-label\">Phone number: </label>\n" +
                    "    <div class=\"col-sm-10\">\n<input type=\"tel\" class=\"form-control\" name=\"phoneNumber\" value=\"" + d.getPhoneNumber() + "\">\n</div>\n" +
                    "  </div>\n" +
                    "  <button type=\"submit\" class=\"btn btn-primary\">Submit</button>\n" +
                    "</form>");
        } catch (Exception ignored) {
            writer.write("<div class=\"alert alert-danger\" role=\"alert\">There is no department with id " + id + "!</div>");
        }
        GeneralWriter.writeEnd(writer);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String path = request.getRequestURI();
        Matcher match = editPattern.matcher(path);
        if (match.matches()) {
            if (editDepartment(request, Integer.parseInt(match.group(1))))
                response.sendRedirect("/department/" + match.group(1));
            else response.sendRedirect("/error");
            return;
        }
        if (path.equals("/department/add")) {
            int i = addDepartment(request);
            if (i != 0)
                response.sendRedirect("/department/" + i);
            else response.sendRedirect("/error");
            return;
        }
        match = deletePattern.matcher(path);
        if (match.matches()) {
            deleteDepartment(Integer.parseInt(match.group(1)));
        }
        response.sendRedirect("/");
    }

    private int addDepartment(HttpServletRequest request) {
        Department d = new DepartmentBuilder()
                .setName(request.getParameter("name"))
                .setPhoneNumber(request.getParameter("phoneNumber"))
                .setCreationDate(LocalDate.parse(request.getParameter("creationDate")))
                .build();
        try {
            DepartmentContext.addDepartment(d);
            return d.getId();
        } catch (Exception ignored) {
            return 0;
        }
    }

    private boolean editDepartment(HttpServletRequest request, int id) {
        try {
            Department d = DepartmentContext.getDepartment(id);
            if (d == null)
                return false;
            d.setName(request.getParameter("name"));
            d.setPhoneNumber(request.getParameter("phoneNumber"));
            return DepartmentContext.updateDepartment(d);
        } catch (Exception ignored) {
            return false;
        }
    }

    private void deleteDepartment(int id) {
        try {
            DepartmentContext.deleteDepartment(id);
        } catch (Exception ignored) {
        }
    }

    public void destroy() {
        // do nothing.
    }
}
