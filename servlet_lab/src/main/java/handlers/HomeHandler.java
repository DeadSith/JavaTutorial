package handlers;

import database.DepartmentContext;
import database.GeneralContext;
import models.Department;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HomeHandler extends HttpServlet {


    public void init() throws ServletException {

    }

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Set response content type
        String path = request.getRequestURI();
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        GeneralWriter.writeStart(out);
        if (path.equals("/error")) {
            writeError(out);
            return;
        }
        Pattern p = Pattern.compile("/find/?(.+)");
        Matcher m = p.matcher(path);
        if (m.matches()) {
            writeFind(m.group(1), out);
            return;
        }
        writeMain(out);
    }

    private void writeMain(PrintWriter writer) {
        List<Department> departments;
        try {
            departments = DepartmentContext.getDepartments();
        } catch (Exception ignored) {
            departments = new ArrayList<>();
        }
        writer.write("<a class=\"btn btn-primary\" href=\"/department/add\">Add new department</a>");
        if (departments.size() == 0)
            writer.write("<div class=\"alert alert-danger\" role=\"alert\">There are no departments. Add one to begin.</div>");
        else {
            writer.write("<h3>Current departments:</h3><ul>");
            for (Department d : departments) {
                writer.write("<li><a href=\"/department/" + d.getId() + "\">" + d.getName() + "</a></li>");
            }
            writer.write("</ul>");
        }
        GeneralWriter.writeEnd(writer);
    }

    private void writeError(PrintWriter writer) {
        writer.write("<div class=\"alert alert-danger\" role=\"alert\">Something went wrong</div>");
    }

    private void writeFind(String name, PrintWriter writer) {
        try {
            Connection conn = GeneralContext.getNewConnection();
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT id, name FROM java.departments WHERE lower(name) LIKE '%" + name.toLowerCase() + "%';");
            writer.write("<h4>Departments: </h4><ul>");
            while (rs.next()) {
                writer.write("<li><a href=\"/department/" + rs.getInt("id") + "\">" + rs.getString("name") + "</a></li>");
            }
            writer.write("</ul>\n<h4>Faculties: </h4><ul>");
            rs = st.executeQuery("SELECT id, name FROM java.faculties WHERE lower(name) LIKE '%" + name.toLowerCase() + "%';");
            while (rs.next()) {
                writer.write("<li><a href=\"/faculty/" + rs.getInt("id") + "\">" + rs.getString("name") + "</a></li>");
            }
            writer.write("</ul>");
        } catch (Exception ignored) {
            writer.write("<div class=\"alert alert-warning\" role=\"alert\">Something went wrong</div>");
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect("/find/" + request.getParameter("search"));
    }

    public void destroy() {

    }
}
