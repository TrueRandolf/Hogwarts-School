package ru.hogwarts.school.model;

import org.springframework.stereotype.Component;

@Component
public class Inform {
    private String port;

    public Inform() {
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    @Override
    public String toString() {
        return "Info{" +
                "port='" + port + '\'' +
                '}';
    }
}
