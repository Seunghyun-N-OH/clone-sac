package com.example.sac.SecuritiyThings.configs;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException exception) throws IOException, ServletException {

        String msg = "Account not exist. Please check ID or PW.";

        if (exception instanceof BadCredentialsException) {

        } else if (exception instanceof InsufficientAuthenticationException) {
            msg = "재로그인 해주세요";
        }

        setDefaultFailureUrl("/member/signin?error=true&exception=" + msg);

        super.onAuthenticationFailure(request, response, exception);
    }
}
