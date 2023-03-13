package com.api.websocketapi.config.component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author sion
 */
@Component
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {

    private final RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException {
        String errorMessage = getErrorMessage(exception);
        String url = "/login?error=true&message=" + errorMessage;
        redirectStrategy.sendRedirect(request, response, url);
    }

    private String getErrorMessage(AuthenticationException exception) {
        String errorMessage = "Unknown error occurred!";
        if (exception instanceof LockedException) {
            errorMessage = "Your account has been locked due to too many failed login attempts. Please contact support.";
        } else if (exception instanceof BadCredentialsException) {
            errorMessage = "Invalid username or password. Please try again.";
        } else if (exception instanceof DisabledException) {
            errorMessage = "Your account has been disabled. Please contact support.";
        } else if (exception instanceof AccountExpiredException) {
            errorMessage = "Your account has expired. Please contact support.";
        } else if (exception instanceof CredentialsExpiredException) {
            errorMessage = "Your credentials have expired. Please update your password.";
        }
        return errorMessage;
    }
}
