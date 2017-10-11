package handlers;

import database.DepartmentContext;
import database.GeneralContext;
import models.Department;
import models.DepartmentBuilder;
import models.Faculty;
import models.FacultyBuilder;

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

    /**
     * writes beginning of file and calls the method to render page
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Set response content type
        String path = request.getRequestURI();
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        if (path.equals("/error")) {
            writeError(request, response);
            return;
        }
        Pattern p = Pattern.compile("/find/?(.+)");
        Matcher m = p.matcher(path);
        if (m.matches()) {
            writeFind(m.group(1), request, response);
            return;
        }
        writeMain(request, response);
    }

    /**
     * writes list of departments
     */
    private void writeMain(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Department> departments;
        try {
            departments = DepartmentContext.getDepartments();
        } catch (Exception ignored) {
            departments = new ArrayList<>();
        }
        request.setAttribute("departments", departments);
        request.getRequestDispatcher("/WEB-INF/views/home/index.jsp").forward(request, response);
    }

    /**
     * writes error message
     */
    private void writeError(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/views/home/error.jsp").forward(request, response);

    }

    /**
     * searches faculty/department by name
     *
     * @param name name of faculty/department to find
     */
    private void writeFind(String name, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Connection conn = GeneralContext.getNewConnection();
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT id, name FROM java.departments WHERE lower(name) LIKE '%" + name.toLowerCase() + "%';");
            List<Department> departments = new ArrayList<>();
            while (rs.next()) {
                departments.add(new DepartmentBuilder().setId(rs.getInt("id")).setName(rs.getString("name")).build());
            }
            List<Faculty> faculties = new ArrayList<>();
            rs = st.executeQuery("SELECT id, name FROM java.faculties WHERE lower(name) LIKE '%" + name.toLowerCase() + "%';");
            while (rs.next()) {
                faculties.add(new FacultyBuilder().setId(rs.getInt("id")).setName(rs.getString("name")).build());
            }
            request.setAttribute("departments", departments);
            request.setAttribute("faculties", faculties);
            request.getRequestDispatcher("/WEB-INF/views/home/find.jsp").forward(request, response);
        } catch (Exception ignored) {
            request.getRequestDispatcher("/WEB-INF/views/home/error.jsp").forward(request, response);
        }
    }

    /**
     * @param request search condition
     * @param response redirect to search
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect("/find/" + request.getParameter("search"));
    }

    public void destroy() {

    }
}
