package zxf.practices.servlet;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

@WebServlet(name = "myServlet", urlPatterns = "/my-servlet", loadOnStartup = 1)
public class MyServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Boolean hasSession = request.getSession(false) != null;
        String startTime = hasSession ? (String) request.getSession(false).getAttribute("start-time") : "";
        System.out.println("Thread=" + Thread.currentThread().getName() + "::MyServlet.doGet, HasSession=" + hasSession + ", start-time=" + startTime);

        if (!hasSession && "true".equals(request.getParameter("session"))) {
            request.getSession(true).setAttribute("start-time", LocalDateTime.now().toString());
        }

        try (OutputStream outputStream = response.getOutputStream()) {
            outputStream.write(("HasSession=" + hasSession + ", start-time=" + startTime + "\n").getBytes(StandardCharsets.UTF_8));
        }
    }
}
