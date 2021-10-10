package zxf.practices.servlet.mysession;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

@WebServlet(name = "mySessionServlet", urlPatterns = "/my-session-servlet", loadOnStartup = 1)
public class MySessionServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Boolean hasSession = request.getSession(false) != null;
        MyObject start = hasSession ? (MyObject) request.getSession(false).getAttribute("start") : new MyObject();
        System.out.println("Thread=" + Thread.currentThread().getName() + "::MySessionServlet.doGet, HasSession=" + hasSession + ", start=" + start);

        if (!hasSession && "true".equals(request.getParameter("session"))) {
            start = new MyObject(request.getParameter("name"), LocalDateTime.now().toString());
            request.getSession(true).setAttribute("start", start);
        }

        try (OutputStream outputStream = response.getOutputStream()) {
            outputStream.write(("HasSession=" + hasSession + ", start-time=" + start + "\n").getBytes(StandardCharsets.UTF_8));
        }
    }
}
