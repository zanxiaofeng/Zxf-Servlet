package zxf.practices.servlet.mysession;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

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
    private Map<String, ObjectWrapper> attributes;
    @JsonProperty
    private Map<String, Object> values;

    public MySession() {

    }

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
        ObjectWrapper object = attributes.get(name);
        if (object == null){
            return null;
        }

        return object.getConvertedObject();
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
        attributes.put(name, new ObjectWrapper(value));
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

    public static class ObjectWrapper {
        private String type;
        private Object object;

        public ObjectWrapper() {

        }

        public ObjectWrapper(Object object) {
            this.type = object.getClass().getTypeName();
            this.object = object;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public Object getObject() {
            return object;
        }

        public void setObject(Object object) {
            this.object = object;
        }

        @JsonIgnore
        public Class getKlass() {
            try {
                return Class.forName(type);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            return Object.class;
        }

        @JsonIgnore
        public Object getConvertedObject() {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.convertValue(object, getKlass());
        }
    }
}
