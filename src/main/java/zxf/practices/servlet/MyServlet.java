package zxf.practices.servlet;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

@WebServlet(name = "myServlet", urlPatterns = "/my-servlet", loadOnStartup = 1)
public class MyServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Boolean hasSession = request.getSession(false) != null;
        Long time = hasSession ? (Long)request.getSession(false).getAttribute("time"):0L;
        System.out.println("MyServlet       ::doGet::Start::Name=" + Thread.currentThread().getName() + " HasSession=" + hasSession + ", time=" + time);
        if (!hasSession && "true".equals(request.getParameter("session"))){
            request.getSession(true).setAttribute("time", System.currentTimeMillis());
        }
        try (OutputStream outputStream = response.getOutputStream()){
           outputStream.write(("Session=" + hasSession + ", time="+ time).getBytes(StandardCharsets.UTF_8));
        }
    }
}
