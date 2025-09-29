package ru.hogwarts.school.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.hogwarts.school.model.RunTimeBench;
import ru.hogwarts.school.service.InfoService;

import java.util.List;

@RequestMapping("/info")
@RestController
public class InfoController {

    private final InfoService service;

    public InfoController(InfoService service) {
        this.service = service;
    }

    @GetMapping("port")
    public String getPort() {
        return service.getInfoPort();
    }

    @GetMapping("bench")
    public List<RunTimeBench> getBillion(){
        return service.getBench();
    }


}








