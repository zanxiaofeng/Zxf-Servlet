#Run
./gradlew appRun

#Test by curl
curl -vvvv http://localhost:8080/Zxf-Servlet/my-servlet

curl -vvvv http://localhost:8080/Zxf-Servlet/my-servlet?session=true

curl -vvvv "http://localhost:8080/Zxf-Servlet/my-servlet?session=true&My-Session-Id=098b50f0-b7aa-46bf-b881-5d08683336b7"
