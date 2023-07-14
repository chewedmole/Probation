package com.chewedmole.probation.api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class SimpleController {

    @GetMapping("level2")
    public String helloWorld(){
        return "Hello, world!";
    }
}
