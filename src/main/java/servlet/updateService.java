package servlet;

import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import DAOs.contactDAO;
import POJO.contact;

/**
 * Servlet implementation class updateService
 */
@WebServlet("/updateService")
public class updateService extends HttpServlet {
	private static final long serialVersionUID = 1L;
	contact ct = new contact();
	contactDAO cDAO = new contactDAO();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public updateService() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String result = request.getParameter("request");
		if ("contact".equalsIgnoreCase(result)) {
			handleContact(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String result = request.getParameter("request");
		
		switch(result) {
			case "contact":
				handleContact(request, response);
				break;
		}
	}
	
	private void handleContact(HttpServletRequest request, HttpServletResponse response) {
		
		feedContactData(ct, request);
		try {
			boolean res = getContactData(ct);
			if (res) {
				response.sendRedirect("success.jsp");
			}
		}
		catch(Exception e) {
			
		}
	}
	
	
	private boolean getContactData(contact cObj) throws ClassNotFoundException, SQLException {
		return cDAO.updateContact(cObj);
				
	}
	
	private void feedContactData(contact ct, HttpServletRequest request) {
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
	}
	
	

}
