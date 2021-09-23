#Run
./gradlew appRun

#Test by curl
curl -vvvv http://localhost:8080/Zxf-Servlet/my-servlet

curl -vvvv http://localhost:8080/Zxf-Servlet/my-servlet?session=true

curl -vvvv "http://localhost:8080/Zxf-Servlet/my-servlet?session=true&My-Session-Id=60f7c470-6e25-4386-b195-b9919feafc8b"
