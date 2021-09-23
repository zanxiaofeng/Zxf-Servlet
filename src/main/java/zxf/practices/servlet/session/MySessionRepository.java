package zxf.practices.servlet.session;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionContext;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MySessionRepository {
    private Map<String, MySession> mySessionMap = new ConcurrentHashMap<>();

    public MySession getSessionById(String sessionId) {
        System.out.println("MySessionRepository       ::getSessionById::Start::Name=" + Thread.currentThread().getName());
        if (sessionId == null) {
            return null;
        }
        return mySessionMap.get(sessionId);
    }

    public void AddSessionById(MySession mySession) {
        System.out.println("MySessionRepository       ::AddSessionById::Start::Name=" + Thread.currentThread().getName() + "ID: " + mySession.getId());
        mySessionMap.put(mySession.getId(), mySession);
    }

    public static class MySession implements HttpSession {
        private String id;
        private long creationTime;
        private long lastAccessedTime;
        private Map<String, Object> attributes;
        private Map<String, Object> values;

        public MySession(String id) {
            this.id = id;
            this.creationTime = System.currentTimeMillis();
            this.lastAccessedTime = System.currentTimeMillis();
            this.attributes = new HashMap<>();
            this.values = new HashMap<>();
        }

        @Override
        public String getId() {
            return id;
        }

        @Override
        public long getCreationTime() {
            return creationTime;
        }

        @Override
        public long getLastAccessedTime() {
            return lastAccessedTime;
        }

        @Override
        public ServletContext getServletContext() {
            return null;
        }

        @Override
        public void setMaxInactiveInterval(int interval) {

        }

        @Override
        public int getMaxInactiveInterval() {
            return 0;
        }

        @Override
        public HttpSessionContext getSessionContext() {
            return null;
        }

        @Override
        public Object getAttribute(String name) {
            return attributes.get(name);
        }

        @Override
        public Object getValue(String name) {
            return values.get(name);
        }

        @Override
        public Enumeration<String> getAttributeNames() {
            return null;
        }

        @Override
        public String[] getValueNames() {
            return null;
        }

        @Override
        public void setAttribute(String name, Object value) {
            attributes.put(name, value);
        }

        @Override
        public void putValue(String name, Object value) {
            values.put(name, value);
        }

        @Override
        public void removeAttribute(String name) {
            attributes.remove(name);
        }

        @Override
        public void removeValue(String name) {
            values.remove(name);
        }

        @Override
        public void invalidate() {

        }

        @Override
        public boolean isNew() {
            return false;
        }
    }
}