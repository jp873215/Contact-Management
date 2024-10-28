package com.ex.ct.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ex.DAOs.contactDAO;
import com.ex.DAOs.userDAO;
import com.ex.extras.GetCookie;
import com.ex.pojo.session;
import com.ex.pojo.user;

@WebServlet("/deleteService")
public class deleteService extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    private userDAO uDAO;
    private contactDAO cDAO;
    private session sObj;


    @Override
    public void init() {
        uDAO = new userDAO();
        cDAO = new contactDAO();
        sObj = new session();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("request");
        

        switch (action) {
            case "email":
                handleDeleteEmail(request, response);
                break;
            case "contact":
                handleDeleteContact(request, response);
                break;
            case "category":
                handleDeleteCategory(request, response);
                break;
            case "session":
            	handleDeleteSession(request, response);
            	break;
            default:
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid delete action");
        }
    }

    private void handleDeleteSession(HttpServletRequest request, HttpServletResponse response) {
    	sObj.setUid(Integer.parseInt((GetCookie.myCookie(request, "UID"))));
    	
    	try {
    		boolean res = uDAO.clearAllTheSession(sObj, request, response);
    		if (res) {
    			response.sendRedirect("login.jsp");
    		}
    		else {
    			System.out.println("session clear aagala");
    		}
    	}
    	catch(Exception e) {
    		System.out.println("Failed to logout from all the devices!!");
    	}
    	
    }
    
    private void handleDeleteEmail(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String email = request.getParameter("request_value");
        System.out.println("Inside the delete Service");
        try {
            if (uDAO.deleteEmail(email)) {
                response.sendRedirect("listService?request=email");
            } else {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Failed to delete email");
            }
        } catch (Exception e) {
            System.err.println("Error deleting email: " + e.getMessage());
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Internal error occurred");
        }
    }

    private void handleDeleteContact(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            int contactId = Integer.parseInt(request.getParameter("PID"));
            if (cDAO.deletecontact(contactId)) {
                response.sendRedirect("success.jsp");
            } else {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Failed to delete contact");
            }
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid contact ID");
        } catch (Exception e) {
            System.err.println("Error deleting contact: " + e.getMessage());
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Internal error occurred");
        }
    }
    

    private void handleDeleteCategory(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int category = Integer.parseInt(request.getParameter("cat_id"));
        try {
            if (uDAO.deleteCategory(category)) {
                try {
                	request.setAttribute("response", "category");
                	RequestDispatcher rd = request.getRequestDispatcher("listService");
                	rd.forward(request, response);
                }
                catch(Exception e) {
                	System.out.println("Error in the Category Deleteion" + e.getMessage());
                }
            } else {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Failed to delete category");
            }
        } catch (Exception e) {
            System.err.println("Error deleting category: " + e.getMessage());
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Internal error occurred");
        }
    }
}
