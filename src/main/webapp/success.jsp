<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.ex.DAOs.contactDAO" %>
<%@ page import="com.ex.pojo.contact" %>
<%@ page import="java.sql.*" %>    
<%@ page import="java.util.*" %>

<% 
if (session.getAttribute("email") == null){
    response.sendRedirect("login.jsp");
    return;
}
%>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Contact Details</title>
    <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@400;700&display=swap" rel="stylesheet">
    <style>
        body {
            font-family: 'Roboto', sans-serif;
            background-color: #f4f7fa; 
            color: #333;
            margin: 0;
            display: flex;
            flex-direction: column;
            align-items: center;
            padding: 0 20 0 20px;
        }

        h1 {
            text-align: center;
            color: #007BFF;
            margin-bottom: 20px;
        }

        table {
            width: 200%;
            max-width: 900px;
            margin: 0 auto;
            border-collapse: collapse;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
        }

        th, td {
            padding: 12px;
            text-align: left;
            border-bottom: 1px solid #ddd;
        }

        th {
            background-color: #007BFF;
            color: white;
        }

        tr:hover {
            background-color: #f1f1f1;
        }

        .no-contacts {
            text-align: center;
            font-size: 1.2rem;
            margin: 20px;
        }

        .header {
            background-color: #007BFF;
            color: white;
            width: 100%;
            padding: 0 20 0 20px;
            display: flex;
            align-items: center;
            flex-direction: row-reverse;
            box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
        }

        .header h1 {
            margin: 0;
            font-size: 2rem;
        }

        .logout {
            margin-bottom: 20px;
        }

        .dashboard {
            display: grid;
            grid-template-columns: repeat(auto-fill, minmax(220px, 1fr));
            gap: 20px;
            width: 150%;
            max-width: 900px;
        }

        .card {
            background-color: white;
            border: 1px solid #ddd;
            border-radius: 8px;
            padding: 20px;
            text-align: center;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            transition: transform 0.2s, box-shadow 0.2s;
        }

        .card:hover {
            transform: translateY(-4px);
            box-shadow: 0 8px 16px rgba(0, 0, 0, 0.2);
        }
        .sidebar {
            width: 300px;
            position: fixed;
            right: -350px;
            top: 0;
            height: 100%;
            background-color: white;
            color: white;
            transition: left 0.3s ease ;
            padding: 20px;
            box-shadow: 2px 0 5px rgba(0, 0, 0, 0.3);
            z-index: 1000; 
            
        }
        .sidebar_info {
        	margin-bottom: 30px;
        }
        
        .sidebar_info p{
        	color: black;	
        }

        .sidebar a {
            color: white;
            text-decoration: none;
            display: block;
            padding: 10px;
            margin: 5px 0;
        }

        .sidebar a:hover {
            background-color: rgba(255, 255, 255, 0.2);
        }

        .close-btn {
            background: none;
            border: none;
            color: white;
            font-size: 1.5rem;
            cursor: pointer;
            position: absolute;
            right: 20px;
            top: 20px;
        }

        .profile {
            margin: 10px;
            height: 40px;
            width: 40px;
            border-radius: 10px;
            background: white;
            cursor: pointer;
        }

        input[type="submit"] {
            background-color: #007BFF; 
            color: white;
            padding: 10px 15px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            transition: background-color 0.3s, transform 0.2s;
            font-size: 1rem;
        }

        input[type="submit"]:hover {
            background-color: #0056b3; 
            transform: scale(1.05);
        }

        input[type="text"] {
            padding: 10px;
            border: 2px solid #ddd;
            border-radius: 8px;
            width: 250px;
            transition: border-color 0.3s;
        }

        input[type="text"]:focus {
            border-color: #007BFF;
            outline: none;
        }
        

        footer {
            margin-top: 40px;
            font-size: 0.9rem;
            color: #666;
        }
        
        .profile{
        	margin: 10px;
        	height: 40px;
        	width: 40px;
        	border-radius: 10px;
        	background: white;
        	cursor: pointer;
        }
        .image{
        	height: 150px;
        	width: 150px;
        	background: black;
        	
        	
        }
        .sidebar_info{
        	color: white;
        	height:100px;
        	width: 100px;
        }
        .logout input[type="submit"] {
            background-color: #FF0000; 
            color: white;
            padding: 10px 15px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            transition: background-color 0.3s, transform 0.2s;
            font-size: 1rem;
        }

        .logout input[type="submit"]:hover {
            background-color: #FF0000; 
            transform: scale(1.05);
            
        }
    </style>
</head>
<body>

    <div class="header">
        <div class="profile" onclick="toggleSidebar()"></div>
        <input class="searchbar" type="text" placeholder="search contacts">
    </div>

    <div class="sidebar" id="sidebar">
        <button class="close-btn" onclick="toggleSidebar()" style="color:black">×</button>
        
        <div class="image">
        	<img src="">
        </div>
        <div class="sidebar_info">
        		<p><%= session.getAttribute("name") %></p>
        		<p><%= session.getAttribute("email") %></p>
        		<p>user ID: <%= session.getAttribute("UID") %></p>
		</div>
	    <form method="post" class="logout">
        	<input type="submit" value="Logout">
    	</form>
    </div>
	
    <h1>Welcome <%= session.getAttribute("name") %></h1>
    

    <div class="dashboard">
        <div class="card">
            <form action="listemail" method="post">
                <input type="submit" value="Add Email">
            </form>
        </div>

        <div class="card">
            <form action="listphone" method="post">
                <input type="submit" value="Add Phone">
            </form>
        </div>

        <div class="card">
            <form action="contact.jsp">
                <input type="submit" value="Add Contact">
            </form>
        </div>
    </div>

    <hr>
    <h1>Contact Details</h1>

    <%
        if ("post".equalsIgnoreCase(request.getMethod())) {
            session.invalidate();
            response.sendRedirect("login.jsp");
            return;
        }
    	
        int uid = (int) session.getAttribute("UID");
        
        contactDAO dao = new contactDAO();
        List<contact> li = dao.getAllContacts(uid);
    %>

    <% if (li.isEmpty()) { %>
        <div class="no-contacts">No contacts found.</div>
    <% } else { %>
        <table>
            <thead>
                <tr>
                    <th>PID</th>
                    <th>Contact Name</th>
                    <th>Notes</th>
                    <th>Related Person</th>
                    <th>Home Address</th>
                    <th>Work Address</th>
                    <th>Email</th>
                    <th>Phone</th>
                    <th>Date</th>
                    <th>Delete Contact</th>
                    <th>Update Contact</th>
                </tr>
            </thead>
            <tbody>
                <%
                for (contact ct : li) {
                %>
                    <tr>
                    	<td><%= ct.getPID() %> </td>
                        <td><%= ct.getName() %></td>
                        <td><%= ct.getNotes() %></td>
                        <td><%= ct.getRelatedPerson() %></td>
                        <td><%= ct.getHomeAddress() %></td>
                        <td><%= ct.getWorkAddress() %></td>
                        <td><%= ct.getEmailID() %> (<%= ct.getEmailIDLabel() %>)</td>
                        <td><%= ct.getPhoneNumber() %> (<%= ct.getPhoneLabel() %>)</td>
                        <td><%= ct.getDate() %> (<%= ct.getDateLabel() %>)</td>
                        <td>
                       	<form action="deleteContact" style="display:inline" method="post">
                       		<input type="hidden" name="deletecontact" value="<%= ct.getPID()%>">
                       		<input type="submit" value="Delete">
                       	</form>
                        </td>
                        <td>						
                        <form action="updateContact" style="display:inline" method="post">
                       		<input type="hidden" name="updatecontact" value="<%= ct.getPID()%>">
                       		<input type="submit" value="Update">
                       	</form></td>
                    </tr>
                <%
                }
                %>
            </tbody>
        </table>
    <% } %>
    <script>
        function toggleSidebar() {
            const sidebar = document.getElementById('sidebar');
            sidebar.style.right = sidebar.style.right === '0px' ? '-350px' : '0px';
        }

        function logout() {
            document.querySelector('.logout').submit();
        }
    </script>
</body>
</html>
