package com.ex.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.ex.SessionManagement.SessionManager;
import com.ex.DAOs.userDAO;
import com.ex.extras.GetCookie;
import com.ex.extras.deleteCookie;
import com.ex.Hikari.*;
@WebFilter("/*") 
public class check extends HttpFilter implements Filter {
    private static final long serialVersionUID = 1L;
    private SessionManager sessionManager;
    private userDAO uDAO = new userDAO();

    public check() {
        super();
    }

    @Override
    public void init(FilterConfig fConfig) throws ServletException {
        // Initialization logic if needed
        this.sessionManager = new SessionManager();
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        String uri = req.getRequestURI();
        String sessionIdCookie = GetCookie.myCookie(req, "SessionID");
        Integer UID = GetCookie.myCookie(req, "UID") != null ? Integer.parseInt(GetCookie.myCookie(req, "UID")) : null;
        System.out.println("SID"+ sessionIdCookie);
        System.out.println("UID"+ UID);
        if (sessionIdCookie != null && UID != null) {
            if (validate(sessionIdCookie, UID, req, res)) {
                sessionManager.updateInteraction(sessionIdCookie);
            } else {
            	System.out.println("Auth Failed!!");
                deleteCookie.delete(req, res);
                res.sendRedirect("login.jsp");
                return;
            }
        }
        if (uri.endsWith("addService")) {
            chain.doFilter(request, response); 
            return;
        }
        if (uri.endsWith("login.jsp") || uri.endsWith("sign.jsp")) {
            if (sessionIdCookie != null && UID != null) {
                res.sendRedirect("success.jsp");
                return;
            } else {
                chain.doFilter(request, response); 
                return;
            }
        } 

        if (sessionIdCookie != null && UID != null) {
            chain.doFilter(request, response); 
        } 
        else {
            res.sendRedirect("login.jsp");
        }

        try {
            this.sessionManager.sendDataToDB(req, res);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void destroy() {
        // Cleanup logic if needed
        sessionManager.shutdown();
    }
    
    private boolean validate(String sessionIdCookie, Integer UID, HttpServletRequest req, HttpServletResponse res) {
        return uDAO.validateSession(sessionIdCookie, UID, req, res);
    }
}
