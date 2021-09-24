package zxf.practices.servlet.mysession;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionContext;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

public class MySession implements HttpSession {
    @JsonIgnore
    private MySessionRepository mySessionRepository;
    @JsonProperty
    private String id;
    @JsonProperty
    private long creationTime;
    @JsonProperty
    private long lastAccessedTime;
    @JsonProperty
    private Map<String, Object> attributes;
    @JsonProperty
    private Map<String, Object> values;

    public MySession(String id, MySessionRepository mySessionRepository) {
        this.mySessionRepository = mySessionRepository;
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
    @JsonIgnore
    public ServletContext getServletContext() {
        return null;
    }

    @Override
    public void setMaxInactiveInterval(int interval) {

    }

    @Override
    @JsonIgnore
    public int getMaxInactiveInterval() {
        return 0;
    }

    @Override
    @JsonIgnore
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
    @JsonIgnore
    public Enumeration<String> getAttributeNames() {
        return null;
    }

    @Override
    @JsonIgnore
    public String[] getValueNames() {
        return null;
    }

    @Override
    public void setAttribute(String name, Object value) {
        attributes.put(name, value);
        mySessionRepository.saveSession(this);
    }

    @Override
    public void putValue(String name, Object value) {
        values.put(name, value);
        mySessionRepository.saveSession(this);
    }

    @Override
    public void removeAttribute(String name) {
        attributes.remove(name);
        mySessionRepository.saveSession(this);
    }

    @Override
    public void removeValue(String name) {
        values.remove(name);
        mySessionRepository.saveSession(this);
    }

    @Override
    public void invalidate() {

    }

    @Override
    @JsonIgnore
    public boolean isNew() {
        return false;
    }
}
