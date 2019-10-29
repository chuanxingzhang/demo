package com.biyao.demo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by jk
 * Create Date: 2019/10/29 10:15
 * Description: hi
 */
@RestController
public class HelloController {


    @RequestMapping(method = RequestMethod.GET, value = "/hello")
    public ResponseEntity hello(@RequestParam(required = false) String name) {
        String msg = "hello" + (name == null ? "" : name);
        return ResponseEntity.ok(msg);
    }
}
