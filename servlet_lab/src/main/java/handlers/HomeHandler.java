package handlers;

import database.DepartmentContext;
import models.Department;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class HomeHandler extends HttpServlet {
    List<Department> departments;

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
        PrintWriter out = response.getWriter();
        GeneralWriter.writeStart(out);
        out.write("<a class=\"btn btn-primary\" href=\"/department/add\">Add new department</a>");
        if (departments.size() == 0)
            out.write("<div class=\"alert alert-danger\" role=\"alert\">There are no departments. Add one to begin.</div>");
        else {
            out.write("<h3>Current departments:</h3>");
            out.write("<ul>");
            for (Department d : departments) {
                out.write("<li><a href=\"/department/" + d.getId() + "\">" + d.getName() + "</a></li>");
            }
            out.write("</ul>");
        }
        GeneralWriter.writeEnd(out);
    }

    public void destroy() {
        departments = null;
    }
}
