package ru.hogwarts.school.service;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Inform;

@Service
public class InfoService {

    @Value("${server.port:8080}")
    private String serverPort;

    private final Inform inform;

    public InfoService(Inform inform) {
        this.inform = inform;
    }

    private static final Logger logger = LoggerFactory.getLogger(InfoService.class);

    @PostConstruct
    public void setInfo() {
        this.inform.setPort(serverPort);
    }

    public String getInfoPort() {
        logger.info("was invoked method for app running port: {}", serverPort);
        return this.serverPort;
    }

}
