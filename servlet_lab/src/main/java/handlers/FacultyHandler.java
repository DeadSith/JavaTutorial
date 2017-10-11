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
    final Pattern getPattern = Pattern.compile("/faculty/(\\d+)");
    final Pattern addPattern = Pattern.compile("/faculty/add/(\\d+)");
    final Pattern editPattern = Pattern.compile("/faculty/edit/(\\d+)");
    final Pattern deletePattern = Pattern.compile("/faculty/delete/(\\d+)");

    public void init() throws ServletException {
        // Do required initialization
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
        Matcher match = getPattern.matcher(path);
        if (match.matches()) {
            writeFaculty(request, response, Integer.parseInt(match.group(1)));
            return;
        }
        match = addPattern.matcher(path);
        if (match.matches()) {
            writeAddForm(request, response, Integer.parseInt(match.group(1)));
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
     * writes information about faculty
     *
     * @param id id of faculty to write info about
     */
    private void writeFaculty(HttpServletRequest request, HttpServletResponse response, int id) throws ServletException, IOException {
        request.setAttribute("id", id);
        try {
            Faculty f = FacultyContext.getFaculty(id);
            Department d = FacultyContext.getParentDepartment(id);
            request.setAttribute("faculty", f);
            request.setAttribute("department", d);
        } catch (Exception ignored) {
        }
        request.getRequestDispatcher("/WEB-INF/views/faculty/index.jsp").forward(request, response);
    }

    /**
     * writes form to create faculty for department with {@code id}
     *
     * @param id id of parent
     */
    private void writeAddForm(HttpServletRequest request, HttpServletResponse response, int id) throws ServletException, IOException {
        request.setAttribute("id", id);
        request.getRequestDispatcher("/WEB-INF/views/faculty/add.jsp").forward(request, response);
    }

    /**
     * writes form to change existing faculty
     *
     * @param id id of faculty
     */
    private void writeEditForm(HttpServletRequest request, HttpServletResponse response, int id) throws ServletException, IOException {
        request.setAttribute("id", id);
        try {
            Faculty f = FacultyContext.getFaculty(id);
            request.setAttribute("faculty", f);
        } catch (Exception ignored) {
        }
        request.getRequestDispatcher("/WEB-INF/views/faculty/edit.jsp").forward(request, response);

    }

    /**
     * chooses method to use with post
     */
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
            deleteFaculty(Integer.parseInt(match.group(1)));
        }
        response.sendRedirect("/");
    }

    /**
     * creates new faculty
     *
     * @param departmentId id of parent department
     * @return id of created Faculty or 0, if failed
     */
    private int addFaculty(HttpServletRequest request, int departmentId) {
        try {
            Department d = DepartmentContext.getDepartment(departmentId);
            TreeSet<String> teachers = new TreeSet<>();
            TreeSet<String> subjects = new TreeSet<>();
            for (String s : request.getParameter("teachers").split("\n")) {
                if (!s.isEmpty())
                    teachers.add(s.trim());
            }
            for (String s : request.getParameter("subjects").split("\n")) {
                if (!s.isEmpty())
                    subjects.add(s.trim());
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

    /**
     * updates existing faculty
     *
     * @param id id of faculty to change
     * @return whether update was successful
     */
    private boolean editFaculty(HttpServletRequest request, int id) {
        try {
            Faculty f = FacultyContext.getFaculty(id);
            f.clearTeachersSubjects();
            for (String s : request.getParameter("teachers").split("\n")) {
                s = s.trim();
                if (!s.isEmpty() && !s.equals("\r")) {
                    s = s.replace("\r", "");
                    f.addTeacher(s);
                }
            }
            for (String s : request.getParameter("subjects").split("\n")) {
                s = s.trim();
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

    /**
     * @param id id of faculty to delete
     */
    private void deleteFaculty(int id) {
        try {
            FacultyContext.deleteFaculty(id);
        } catch (Exception ignored) {
        }
    }

    public void destroy() {
        // do nothing.
    }
}
