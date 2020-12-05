package com.lancabbage.lancodeapi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author: lanyanhua
 * @date: 2020/12/4 10:34 下午
 * @Description:
 */
@Controller
public class RouteController {

    @GetMapping("/docs")
    public String doc(){
        return "/docs";
    }
    @GetMapping("/template")
    public String template(){
        return "/template";
    }
}
