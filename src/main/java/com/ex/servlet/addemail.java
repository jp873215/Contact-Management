//package com.ex.servlet;
//
//import java.io.PrintWriter;
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.PreparedStatement;
//
//import javax.servlet.annotation.WebServlet;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//
//
//@WebServlet("/addemail")
//public class addemail extends HttpServlet{
//    public void doPost(HttpServletRequest request, HttpServletResponse response){
//        String email = request.getParameter("email");
//        String emaillabel = request.getParameter("emaillabel");
//
//        try (PrintWriter pw = response.getWriter();){
//            HttpSession session = request.getSession(false);
//            addConnect obj = new addConnect();
//            boolean res = obj.find(email, emaillabel, session, pw);
//            if (session != null && res){
//                //request.setAttribute("email", email);
//                response.sendRedirect("listemail");
//            }
//            else{
//                pw.println("Not Added!!");
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//}
//
//
//class addConnect{
//
//    private static final String URL = "jdbc:mysql://localhost:3306/users";
//    private static final String name = "root";
//    private static final String pass = "Prakashrmkec@873215$";
//    public boolean find(String email, String emaillabel, HttpSession session, PrintWriter pw){
//        try {	
//            Class.forName("com.mysql.cj.jdbc.Driver");
//            try (Connection conn = DriverManager.getConnection(URL, name, pass)) {
//                String query = "INSERT INTO ut_email_id(UID, Email_ID, Label) values(?, ?, ?);";
//                try (PreparedStatement pst = conn.prepareStatement(query)) {
//                    Integer UID = (Integer) session.getAttribute("UID");
//                    if (UID != null)
//                    {
//                        pst.setInt(1, UID);
//                        pst.setString(2, email); 
//                        pst.setString(3, emaillabel);
//                        int res = pst.executeUpdate();
//                        if (res != 0){
//                            return true;
//                        }
//                    }   
//                    else{
//                        pw.println("Session not available");
//                    }
//
//                } catch (Exception e) {
//                    pw.println("PreparedStatement Error");
//                }
//            } catch (Exception e) {
//                pw.println("DB Connection got wrong inputs");
//            }
//        } catch (Exception e) {
//            pw.println("DB Error");
//        }
//        return false;
//    }
//}