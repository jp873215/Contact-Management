package com.ex.ct.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ex.DAOs.contactDAO;
import com.ex.DAOs.userDAO;
import com.ex.extras.GetCookie;
import com.ex.pojo.contact;
import com.ex.pojo.session;
import com.ex.pojo.user;

/**
 * Servlet implementation class listService
 */
@WebServlet("/listService")
public class listService extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private userDAO uDAO = new userDAO();
	private contactDAO cDAO = new contactDAO();
	private contact cObj = new contact();

       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public listService() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("request");
		System.out.println(action);
		if("email".equals(action)) {
			listEmail(request, response);
		}	
		else if ("category".equals(action)) {
			listCategory(request, response);
		}
		else if ("categoryMembers".equals(action)) {
			listCategoryMembers(request, response);
		}
		else if("contactedit".equals(action)) {
			getContactforEdit(request, response);
		}
		else {
	        response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid action specified.");
	    }
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String result = (String) request.getParameter("request");
		switch(result) {
			case "email":
				listEmail(request, response);
				break;
			case "category":
				listCategory(request, response);
				break;
			case "categoryMembers":
				listCategoryMembers(request, response);
				break;
			case "contact":
				getContact(request, response);
				break;
			case "contactedit":
				getContactforEdit(request, response);
				break;
			default:
				response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid action specified.");
		}
	}
	
	
	//from the request we need to get PID
	public void getContact(HttpServletRequest request, HttpServletResponse response) {
		feedContactData(cObj, request);
		contact resObj = getContact(cObj);
		if (resObj.getName() != null) {
			request.setAttribute("contactObj", resObj);
			RequestDispatcher rd = request.getRequestDispatcher("contact_profile.jsp");
			try {
				rd.include(request, response);
			}
			catch(Exception e) {
				
			}
		}
	}
	
	
	public void getContactforEdit(HttpServletRequest request, HttpServletResponse response) {
		feedContactData(cObj, request);
		contact resObj = getContact(cObj);
		if (resObj.getName() != null) {
			System.out.println("Ready to get into the contact jsp");
			request.setAttribute("object", resObj);
			RequestDispatcher rd = request.getRequestDispatcher("contact.jsp");
			try {
				rd.include(request, response);
			}
			catch(Exception e) {
				
			}
		}
	}
	
	public void listEmail(HttpServletRequest request, HttpServletResponse response) {
		int UID = Integer.parseInt(GetCookie.myCookie(request, "UID"));
		System.out.println("From listService for email: " + UID);
		user user = getEmail(UID);
		if (user.getEmailID() != null) {
			request.setAttribute("EmailList", user);
			System.out.println("email list in user obj is not null");
			RequestDispatcher rs = request.getRequestDispatcher("email.jsp");
			try {
				rs.forward(request, response);
			}
			catch(Exception e) {
				System.out.println("Error getting a list of emails" + e.getMessage());
			}
		}
		
	}
	
	public void listCategory(HttpServletRequest request, HttpServletResponse response) {
	    int UID = Integer.parseInt(GetCookie.myCookie(request, "UID"));
	    System.out.println("From listService for category: " + UID);
	    user obj = getCategory(UID);
	    if (obj != null && obj.getCategory() != null && !obj.getCategory().isEmpty()) {
	        request.setAttribute("CatList", obj);
	        System.out.println("category list in user obj is not null");
	        System.out.println(obj.getCategory().get(0)); // Access the list correctly -- Done
	        RequestDispatcher rs = request.getRequestDispatcher("category.jsp");
	        try {
	            rs.forward(request, response);
	        } catch (Exception e) {	
	            System.out.println("Error getting a list of categories: " + e.getMessage());
	        }
	    } else {
	        request.setAttribute("CatList", null);
	        RequestDispatcher rs = request.getRequestDispatcher("category.jsp");
	        try {
	            rs.forward(request, response);
	        } catch (Exception e) {
	            System.out.println("Error getting a list of categories: " + e.getMessage());
	        }
	    }
	}

	
	public void listCategoryMembers(HttpServletRequest request, HttpServletResponse response) {
//		Integer CID = Integer.parseInt(request.getParameter("cat_id")) ;
//		String catname = (String) request.getParameter("cat_name");
//		if (CID == null && catname == null) {
//			CID = (int) request.getAttribute("cat_id");
//			catname = (request.getParameter("cat_name"));
//		}
		
		Integer CID = null;
		String catname = null;
		
	    // Check for 'cat_id' in parameters first, then in attributes
	    String catIdParam = request.getParameter("cat_id");
	    if (catIdParam != null) {
	        try {
	            CID = Integer.parseInt(catIdParam);
	        } catch (NumberFormatException e) {

	            System.out.println("Invalid 'cat_id' format: " + catIdParam);
	        }
	    }

	    if (CID == null) {
	        Object catIdAttr = request.getAttribute("cat_id");
	        if (catIdAttr != null && catIdAttr instanceof Integer) {
	            CID = (Integer) catIdAttr;
	        }
	    }

	    // Check for 'cat_name' in parameters first, then in attributes
	    catname = request.getParameter("cat_name");
	    if (catname == null) {
	        Object catNameAttr = request.getAttribute("cat_name");
	        if (catNameAttr != null && catNameAttr instanceof String) {
	            catname = (String) catNameAttr;
	        }
	    }
		
		System.out.println("From listService for categoryMembers: " + CID);
		List<contact> obj = cDAO.getCategoryMembers(CID);
		if (obj != null) {
			request.setAttribute("MembersList", obj);
			request.setAttribute("CategoryName", catname);
			System.out.println("category list in user obj is not null");
			RequestDispatcher rs = request.getRequestDispatcher("categoryMembers.jsp");
			try {
				rs.forward(request, response);
			}
			catch(Exception e) {
				System.out.println("Error getting a list of emails" + e.getMessage());
			}
		}
	}
	
	private void feedContactData(contact cObj, HttpServletRequest request) {
		cObj.setPID(Integer.parseInt(request.getParameter("PID")));	
	}
	
	
	private user getEmail(int UID){
		return uDAO.getEmails(UID);
	}
	
	private user getCategory(int UID){
		return uDAO.getCategory(UID);
	}
	
	private contact getContact(contact cObj) {
		return cDAO.getContact(cObj);
	}
	

}
