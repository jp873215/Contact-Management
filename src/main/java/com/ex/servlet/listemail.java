package com.ex.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@WebServlet("/listemail")
public class listemail extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        fetchEmails(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        fetchEmails(request, response);
    } 
    private void fetchEmails(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        List<String> li = new ArrayList<>();
        try {
            dbConnect obj = new dbConnect();
            li = obj.find(response, session);
            // if (request.getAttribute("email") != null){
            //     String email = (String) request.getAttribute("email");
            //     li.add(email);
            // }
            request.setAttribute("email", li);
            RequestDispatcher rd = request.getRequestDispatcher("email.jsp");
            rd.include(request, response);
        } catch (Exception e) {
            e.printStackTrace();
        }        
    }
}


class dbConnect{

    private static final String URL = "jdbc:mysql://localhost:3306/users";
    private static final String name = "root";
    private static final String pass = "Prakashrmkec@873215$";
    public List<String> find(HttpServletResponse response, HttpSession session) throws IOException{
        PrintWriter pw = response.getWriter();
        List<String> li = new ArrayList<>();
        try {
        	Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection conn = DriverManager.getConnection(URL, name, pass)) {
                String query = "select Email_ID from ut_email_id where UID = ?;";
                try (PreparedStatement pst = conn.prepareStatement(query)) {
                    Integer UID = (Integer) session.getAttribute("UID");
                    if (UID != null)
                    {
                        pst.setInt(1, UID);
                        try (ResultSet rs = pst.executeQuery();) {
                            while(rs.next()){
                                li.add(rs.getString("Email_ID"));
                            }
                        } 
                    }   
                    else{
                        pw.println("Session not available");
                    }

                } catch (Exception e) {
                    pw.println("PreparedStatement Error");
                }
            } catch (Exception e) {
                pw.println("DB Connection got wrong inputs");
            }
        } catch (Exception e) {
            pw.println("DB Error");
        }
        return li;
    }
}