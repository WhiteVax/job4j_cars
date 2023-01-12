package ru.job4j.cars.filter;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

@Component
public class AuthFilter implements Filter {
    private static final String USER = "user";
    private static final String LOGIN_PAGE = "/loginPage";

    private static final Set<String> PAGES = Set.of("/loginPage", "/login",
            "/registration", "/fail", "/createUser");

    private boolean checkUri(String uri) {
        return PAGES.stream().anyMatch(uri::endsWith);
    }

    @Override
    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        var req = (HttpServletRequest) request;
        var res = (HttpServletResponse) response;
        var uri = req.getRequestURI();
        if (checkUri(uri)) {
            chain.doFilter(req, res);
            return;
        }
        if (req.getSession().getAttribute(USER) == null) {
            res.sendRedirect(req.getContextPath() + LOGIN_PAGE);
            return;
        }
        chain.doFilter(req, res);
    }
}
