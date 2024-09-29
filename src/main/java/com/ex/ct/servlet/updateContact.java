package com.ex.ct.servlet;

import javax.servlet.RequestDispatcher;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ex.DAOs.contactDAO;
import com.ex.pojo.contact;

@WebServlet("/updateContact")
public class updateContact extends HttpServlet{

	private static final long serialVersionUID = 1L;
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) {
		
		contact ct = new contact();
		try {
			ct.setPID(Integer.parseInt(request.getParameter("PID")));
			ct.setName(request.getParameter("name"));
            ct.setNotes(request.getParameter("notes"));
            ct.setPhoneNumber(Long.parseLong(request.getParameter("phone_number")));
            ct.setEmailID(request.getParameter("email_id"));
            ct.setRelatedPerson(request.getParameter("related_person"));
            ct.setHomeAddress(request.getParameter("home_address"));
            ct.setWorkAddress(request.getParameter("work_address"));
            ct.setEmailIDLabel(request.getParameter("email_id_label"));
            ct.setDate(request.getParameter("date"));
            ct.setDateLabel(request.getParameter("datelabel"));
            ct.setPhoneLabel(request.getParameter("phone_number_label"));
            
            //where is pid?
		}
		catch(Exception e) {
			System.out.println("Error1 from updatecontact"+ e.getMessage());
		}
		contactDAO c = new contactDAO();
		System.out.println("ulla vanten");
		
		try {
			boolean result = c.updateContact(ct);
			System.out.println(result);
			if (result != false) {
				response.sendRedirect("success.jsp");
//				RequestDispatcher rs = request.getRequestDispatcher("success.jsp");
//				try {
//					rs.forward(request, response);
//				}
//				catch(Exception e) {
//					System.out.println("Error da epaaaa");
//				}
			}
		}
		catch(Exception e) {
			System.out.println("Error2 from updatecontact"+ e.getMessage());
		}
	}
}
