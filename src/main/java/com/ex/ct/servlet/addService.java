package com.ex.ct.servlet;

import java.io.IOException;
import java.time.LocalDateTime;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ex.DAOs.contactDAO;
import com.ex.DAOs.userDAO;
import com.ex.extras.GetCookie;
import com.ex.extras.randomnumber;
import com.ex.pojo.contact;
import com.ex.pojo.session;
import com.ex.pojo.user;


@WebServlet("/addService")
public class addService extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private userDAO uDAO = new userDAO();
	private contactDAO cDAO = new contactDAO();
       
    public addService() {
        super();
    }

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String result = request.getParameter("request");
		switch (result) {
			case "contact":
				handleAddContact(request, response);
				break;
//			case "signup":
//				handleSignUp(request, response);
//				break;
			case "login":
				System.out.print("inside login \n");
				handleLogin(request, response);
				break;
			case "email":
				handleEmail(request, response);
				break;
			case "category":
				handleCategory(request, response);
				break;
			case "members":
				handleMember(request, response);
				break;
			default:
				response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid action specified.");
		}
	}

	private void handleAddContact(HttpServletRequest request, HttpServletResponse response) throws IOException {
		contact ct = new contact();
		try {
			populateContactFromRequest(ct, request);
			int UID = Integer.parseInt(GetCookie.myCookie(request, "UID"));

			if (addContact(ct, UID)) {
				response.sendRedirect("success.jsp");
			} else {
				response.getWriter().println("Error adding contact.");
			}
		} catch (Exception e) {
			response.getWriter().println("Error with input data: " + e.getMessage());
		}
	}
	
	private void handleMember(HttpServletRequest request, HttpServletResponse response) {
		try {
			contact ct = new contact();
			populateCategoryMemberFromRequest(ct, request);
			System.out.println("From contact to add in category" + ct.getName());
			contact resObj = cDAO.insertMemberIntoCategory(ct);
			if (resObj.getCategory().get(0) != null) {
				// get resObj as object and pass CID and catname as parameter
				request.setAttribute("request", "categoryMembers");
				request.setAttribute("cat_id", resObj.getCategoryID().get(0));
				request.setAttribute("cat_name", resObj.getCategory().get(0));
				
				RequestDispatcher rd = request.getRequestDispatcher("listService");
				rd.include(request, response);
				
			}
		} 
		catch(Exception e) {
			System.out.println("Error with connection: " + e.getMessage());
		}
	}

	
	private void handleEmail(HttpServletRequest request, HttpServletResponse response) throws IOException {
		user emailadd = new user();
		int UID = Integer.parseInt(GetCookie.myCookie(request, "UID"));
		System.out.println("Printing from handleEmail(addService) UID is "+ UID);
		emailadd.setUID(UID);
		try {
			populateUserForAddingEmail(emailadd, request);
			System.out.println("Printing from handleEmail(addService) Email is "+ emailadd.getEmailID());
			System.out.println("Printing from handleEmail(addService) Email Label is "+ emailadd.getEmailIDLabel());
			if (addEmail(emailadd)) {
				RequestDispatcher rd = request.getRequestDispatcher("listService?request=email");
				rd.forward(request, response);
				
				//just change it to response and check else delete this comment
			}
			else {
				response.getWriter().println("Email failed");
			}
		}
		catch(Throwable e) {
			response.getWriter().println("Not Added!!" + e.getMessage());
		}
	}
//	private void handleSignUp(HttpServletRequest request, HttpServletResponse response) throws IOException {
//		user newUser = new user();
//		populateUserFromRequest(newUser, request);
//		user userObj = signUp(newUser);
//		try {
//			if (userObj != null) {
//				System.out.println("New Account UID:" + userObj.getUID());
//				System.out.println("User Object is not null from Signup");
//				session s = new session();
//				s.setUid(userObj.getUID());// Setting up the UID in the session
//				handleSession(s);
//				System.out.println("HandleSignup" + s.getLastActiveTime());
//				Cookie userCookie1 = new Cookie("SessionID", s.getSessionId());
//				Cookie userCookie2 = new Cookie("UID", String.valueOf(s.getUid()) );
//				userCookie1.setMaxAge(s.getTimeoutPeriod());
//				userCookie2.setMaxAge(s.getTimeoutPeriod());
//				System.out.println(s.getSessionId());
////				response.addCookie(userCookie1);
////				response.addCookie(userCookie2);
////				request.setAttribute("sessionObj", s);
////				RequestDispatcher dispatcher = request.getRequestDispatcher("success.jsp");
////				dispatcher.forward(request, response);
//
//				if (addSession(s) == true){
//					response.addCookie(userCookie1);
//					response.addCookie(userCookie2);
//					request.setAttribute("sessionObj", s);
//					RequestDispatcher dispatcher = request.getRequestDispatcher("success.jsp");
//					dispatcher.include(request, response);
//				}
//				else {
//					response.getWriter().println("Error with the session creation");
//				}
//			} else {
//				response.getWriter().println("Error signing up the user.");
//			}
//		} catch (Exception e) {
//			response.getWriter().println("Error with input data for signup: " + e.getMessage());
//		}
//	}
	
	private void handleCategory(HttpServletRequest request, HttpServletResponse response) {
		user catUserObj = new user();
		populateforCategory(catUserObj, request);
		boolean res = addCategory(catUserObj);
		if (res != false) {
			request.setAttribute("request", "category");
			RequestDispatcher dispatcher = request.getRequestDispatcher("listService");
			try {
				dispatcher.include(request, response);
			}
			catch(Exception e) {
				System.out.println("Unable to add Category" + e.getMessage());
			}
		}
		else {
			System.out.println("Unable to add Category" );
		}
		
	}

	private void handleLogin(HttpServletRequest request, HttpServletResponse response) throws IOException {
		user loginUser = new user();
		checkLogin(loginUser, request);// loading the data 
		user res = logIn(loginUser); // getting the user object after the login check with db
		try {
			if (res.getUID() != 0) {
				System.out.println("About to check");
				System.out.println(res.getUID());
				System.out.println("login kula vanten");
				String SID = GetCookie.myCookie(request, "SessionID");
				if (SID == null) {
					//cookie not available
					session s = new session();
					s.setUid(res.getUID());
					handleSession(s);
					Cookie userCookie1 = new Cookie("SessionID", s.getSessionId());
					Cookie userCookie2 = new Cookie("UID", String.valueOf(s.getUid()) );
					
					userCookie1.setMaxAge(s.getTimeoutPeriod());
					userCookie2.setMaxAge(s.getTimeoutPeriod());
					
					if (addSession(s)){
						response.addCookie(userCookie1);
						response.addCookie(userCookie2);
						System.out.println("value:" + s.getUid());
						//request.setAttribute("sessionObj", s);					
						response.sendRedirect("success.jsp");
					}
					else {
						response.getWriter().println("Error with the session creation");
					}
				}
//				else {
//					session s = new session();
//					s.setUid(res.getUID());
//					s.setSessionId(GetCookie.myCookie(request));
//					System.out.println("value:" + s.getUid());
//					request.setAttribute("sessionData", s);
//					RequestDispatcher dispatcher = request.getRequestDispatcher("success.jsp");
//					dispatcher.forward(request, response);
//				}
			}
		} 
		catch (Exception e) {
			response.getWriter().println("Error during login: " + e.getMessage());
		}
	}
	
	private void handleSession(session s) {
		s.setSessionId(randomnumber.generateUUIDv4());
		System.out.println("HandleSession:" + LocalDateTime.now());
		System.out.println("UID from handleSession" + s.getUid());
		s.setLastActiveTime(LocalDateTime.now());
		s.setCreationTime(LocalDateTime.now());
		s.setTimeoutPeriod(60*30);
	}
	
	private void populateCategoryMemberFromRequest(contact ct, HttpServletRequest request) {
		ct.setCategory((String) request.getParameter("cat_name"));
		ct.setName((String) request.getParameter("name"));
		ct.setEmailID((String) request.getParameter("email"));
		ct.setPhoneNumber(Long.parseLong(request.getParameter("phone")));
	}
	private void checkLogin(user us, HttpServletRequest request) {
		us.setEmailID(request.getParameter("email"));
		us.setPassword(request.getParameter("password"));
	}
	
	private void populateforCategory(user us, HttpServletRequest request) {
		us.setCategory((String) request.getParameter("category_name"));
		us.setUID(Integer.parseInt(GetCookie.myCookie(request, "UID")));
	}
	private void populateContactFromRequest(contact ct, HttpServletRequest request) {
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

	private void populateUserFromRequest(user newUser, HttpServletRequest request) {
		newUser.setName(request.getParameter("name"));
		newUser.setGender(request.getParameter("gender"));
		newUser.setHomeAddress(request.getParameter("homeAddress"));
		newUser.setWorkAddress(request.getParameter("workAddress"));
		newUser.setPhoneNumber(Long.parseLong(request.getParameter("phone")));
		newUser.setEmailID(request.getParameter("email"));
		newUser.setEmailIDLabel(request.getParameter("elabel"));
		newUser.setPhoneLabel(request.getParameter("plabel"));
		newUser.setPassword(request.getParameter("password"));
	}
	
	private void populateUserForAddingEmail(user newUser, HttpServletRequest request) {
		newUser.setEmailID(request.getParameter("email"));
		newUser.setEmailIDLabel(request.getParameter("emaillabel"));
	}
	
	private boolean addSession(session ses) {
		
		try {
			if(uDAO.sessiondata(ses) ) {
				return true;
			}
		}
		catch(Exception e) {
			System.err.println("Error adding session: " + e.getMessage());
			return false;
		}
		return false;
	}
	
	private boolean addContact(contact ct, int UID) {
		try {
			return cDAO.addContact(ct, UID) != null;
		} catch (Exception e) {
			System.err.println("Error adding contact: " + e.getMessage());
			return false;
		}
	}

// Need to reconstruct the DAO class	
	private boolean addEmail(user obj) throws Throwable {
		try {
			return uDAO.addEmail(obj);
		}
		catch(Exception e) {
			System.err.println("Error adding email: " + e.getMessage());
			return false;
		}
	}
	

//	private user signUp(user obj) {
//		try {
//			uDAO.addUser(obj);
//			return null;
//		} catch (Exception e) {
//			System.err.println("Error signing up user: " + e.getMessage());
//			return null;
//		}
//	}

// sikarama un comment pannu -- not yet uncommented completely!!
	
	private user logIn(user obj) {
		try {
			System.out.println("Inside the logIN DAO Layer");
			return uDAO.loginUsers(obj);
		} catch (Exception e) {
			System.err.println("Error during login: " + e.getMessage());
			return null;
		}
	}
	
	private boolean addCategory(user obj) {
		return uDAO.addCategory(obj);
	}
}
