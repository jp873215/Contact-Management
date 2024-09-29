package com.ex.ct.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ex.DAOs.contactDAO;
import com.ex.enums.Enums;
import com.ex.pojo.contact;


@WebServlet("/addcontact")
public class addContact extends HttpServlet{
    private static final long serialVersionUID = 1L;

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException{
        PrintWriter pw = response.getWriter();
        //SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        contact ct = new contact();
        try{
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
        }
        catch(Exception e){
            pw.println("Some Error with the Input");
        }
        HttpSession session = request.getSession();
        int UID = (int) session.getAttribute(Enums.email.getValue());
        contactDAO ctDAO = new contactDAO();
        try {
        	contact res = ctDAO.addContact(ct, UID);
            if (res != null){
//            	request.setAttribute("PID", res.getPID());
//            	RequestDispatcher red = request.getRequestDispatcher("success.jsp");
//            	rd.forward(request, response);
            	response.sendRedirect("success.jsp");
            }
            else{
                pw.println("Some Error with the Contact Details");
            }
        }
        catch(Exception e) {
        	System.out.println("Error add contact:" + e.getMessage());
        }


    }

    
}


