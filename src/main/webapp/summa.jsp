<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="DAOs.contactDAO" %>
<%@ page import="POJO.contact" %>
<%@ page import="DAOs.userDAO" %>
<%@ page import="POJO.*" %>
<%@ page import="Extras.*" %>
<%@ page import="java.util.*" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Contact Details</title>
    <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@300;400;500;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <style>
        :root {
            --primary-color: #4a90e2;
            --secondary-color: #f5f7fa;
            --text-color: #333;
            --shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
        }

        body {
            font-family: 'Roboto', sans-serif;
            background-color: var(--secondary-color);
            color: var(--text-color);
            margin: 0;
            padding: 0;
            min-height: 100vh;
            display: flex;
            flex-direction: column;
        }

        .header {
            background-color: white;
            box-shadow: var(--shadow);
            padding: 1rem 2rem;
            display: flex;
            justify-content: space-between;
            align-items: center;
        }

        .header-title {
            flex: 1;
            text-align: flex-start;
            font-weight: bold;
            color: var(--primary-color);
            font-size: 30px;
        }

        .profile-button {
        	height: 40px;
            background-color: var(--primary-color);
            color: white;
            border: none;
            padding: 0.5rem 1rem;
            border-radius: 10px;
            cursor: pointer;
            transition: background-color 0.3s ease;
        }

        .profile-button:hover {
            background-color: #3a7bd5;
        }

        .searchbar {
            padding: 0.5rem 1rem;
            border: 1px solid #ddd;
            border-radius: 20px;
            width: 300px;
            font-size: 1rem;
            margin-right: 1rem;
        }

        .main-content {
            flex-grow: 1;
            padding: 2rem;
            max-width: 1200px;
            margin: 0 auto;
            width: 100%;
        }

        h1 {
            text-align: center;
            color: var(--primary-color);
            margin-bottom: 2rem;
        }

        .dashboard {
            display: grid;
            grid-template-columns: repeat(auto-fill, minmax(250px, 1fr));
            gap: 2rem;
        }

        .contact-card {
            background-color: white;
            border-radius: 10px;
            overflow: hidden;
            box-shadow: var(--shadow);
            transition: transform 0.3s ease, box-shadow 0.3s ease;
        }

        .contact-card:hover {
            transform: translateY(-5px);
            box-shadow: 0 6px 12px rgba(0, 0, 0, 0.15);
        }

        .contact-image {
            width: 100%;
            height: 200px;
            object-fit: cover;
        }

        .contact-info {
            padding: 1rem;
        }

        .contact-name {
            font-size: 1.2rem;
            font-weight: 500;
            margin: 0 0 0.5rem 0;
        }

        .contact-relation {
            color: #666;
            font-size: 0.9rem;
            margin-bottom: 1rem;
        }

        .contact-details {
            font-size: 0.9rem;
            margin-bottom: 1rem;
        }

        .contact-details i {
            width: 20px;
            color: var(--primary-color);
        }

        .know-more-btn, .add-contact-btn {
            display: block;
            width: 100%;
            padding: 0.75rem;
            background-color: var(--primary-color);
            color: white;
            text-align: center;
            text-decoration: none;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            transition: background-color 0.3s ease;
        }

        .know-more-btn:hover, .add-contact-btn:hover {
            background-color: #3a7bd5;
        }

        .sidebar {
            position: fixed;
            right: -300px;
            top: 0;
            width: 300px;
            height: 100%;
            background-color: white;
            box-shadow: 2px 0 5px rgba(0, 0, 0, 0.1);
            transition: left 0.3s ease;
            padding: 2rem;
            box-sizing: border-box;
        }

        .sidebar.open {
            right: 0;
        }

        .close-btn {
            position: absolute;
            top: 1rem;
            right: 1rem;
            background: none;
            border: none;
            font-size: 1.5rem;
            cursor: pointer;
        }

        .sidebar-info {
            margin-bottom: 2rem;
        }

        .sidebar-info a {
            color: var(--primary-color);
            text-decoration: none;
        }
        
        .yourcontact{
        	display:flex;
        	align-items: space-between;
        	flex-direction: row;
        }

        .logout-btn {
        	margin:10px;
            width: 100%;
            padding: 0.75rem;
            background-color: #e74c3c;
            color: white;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            transition: background-color 0.3s ease;
        }

        .logout-btn:hover {
            background-color: #c0392b;
        }

		.no-contacts {
		    display: flex; /* Use flexbox for centering */
		    flex-direction: column; /* Arrange items in a column */
		    align-items: center; /* Center items horizontally */
		    justify-content: center; /* Center items vertically */
		    text-align: center; /* Center text */
		    font-size: 1.2rem;
		    color: #666;
		    margin-top: 2rem;
		}

        @media (max-width: 768px) {
            .header {
                flex-direction: column;
                align-items: stretch;
            }

            .searchbar {
                width: 100%;
                margin-top: 1rem;
            }

            .dashboard {
                grid-template-columns: 1fr;
            }
        }
    </style>
</head>
<body>
    <div class="header">
        <div class="header-title" style="color:black">Zoho Contacts</div>
        <input class="searchbar" type="text" placeholder="Search contacts">
        <button class="profile-button" onclick="toggleSidebar()">
            <i class="fas fa-user"></i> Profile
        </button>
    </div>
    <div class="yourcontact">
    	<h1>Your Contacts</h1>
    	<form action="contact.jsp">
    		<input type="submit" value="Add Contact">
    	</form>
    </div>
    <div class="main-content">

		<%
		    userDAO userObj = new userDAO();
		    String SessionID = (String) GetCookie.myCookie(request, "SessionID");
			user userRes = null; // Initialize userRes
		
		    if (SessionID != null) {
		        userRes = userObj.getUserFromSession(SessionID);
		    } 

		
		    if (userRes == null) {
		        out.println("<div class='error-message'>User not found. Please log in again.</div>");
		        return;
		    }
		
		    List<contact> contactList = new contactDAO().getAllContacts(userRes.getUID());
		%>

        <div class="dashboard">
            <%
            if (contactList.isEmpty()) {
            %>
                <div class="no-contacts">
                    <p>No contacts found.</p>
                    <form method="post" action="contact.jsp">
                    	<input type="submit" value="Add New Contact" class="add-contact-btn">
                    </form>
                </div>
            <%
            } else {
            	
                for (int i = 0; i<contactList.size(); i++) {
            %>
                <div class="contact-card">
                    <img src="https://api.dicebear.com/7.x/avataaars/svg?seed=<%= contactList.get(i).getName() %>" alt="<%= contactList.get(i).getName() %>" class="contact-image">
                    <div class="contact-info">
                        <h3 class="contact-name"><%= contactList.get(i).getName() %></h3>
                        <p class="contact-relation"><%= contactList.get(i).getRelatedPerson() %></p>
                        <div class="contact-details">
                            <p><i class="fas fa-envelope"></i> <%= contactList.get(i).getEmailID().get(0) %></p>
                            <p><i class="fas fa-phone"></i> <%= contactList.get(i).getPhoneNumber().get(0) %></p>
                        </div>
                        <form action="listService" method="post">
                        	<input type="hidden" name="request" value="contact">
                        	<input type="hidden" name="PID" value="<%= contactList.get(i).getPID() %>">
                            <button type="submit" class="know-more-btn">Know More</button>
                        </form>
                    </div>
                </div>
            <%
                }
            }
            %>
        </div>
    </div>

    <div class="sidebar" id="sidebar">
        <button class="close-btn" onclick="toggleSidebar()"><i class="fas fa-times"></i></button>
        <div class="sidebar-info">
            <h2>Profile</h2>
            <p><strong>UID:</strong> <a href="profile.jsp"><%= userRes.getUID() %></a></p>
            <p><strong>Name:</strong> <%= userRes.getName() %></p>
            <p><strong>Email:</strong> <% List<String> emails = userRes.getEmailID();
            if (emails != null && !emails.isEmpty()) {
                for (String email : emails) {
                    out.println(email + "<br/>");
                }
            } else {
                out.println("No email available.");
            } %></p>
        </div>
        <form method="post">
            <button type="submit" class="logout-btn">Logout</button>
        </form>	
        
        <form method="post" action="deleteService">
        	<input type="hidden" name="request" value="session">
        	<input type="submit" class="logout-btn" value="Logout from all the devices">   
        </form>	
        
    </div>

    <script>
        function toggleSidebar() {
            const sidebar = document.getElementById('sidebar');
            sidebar.classList.toggle('open');
        }

        <%
        session sObj = new session();
        if ("post".equalsIgnoreCase(request.getMethod())) {
            	sObj.setSessionId(GetCookie.myCookie(request,"SessionID"));
            	// reduce the code using the userDAO - clearcookie --> update
            	boolean res = userObj.clearCookie(sObj, request, response);
            	System.out.println(res);
                if (res) {
                	//boolean result = deleteCookie.delete(request, response);	
                		response.sendRedirect("login.jsp");	
                }
		}
        %>
    </script>
</body>
</html>