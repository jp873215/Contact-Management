package com.ex.ct.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ex.DAOs.contactDAO;
import com.ex.pojo.contact;

@WebServlet("/getContact")
public class getContact extends HttpServlet{

	private static final long serialVersionUID = 1L;
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		int PID = Integer.parseInt(request.getParameter("updatecontact"));
		System.out.println(PID);
		
		
		
		contactDAO c = new contactDAO();
		contact obj = c.getContact(PID);
		request.setAttribute("object", obj);
		if (obj != null) {
			RequestDispatcher rs = request.getRequestDispatcher("contact.jsp");
			System.out.println("Object is not null");
			try
			{
				rs.forward(request, response);
			}
			catch(Exception e) {
				System.out.println("Error with the dispatcher from updateContact.java");
			}
			
		}
				
		
	}
	
	
	

}
