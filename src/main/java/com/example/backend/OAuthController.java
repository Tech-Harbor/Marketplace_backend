package com.example.backend;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class OAuthController {
    @GetMapping("/oauth2/authorize/google")
    public String authorizeGoogle() {
        return "redirect:/oauth2/authorization/google";
    }
}
