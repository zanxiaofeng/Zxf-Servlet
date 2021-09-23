package zxf.practices.servlet.session;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.UUID;

@WebFilter(filterName = "mySession filter", urlPatterns = "/*")
public class MySessionFilter implements Filter {
    protected MySessionRepository mySessionRepository = new MySessionRepository();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        System.out.println("MySessionFilter       ::doFilter::Start::Name=" + Thread.currentThread().getName());
        chain.doFilter(new MyHttpServletRequestWrapper((HttpServletRequest) request, (HttpServletResponse) response, mySessionRepository), response);
    }

    @Override
    public void destroy() {

    }

    public static class MyHttpServletRequestWrapper extends HttpServletRequestWrapper {
        private HttpServletResponse response;
        private MySessionRepository mySessionRepository;

        public MyHttpServletRequestWrapper(HttpServletRequest request, HttpServletResponse response, MySessionRepository mySessionRepository) {
            super(request);
            this.response = response;
            this.mySessionRepository = mySessionRepository;
        }

        @Override
        public HttpSession getSession(boolean create) {
            MySessionRepository.MySession mySession = mySessionRepository.getSession(this.getRequestedSessionId());
            if (mySession != null) {
                System.out.println("MyHttpServletRequestWrapper       ::getSession::HasSession::Name=" + Thread.currentThread().getName());
                return mySession;
            }

            if (create) {
                System.out.println("MyHttpServletRequestWrapper       ::getSession::CreateSession::Name=" + Thread.currentThread().getName());
                MySessionRepository.MySession myNewSession = new MySessionRepository.MySession(UUID.randomUUID().toString(), mySessionRepository);
                mySessionRepository.saveSession(myNewSession);
                response.setHeader("My-Session-Id", myNewSession.getId());
                return myNewSession;
            }

            System.out.println("MyHttpServletRequestWrapper       ::GetSession::NoSession::Name=" + Thread.currentThread().getName());
            return null;
        }


        @Override
        public HttpSession getSession() {
            return getSession(false);
        }

        @Override
        public String getRequestedSessionId() {
            System.out.println("MyHttpServletRequestWrapper       ::getRequestedSessionId::Start::Name=" + Thread.currentThread().getName() + ", Id: " + this.getRequest().getParameter("My-Session-Id"));
            return this.getRequest().getParameter("My-Session-Id");
        }
    }
}
