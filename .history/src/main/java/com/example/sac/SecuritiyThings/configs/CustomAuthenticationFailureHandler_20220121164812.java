package com.example.sac.SecuritiyThings.configs;

@Component
public class CustomAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException exception) throws IOException, ServletException {

        String msg = "Invaild Username or Password";

        if (exception instanceof BadCredentialsException) {

        } else if (exception instanceof InsufficientAuthenticationException) {
            msg = "Invalid Secret Key";
        }

        setDefaultFailureUrl("/login?error=true&exception=" + msg);

        super.onAuthenticationFailure(request, response, exception);
    }
}
