package zxf.practices.servlet.myjndi;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

@WebServlet(name = "myJndiServlet", urlPatterns = "/my-jndi-servlet", loadOnStartup = 1)
public class MyJndiServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println("Thread=" + Thread.currentThread().getName() + "::MyJndiServlet.doGet");

        try {
            Context initCtx = new InitialContext();
            Context envCtx = (Context) initCtx.lookup("java:comp/env");
            MyBean bean = (MyBean) envCtx.lookup("bean/MyBean");

            try (OutputStream outputStream = response.getOutputStream()) {
                outputStream.write(("foo = " + bean.getFoo() + ", bar = " + bean.getBar() + "\n").getBytes(StandardCharsets.UTF_8));
            }
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }
}
