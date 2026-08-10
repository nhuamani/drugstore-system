package com.nhuamani.drugstore_system.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    @GetMapping("/dashboard")
    public String dashboard(Authentication authentication) {

        System.out.println("================================");
        System.out.println("USER: " + authentication.getName());
        System.out.println("AUTHORITIES: " + authentication.getAuthorities());
        System.out.println("================================");

        return "dashboard";
    }
}
