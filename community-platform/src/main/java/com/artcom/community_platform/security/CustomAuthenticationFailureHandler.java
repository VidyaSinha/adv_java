package com.artcom.community_platform.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException exception) throws IOException, ServletException {
        
        String errorMessage = "Invalid username or password";
        
        if (exception.getMessage().equalsIgnoreCase("User account is locked")) {
            errorMessage = "Your account has been locked. Please contact support.";
        } else if (exception.getMessage().equalsIgnoreCase("User account is disabled")) {
            errorMessage = "Your account has been disabled. Please contact support.";
        } else if (exception.getMessage().equalsIgnoreCase("User account has expired")) {
            errorMessage = "Your account has expired. Please contact support.";
        } else if (exception.getMessage().equalsIgnoreCase("Invalid credentials")) {
            errorMessage = "Invalid username or password";
        } else if (exception.getMessage().equalsIgnoreCase("User not found")) {
            errorMessage = "No account found with this email address";
        }

        setDefaultFailureUrl("/login?error=" + errorMessage);
        super.onAuthenticationFailure(request, response, exception);
    }
} 