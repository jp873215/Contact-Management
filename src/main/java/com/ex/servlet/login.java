package com.ex.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/login")
public class login extends HttpServlet{
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException{
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        PrintWriter pw = response.getWriter();
        doConnect dc = new doConnect();
        int UID = dc.find(email, password, pw);
        if (UID != -1){
            HttpSession session = request.getSession(true);
            session.setAttribute("UID", UID);
            session.setAttribute("name", dc.getName(UID));
            session.setAttribute("email", email);
            response.sendRedirect("success.jsp");
        } 
        else{
            response.sendRedirect("sign.jsp");
        }
    }
}



class doConnect{

    private final String URL = "jdbc:mysql://localhost:3306/users";
    private final String name = "root";
    private final String pass = "Prakashrmkec@873215$";
    int UID = -1;
    public int find(String email, String password, PrintWriter pw){

        try {
            Class.forName("com.mysql.jdbc.Driver");
            try (Connection conn = DriverManager.getConnection(URL, name, pass)) {
                String query = "select user_data.UID from user_data JOIN ut_email_id on user_data.UID = ut_email_id.UID where ut_email_id.Email_ID = ? AND user_data.Password = ?;";
                try (PreparedStatement pst = conn.prepareStatement(query)) {
                    pst.setString(1, email);
                    pst.setString(2, password);

                    ResultSet rs = pst.executeQuery();

                    if (rs.next()){
                        UID = rs.getInt("UID");
                        pw.println(UID);
                        return UID;
                    }
                    else{
                        pw.println("Wrong Information");
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
        return UID;
    }
    public String getName(int UID) {
    	try {
    		Class.forName("com.mysql.jdbc.Driver");
    		try(Connection conn = DriverManager.getConnection(URL,name,pass)){
    			String query = "select Name from user_data where UID = ?;";
    			try(PreparedStatement pst = conn.prepareStatement(query)){
    				pst.setInt(1, UID);
    				ResultSet rs = pst.executeQuery();
    				if (rs.next()) {
    					return rs.getString("Name");
    				}
    				else {
    					System.out.println("Name not found");
    				}
    			}
    			catch(Exception e) {
    				System.out.println("Error1:" + e.getMessage());
    			}
    		}
    	}
    	catch(Exception e) {
			System.out.println("Error1:" + e.getMessage());
    	}
    	return null;
    	
    }
}