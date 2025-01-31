package com.rktechyt.ecommerce.configuration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.rktechyt.ecommerce.model.Role;
import com.rktechyt.ecommerce.model.User;
import com.rktechyt.ecommerce.repository.RoleRepository;
import com.rktechyt.ecommerce.repository.UserRepository;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class GoogleOAuth2SuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;
    
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
    
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {
        OAuth2AuthenticationToken token = (OAuth2AuthenticationToken) authentication;
        String email = token.getPrincipal().getAttribute("principal_email");
        if(userRepository.findUserByEmail(email).isPresent()){
            redirectStrategy.sendRedirect(request, response, "redirect:/home");
        } else {
            User user = new User();
            user.setFirstName(token.getPrincipal().getAttribute("given_name"));
            user.setLastName(token.getPrincipal().getAttribute("family_name"));
            user.setEmail(email);
            List<Role> roles = new ArrayList<>();
            roles.add(roleRepository.findById(2).get());
            user.setRoles(roles);
            userRepository.save(user);
            redirectStrategy.sendRedirect(request, response, "redirect:/home");
        }
    }
    
}
