package com.bogdevich.cafe.controller.filter;

import javax.servlet.*;
import java.io.IOException;

public class CharsetFilter implements Filter {

    private static final String PARAM = "encoding";
    private String defaultEncoding = "UTF-8";

    public void init(FilterConfig config) throws ServletException {
        String encoding = config.getInitParameter(PARAM);
        if (encoding != null) {
            defaultEncoding = encoding;
        }
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        req.setCharacterEncoding(defaultEncoding);
        resp.setCharacterEncoding(defaultEncoding);
        chain.doFilter(req, resp);
    }

    public void destroy() {
    }

}
