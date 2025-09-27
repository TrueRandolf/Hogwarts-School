package ru.hogwarts.school.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.hogwarts.school.service.InfoService;

@RestController
public class InfoController {

    private final InfoService service;

    public InfoController(InfoService service) {
        this.service = service;
    }

    @GetMapping("/port")
    public String getPort() {
        return service.getInfoPort();
    }

//    Буквально, допустимо было выполнить так:
//    @Value("${server.port}")
//    private String serverPort;
//
//    @GetMapping("/port")
//    public String getPort() {
//        return serverPort;
//    }
//    Но выполнил по классике, с сервисами и моделями, на вырост.
//    Вдруг потребуется вывести еще информацию по приложению.
//    Либо отдать её одним JSONом.
//    Доп челлендж с @PostConstruct

}








