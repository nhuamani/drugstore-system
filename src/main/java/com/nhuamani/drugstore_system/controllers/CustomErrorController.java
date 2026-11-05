package com.nhuamani.drugstore_system.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.boot.webmvc.error.ErrorController;

@Controller
public class CustomErrorController implements ErrorController {

    @GetMapping("/403")
    public String accessDenied() {

        return "403";
    }

    @RequestMapping("/error")
    public String handleError() {
        return "redirect:/dashboard";
    }
}
