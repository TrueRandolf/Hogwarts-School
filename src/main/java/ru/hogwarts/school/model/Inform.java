package ru.hogwarts.school.model;

import org.springframework.stereotype.Component;

@Component
public class Inform {
    private String port;
    private RunTimeBench bench;

    public Inform() {
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public RunTimeBench getBench() {
        return bench;
    }

    public void setBench(RunTimeBench bench) {
        this.bench = bench;
    }

    @Override
    public String toString() {
        return "Info{" +
                "port='" + port + '\'' +
                '}';
    }
}
