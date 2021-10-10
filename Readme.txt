#Run
./gradlew appRun

#Test Jndi by curl
curl -vvvv http://localhost:8080/Zxf-Servlet/my-jndi-servlet

#Test Session by curl
curl -vvvv http://localhost:8080/Zxf-Servlet/my-session-servlet

curl -vvvv "http://localhost:8080/Zxf-Servlet/my-session-servlet?session=true&name=Davis"

curl -vvvv "http://localhost:8080/Zxf-Servlet/my-session-servlet?session=true&My-Session-Id=861e1c25-c822-4f02-a9b6-e6e0156bbb18"

#Issues
如果在session中存储自定义类型，反序列化后类型会变为Map<String, Object>, 这个问题如何解决？

#Session Replication Solutions
Filter based session replication
SpringSession based session replication(spring-session-hazelcast)
SessionManager based session replication(Tomcat or Jetty)