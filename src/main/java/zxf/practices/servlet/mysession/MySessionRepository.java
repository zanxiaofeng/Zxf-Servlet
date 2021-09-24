package zxf.practices.servlet.mysession;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class MySessionRepository {
    private ObjectMapper objectMapper = new ObjectMapper();

    public MySession getSession(String sessionId) {
        System.out.println("Thread=" + Thread.currentThread().getName() + "::MySessionRepository.getSession, Id=" + sessionId);

        if (sessionId == null) {
            return null;
        }

        Path sessionFileName = getSessionFileName(sessionId);
        if (Files.exists(sessionFileName)) {
            try {
                return objectMapper.readValue(sessionFileName.toFile(), MySession.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    public void saveSession(MySession mySession) {
        System.out.println("Thread=" + Thread.currentThread().getName() + "::MySessionRepository.saveSession, Id=" + mySession.getId());

        try {
            Path sessionFileName = getSessionFileName(mySession.getId());
            objectMapper.writeValue(sessionFileName.toFile(), mySession);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Path getSessionFileName(String sessionId) {
        return Path.of("./store/" + sessionId + ".json");
    }
}