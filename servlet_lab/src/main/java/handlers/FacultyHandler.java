package handlers;

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
        if (path.equals("/department/add")) {
            writeAddForm(out);
            return;
        }
        Matcher match = getPattern.matcher(path);
        if (match.matches()) {
            writeFaculty(out, Integer.parseInt(match.group(1)));
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
        //todo
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
