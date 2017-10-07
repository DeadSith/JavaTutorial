import handlers.DepartmentHandler;
import handlers.FacultyHandler;
import handlers.HomeHandler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

public class Setup {
    public static void main(String[] args) throws Exception {
        Server server = new Server(8080);
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        server.setHandler(context);
        context.addServlet(new ServletHolder(new DepartmentHandler()),"/department/*");
        context.addServlet(new ServletHolder(new FacultyHandler()),"/faculty/*");
        context.addServlet(new ServletHolder(new HomeHandler()),"/*");
        server.start();
        server.join();
    }
}
