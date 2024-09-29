package com.ex.ct.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ex.DAOs.userDAO;
import com.ex.enums.Enums;
import com.ex.imp.hash;
import com.ex.pojo.user;


@WebServlet("/login")
public class login extends HttpServlet {
    private static final long serialVersionUID = 1L;
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		user o1 = new user();
		hash hashobj = new hash();
		userDAO dao1 = new userDAO();
		try {
			
			//get the salt and map with the hashed password
			o1.setPassword(request.getParameter("password"));
			o1.setEmailID(request.getParameter("email"));
			
			try {
				user saltandhash = dao1.getSaltandHash(o1);
				System.out.println(saltandhash.getSalt());
				System.out.println(saltandhash.getPassword());
				
				boolean res = hashobj.verifyPassword(saltandhash.getSalt(), saltandhash.getPassword(), o1.getPassword());
				System.out.println(res);
				if (res != false) {
					System.out.println("About to login");
					HttpSession session = request.getSession(true);
					session.setAttribute(Enums.name.getValue(), saltandhash.getName());
					session.setAttribute(Enums.UID.getValue(), saltandhash.getUID());
					session.setAttribute(Enums.email.getValue(), o1.getEmailID());
					response.sendRedirect("success.jsp");
				}
				else {
					System.out.println("Ula pogala");
					
				}
			}
			catch(Exception e) {
				System.out.println("Error with the password hashing and salting:" + e.getMessage());
				
			}
		}
		catch(Exception e) {
			System.out.println("Error with login:" + e.getMessage());	
		}
		
	}
}


