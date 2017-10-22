package handlers;

import database.DepartmentContext;
import models.Department;
import models.DepartmentBuilder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DepartmentHandler extends HttpServlet {
    final Pattern getPattern = Pattern.compile("/department/(\\d+)");
    final Pattern editPattern = Pattern.compile("/department/edit/(\\d+)");
    final Pattern deletePattern = Pattern.compile("/department/delete/(\\d+)");


    public void init() throws ServletException {
    }

    /**
     * writes beginning of file. Chooses which page to render and calls required method
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Set response content type
        response.setContentType("text/html");
        String path = request.getRequestURI();
        PrintWriter out = response.getWriter();
        GeneralWriter.writeStart(out);
        if (path.equals("/department/add")) {
            writeAddForm(request, response);
            return;
        }
        Matcher match = getPattern.matcher(path);
        if (match.matches()) {
            writeDepartment(request, response, Integer.parseInt(match.group(1)));
            return;
        }
        match = editPattern.matcher(path);
        if (match.matches()) {
            writeEditForm(request, response, Integer.parseInt(match.group(1)));
            return;
        }
        response.sendRedirect("/error");
    }

    /**
     * writes info about department with id {@code id}
     *
     * @param id department to write about
     */
    private void writeDepartment(HttpServletRequest request, HttpServletResponse response, int id) throws ServletException, IOException {
        request.setAttribute("id", id);
        try {
            Department d = DepartmentContext.getDepartment(id);
            request.setAttribute("department", d);
        } catch (Exception ignored) {
        }
        request.getRequestDispatcher("/WEB-INF/views/department/index.jsp").forward(request, response);
    }

    /**
     * writes from to add new department
     */
    private void writeAddForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/views/department/add.jsp").forward(request, response);
    }

    /**
     * writes from to edit department with id {@code id}
     *
     * @param id department to edit
     */
    private void writeEditForm(HttpServletRequest request, HttpServletResponse response, int id) throws ServletException, IOException {
        request.setAttribute("id", id);
        try {
            Department d = DepartmentContext.getDepartment(id);
            request.setAttribute("department", d);
        } catch (Exception ignored) {
            response.sendRedirect("/error");
        }
        request.getRequestDispatcher("/WEB-INF/views/department/edit.jsp").forward(request, response);
    }

    /**
     * chooses method to use with post
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String path = request.getRequestURI();
        Matcher match = editPattern.matcher(path);
        if (match.matches()) {
            try {
                editDepartment(request, response, Integer.parseInt(match.group(1)));
            } catch (SQLException | ClassNotFoundException ignored) {
                response.sendRedirect("/error");
            }
            return;
        }
        if (path.equals("/department/add")) {
            addDepartment(request, response);
            return;
        }
        match = deletePattern.matcher(path);
        if (match.matches()) {
            deleteDepartment(Integer.parseInt(match.group(1)));
        }
        response.sendRedirect("/");
    }

    /**
     * creates new department
     */
    private void addDepartment(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Department d = new DepartmentBuilder()
                    .setName(request.getParameter("name"))
                    .setPhoneNumber(request.getParameter("phoneNumber"))
                    .setCreationDate(LocalDate.parse(request.getParameter("creationDate")))
                    .build();
            DepartmentContext.addDepartment(d);
            response.sendRedirect("/department/" + d.getId());
        } catch (Exception ignored) {
            request.setAttribute("error", true);
            writeAddForm(request, response);
        }
    }

    /**
     * updates fields of department with id {@code id}
     *
     * @param id id of department to edit
     * @return whether updated successfully
     */
    private void editDepartment(HttpServletRequest request, HttpServletResponse response, int id) throws ServletException, IOException, SQLException, ClassNotFoundException {
        Department d = DepartmentContext.getDepartment(id);
        if (d == null) {
            response.sendRedirect("/department/add/" + id);
        }
        try {
            d.setName(request.getParameter("name"));
            d.setPhoneNumber(request.getParameter("phoneNumber"));
            DepartmentContext.updateDepartment(d);
            response.sendRedirect("/department/" + id);
        } catch (Exception ignored) {
            request.setAttribute("error", true);
            writeEditForm(request, response, id);
        }
    }

    /**
     * @param id department to delete
     */
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
