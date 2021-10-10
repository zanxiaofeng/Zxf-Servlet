package zxf.practices.servlet.mysession;

public class MyObject {
    private String name;
    private String startTime;

    public MyObject() {

    }

    public MyObject(String name, String startTime) {
        this.name = name;
        this.startTime = startTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String toString() {
        return "(" + name + ", " + startTime + ")";
    }
}
