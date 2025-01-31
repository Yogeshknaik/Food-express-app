package com.rktechyt.ecommerce.controller;

import com.rktechyt.ecommerce.model.User;
import com.rktechyt.ecommerce.repository.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
@Controller
public class ForgotPass {


    private final UserRepository userRepository;

    public ForgotPass(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/forgotpassword")
    public String forgotPasswordGet(){
        return "forgotpassword";
    }

    @PostMapping("/forgotpassword")
    public String forgotPasswordPost(@RequestParam String email, Model model){
        // Implement your forgot password logic here
        // For example, send a reset link to the user's email
        User user = userRepository.findByEmail(email);
        if (user != null) {
            // Send reset link to user's email
            // For simplicity, we'll just print a message
            model.addAttribute("message", "Reset link sent to your email");
        } else {
            model.addAttribute("error", "Email not found");
        }
        return "forgotpassword";
    }
}
