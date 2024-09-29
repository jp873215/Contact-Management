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
import com.mysql.cj.Session;


@WebServlet("/signup")
public class signup extends HttpServlet {
    private static final long serialVersionUID = 1L;

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		PrintWriter pw = response.getWriter();
		user newUser = new user();
		hash hashobj = new hash();
		try {
			
			//wherever I get the password just call the hash class and hash the password
			String pass = request.getParameter("password");
			String[] b = hashobj.hashPassword(pass);
			String hashpassword = b[1];
			String salt = b[0];
			System.out.println(hashpassword);
			newUser.setPassword(hashpassword);
			newUser.setSalt(salt);
			
			
			newUser.setName(request.getParameter("name"));
		    newUser.setGender(request.getParameter("gender"));
		    newUser.setHomeAddress(request.getParameter("homeAddress"));
		    newUser.setWorkAddress(request.getParameter("workAddress"));
		    newUser.setPhoneNumber(Long.valueOf(request.getParameter("phone")));
		    newUser.setEmailID(request.getParameter("email"));
		    newUser.setEmailIDLabel(request.getParameter("elabel"));
		    newUser.setPhoneLabel(request.getParameter("plabel"));
		}
		catch(Exception e) {
			pw.println("Error1:" + e.getMessage());
		}
		try {
			HttpSession session = request.getSession(true);
	    	userDAO dao = new userDAO();
	    	user obj = dao.addUser(newUser);
	    	if (obj != null) {
	    		session.setAttribute(Enums.UID.getValue(), obj.getUID());
	    		session.setAttribute(Enums.name.getValue(), obj.getName());
	    		session.setAttribute(Enums.email.getValue(), obj.getEmailID());
	    		response.sendRedirect("success.jsp");
	    	}
	    	else {
	    		pw.println("Some Error with the Contact Details");
	    	}
	    	
	    }
		catch(Exception e) {
			
		}
		
	}
}


