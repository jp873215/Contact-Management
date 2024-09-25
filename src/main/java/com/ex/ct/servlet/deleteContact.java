package com.ex.ct.servlet;


import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ex.DAOs.contactDAO;


@WebServlet("/deleteContact")
public class deleteContact extends HttpServlet{

	private static final long serialVersionUID = 1L;

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException{
        PrintWriter pw = response.getWriter();
        int PID = -1;
        try{
            PID = Integer.parseInt(request.getParameter("deletecontact")) ;
            System.out.println(PID);
        }
        catch(Exception e){
            pw.println("Some Error with the Input");
        }
        contactDAO ctDAO = new contactDAO();
        try {
        	if (PID != -1) {
	        	boolean res = ctDAO.deletecontact(PID);
	            if (res != false){
	                response.sendRedirect("success.jsp");
	            }
	            else{
	                pw.println("Contact Details not deleted");
	                
	                
	            }
        	}
        }
        catch(Exception e) {
        	System.out.println("Error add contact:" + e.getMessage());
        }


    }

    
}



