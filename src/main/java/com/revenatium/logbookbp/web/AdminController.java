package com.revenatium.logbookbp.web;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/admin")
@RestController
public class AdminController {

    @GetMapping("/hello-world")
    public ResponseEntity<String> getHelloWorld () {
        return new ResponseEntity<>("Hello World!", HttpStatus.OK);
    }
}
