#Run
./gradlew appRun

#Test by curl
curl -vvvv http://localhost:8080/Zxf-Servlet/my-servlet

curl -vvvv "http://localhost:8080/Zxf-Servlet/my-servlet?session=true&name=Davis"

curl -vvvv "http://localhost:8080/Zxf-Servlet/my-servlet?session=true&My-Session-Id=00091282-2620-4416-bc62-d8f8b24494e5"

#Issues
如果在session中存储自定义类型，反序列化后类型会变为Map<String, Object>, 这个问题如何解决？

#Session Replication Solutions
Filter based session replication
SpringSession based session replication(spring-session-hazelcast)
SessionManager based session replication(Tomcat or Jetty)