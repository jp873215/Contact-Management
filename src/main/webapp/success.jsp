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

        .profile-button, .add-category-btn {
            height: 40px;
            background-color: var(--primary-color);
            color: white;
            border: none;
            padding: 0.5rem 1rem;
            border-radius: 10px;
            cursor: pointer;
            transition: background-color 0.3s ease;
        }

        .profile-button:hover, .add-category-btn:hover {
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

        .categories-section {
            display: flex;
            justify-content: space-between;
            align-items: center;
            <!-- margin-bottom: 2rem; -->
            
        }

        .categories-slider {
            display: flex;
            overflow-x: auto;
            padding: 10px;
            gap: 10px;
            padding-bottom: 20px; 
        }

        .category {
            background-color: white;
            border-radius: 5px;
            padding: 10px 20px;
            box-shadow: var(--shadow);
            min-width: 150px;
            min-height: 25px;
            text-align: center;
            position: relative;
            transition: transform 0.2s ease;
            item-align: center;
        }

        .category:hover {
            transform: scale(1.05);
        }

        .category-name {
            font-weight: bold;
        }

        .category-id {
            font-size: 0.8rem;
            color: #666;
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
            margin: 0 0 0 0;
            text-decoration: None;
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

        .know-more-btn {
            display: block;
            width: 90%;
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

        .know-more-btn:hover {
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
        }

        .sidebar-info {
            margin-bottom: 2rem;
        }

        .sidebar-info a {
            color: var(--primary-color);
            text-decoration: none;
        }
		
		.yourcontact {
		    display: flex;
		    align-items: center;
		    justify-content: space-between;
		    padding: 1rem 0;
		    border-radius: 8px;
		    margin-bottom: 1.5rem;
		}
		
		.yourcontact h1 {
		    margin: 0;
		    font-size: 1.8rem;
		    color: var(--primary-color);
		}
		
		.yourcontact form {
		    margin: 0;
		}

		
        .logout-btn {
            margin: 10px;
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
            display: flex;
            flex-direction: column;
            align-items: center;
            justify-content: center;
            text-align: center;
            font-size: 1.2rem;
            color: #666;
            margin-top: 2rem;
        }
        .h1{
        	align-items: flex-start;
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
    <%
        userDAO userObj = new userDAO();
        String SessionID = (String) GetCookie.myCookie(request, "SessionID");
        user userRes = null;

        if (SessionID != null) {
            userRes = userObj.getUserFromSession(SessionID);
        }

        if (userRes != null) {
            contactDAO con = new contactDAO();
            List<contact> contacts = con.getAllContacts(userRes.getUID());
    %>
    <div class="header">
        <div class="header-title" style="color:black">Zoho Contacts</div>
        <input class="searchbar" type="text" placeholder="Search contacts">
        <button class="profile-button" onclick="toggleSidebar()">
            <i class="fas fa-user"></i> Profile
        </button>
    </div>
    <div class="main-content">
        <div class="categories-section">
            <h1>Categories</h1>
            <form action="listService" method="post">
					<input type="hidden" name="request" value="category">
                    <input class = "add-category-btn" type="submit" value="Add Category">
            </form>
            
            
        </div>
        <div class="categories-slider">
        <%
        user uObj = userObj.getCategory(userRes.getUID());
        if (uObj.getCategory() != null) {
            for (int i = 0; i < uObj.getCategory().size(); i++) {
        %>
            <div class="category">
                <div class="category-name"> <a href="listService?request=categoryMembers&cat_id=<%= (uObj.getCategoryID().get(i)) %>&cat_name=<%= uObj.getCategory().get(i) %>" style="text-decoration: none; color: black"> <%= uObj.getCategory().get(i)%> </a></div>
                <!-- Add total number of members in the category -->
                
            </div>
        <%
            }
        } else {
        %>
            <div class="no-categories">No categories available.</div>
        <%
        }
        %>
        </div>
		<div class="yourcontact">
		    	<h1>Your Contacts</h1>
		    	<form action="contact.jsp">
		    		<input class = "add-category-btn" type="submit" value="Add Contact">
		    	</form>
		</div>
        <div class="dashboard">
        <%
        if (contacts != null && !contacts.isEmpty()) {
            for (int i = 0; i < contacts.size(); i++) {
        %>
            <div class="contact-card">
                <img class="contact-image" src="https://api.dicebear.com/7.x/avataaars/svg?seed=<%= contacts.get(i).getName() %>" alt="Contact Image">
                <div class="contact-info">
                    <div class="contact-name"><%= contacts.get(i).getName()%></div>
                    <div class="contact-relation"><%= contacts.get(i).getRelatedPerson() %></div>
                    <div class="contact-details">
                        <i class="fas fa-phone"></i> <%= contacts.get(i).getPhoneNumber().get(0) %> <br/>
                        <i class="fas fa-envelope"></i> <%= contacts.get(i).getEmailID().get(0)%>
                    </div>	
                    
					<form action="listService" method="post">
                    	<input type="hidden" name="request" value="contact">
                    	<input type="hidden" name="PID" value="<%= contacts.get(i).getPID() %>">
                        <button type="submit" class="know-more-btn">Know More</button>
                    </form>
                   
                    
                </div>
            </div>
        <%
            }
        } else {
        %>
            <div class="no-contacts">No contacts found. Please add contacts.</div>
        <%
        }
        %>
        </div>
    </div>
    <div class="sidebar" id="sidebar">
        <button class="close-btn" onclick="toggleSidebar()">&times;</button>
        <div class="sidebar-info">
            <h2>Profile</h2>
            <p><strong>UID:</strong> <a href="profile.jsp"><%= userRes.getUID() %></a></p>
            <p>Name: <%=userRes.getName()%></p>
            <p>Email: <%=userRes.getEmailID().get(0)%></p>
        </div>
		<form method="post">
            <button type="submit" class="logout-btn">Logout</button>
        </form>
		<form method="post" action="deleteService">
        	<input type="hidden" name="request" value="session">
        	<input type="submit" class="logout-btn" value="Logout from all the devices">   
        </form>	
    </div>
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
    <script>
        function toggleSidebar() {
            const sidebar = document.getElementById('sidebar');
            sidebar.classList.toggle('open');
        }
    </script>
    <% } else { %>
        <script>
            window.location.href = "login.jsp"; 
        </script>
    <% } %>
    
    
    <script>
    function copyContactDetails(name, phone, email) {
        console.log("Copying details:", { name, phone, email }); // Debugging line
        const details = `Name: ${name}\nPhone: ${phone}\nEmail: ${email}`;
        navigator.clipboard.writeText(details).then(() => {
            alert('Contact details copied to clipboard');
        }).catch(err => {
            console.error('Failed to copy: ', err);
        });
    }


    </script>
</body>
</html>
