package zxf.practices.servlet.mysession;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.UUID;

@WebFilter(filterName = "mySessionFilter", urlPatterns = "/*")
public class MySessionFilter implements Filter {
    protected MySessionRepository mySessionRepository = new MySessionRepository();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("Thread=" + Thread.currentThread().getName() + "::MySessionFilter.init");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        System.out.println("Thread=" + Thread.currentThread().getName() + "::MySessionFilter.doFilter");
        chain.doFilter(new MyHttpServletRequestWrapper((HttpServletRequest) request, (HttpServletResponse) response, mySessionRepository), response);
    }

    @Override
    public void destroy() {
        System.out.println("Thread=" + Thread.currentThread().getName() + "::MySessionFilter.destroy");
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
            MySession mySession = mySessionRepository.getSession(this.getRequestedSessionId());
            if (mySession != null) {
                System.out.println("Thread=" + Thread.currentThread().getName() + "::MyHttpServletRequestWrapper.getSession, HasSession");
                return mySession;
            }

            if (create) {
                System.out.println("Thread=" + Thread.currentThread().getName() + "::MyHttpServletRequestWrapper.getSession, CreateSession");
                MySession myNewSession = new MySession(UUID.randomUUID().toString(), mySessionRepository);
                mySessionRepository.saveSession(myNewSession);
                response.setHeader("My-Session-Id", myNewSession.getId());
                return myNewSession;
            }

            System.out.println("Thread=" + Thread.currentThread().getName() + "::MyHttpServletRequestWrapper.GetSession, NoSession");
            return null;
        }

        @Override
        public HttpSession getSession() {
            return getSession(false);
        }

        @Override
        public String getRequestedSessionId() {
            System.out.println("Thread=" + Thread.currentThread().getName() + "::MyHttpServletRequestWrapper.getRequestedSessionId, Id=" + this.getRequest().getParameter("My-Session-Id"));

            return this.getRequest().getParameter("My-Session-Id");
        }
    }
}
