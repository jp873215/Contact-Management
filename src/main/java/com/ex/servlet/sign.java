package com.ex.servlet;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/sign")
public class sign extends HttpServlet {
    public void doPost(HttpServletRequest request, HttpServletResponse response) {
        
        PrintWriter pw = null;
        try {
            pw = response.getWriter();
            String name = request.getParameter("name");
            String email = request.getParameter("email");
            String emailLabel = request.getParameter("elabel");
            String password = request.getParameter("password");
            Long phone = Long.parseLong(request.getParameter("phone")); 
            String phoneLabel = request.getParameter("plabel");
            String gender = request.getParameter("gender");
            String homeAddress = request.getParameter("Home Address");
            String workAddress = request.getParameter("Work Address");

            boolean success = DoConnect.add(name, email, emailLabel, password, phone, phoneLabel, gender, homeAddress, workAddress);
            if (success) {
                response.sendRedirect("login.jsp");
            } else {
                pw.println("Failed to add user.");
            }
        } catch (Exception e) {
            if (pw != null) {
                pw.println("Error: " + e.getMessage());
            }
        }
    }
}

class DoConnect {
    private static final String URL = "jdbc:mysql://localhost:3306/users";
    private static final String ROOT = "root";
    private static final String PASS = "Prakashrmkec@873215$";

    public static boolean add(String name, String email, String emailLabel, String password, Long phone, String phoneLabel, String gender, String homeAddress, String workAddress) {
        int uid = 0;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection conn = DriverManager.getConnection(URL, ROOT, PASS)) {
                String queryUsers = "INSERT INTO user_data(Name, Gender, Password, Home_Address, Work_Address) values (?, ?, ?, ?, ?)";
                String queryEmail = "INSERT INTO ut_email_id(UID, Email_ID, Label, Starred) values (?, ?, ?, 1)";
                String queryPhone = "INSERT INTO ut_phone(UID, Phone_Number, Label) values (?, ?, ?)";

                try (PreparedStatement pst = conn.prepareStatement(queryUsers, PreparedStatement.RETURN_GENERATED_KEYS)) {
                    pst.setString(1, name);
                    pst.setString(2, gender);
                    pst.setString(3, password);
                    pst.setString(4, homeAddress);
                    pst.setString(5, workAddress);

                    int rs = pst.executeUpdate();

                    if (rs != 0) {
                        try (ResultSet generatedKeys = pst.getGeneratedKeys()) {
                            if (generatedKeys.next()) {
                                uid = generatedKeys.getInt(1);
                            }
                        }

                        try (PreparedStatement pst1 = conn.prepareStatement(queryEmail)) {
                            pst1.setInt(1, uid);
                            pst1.setString(2, email);
                            pst1.setString(3, emailLabel);
                            int rs1 = pst1.executeUpdate();

                            if (rs1 != 0) {
                                try (PreparedStatement pst2 = conn.prepareStatement(queryPhone)) {
                                    pst2.setInt(1, uid);
                                    pst2.setLong(2, phone);
                                    pst2.setString(3, phoneLabel);
                                    int rs2 = pst2.executeUpdate();

                                    return rs2 != 0;
                                }
                            }
                        }
                    } else {
                        System.out.println("Wrong Information");
                    }
                }
            } catch (Exception e) {
                System.out.println("DB Connection Error: " + e.getMessage());
            }
        } catch (Exception e) {
            System.out.println("DB Driver Error: " + e.getMessage());
        }
        return false;
    }
}
