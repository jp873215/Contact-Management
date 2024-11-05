<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="POJO.*" %>

<%
List<contact> li = (List<contact>) request.getAttribute("MembersList");
String catName = (String) request.getAttribute("CategoryName");
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Category Members | Zoho Contacts</title>
    <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@300;400;700&display=swap" rel="stylesheet">
    <style>
        :root {
            --primary-color: #3498db;
            --secondary-color: #2980b9;
            --background-color: #ecf0f1;
            --text-color: #34495e;
            --error-color: #e74c3c;
        }

        body {
            font-family: 'Roboto', sans-serif;
            background-color: var(--background-color);
            color: var(--text-color);
            line-height: 1.6;
            margin: 0;
            padding: 20px;
            display: flex;
            flex-direction: column;
            align-items: center;
        }

        h1 {
            color: var(--primary-color);
            margin-bottom: 30px;
        }

        .contact-container {
            width: 100%;
            max-width: 800px;
            border: 1px solid #ddd;
            border-radius: 8px;
            margin-bottom: 20px;
            padding: 15px;
            background-color: white;
            box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
            pointer: cursor;
        }

        .contact-info {
            margin-bottom: 10px;
        }

        .error-message {
            color: var(--error-color);
            font-weight: bold;
        }

        a {
            display: inline-block;
            padding: 10px 20px;
            margin-top: 20px;
            background-color: var(--primary-color);
            color: white;
            text-decoration: none;
            border-radius: 5px;
            transition: background-color 0.3s ease;
        }

        a:hover {
            background-color: var(--secondary-color);
        }

        .delete-button {
            background-color: var(--error-color);
            color: white;
            border: none;
            padding: 8px 12px;
            border-radius: 5px;
            cursor: pointer;
            transition: background-color 0.3s ease;
        }

        .delete-button:hover {
            background-color: darkred;
        }

        .modal {
            display: none;
            position: fixed;
            z-index: 1000;
            left: 0;
            top: 0;
            width: 100%;
            height: 100%;
            overflow: auto;
            background-color: rgba(0, 0, 0, 0.4);
        }

        .modal-content {
            background-color: #fefefe;
            margin: 15% auto;
            padding: 20px;
            border: 1px solid #888;
            width: 80%;
            max-width: 500px;
            border-radius: 8px;
        }

        .close {
            color: #aaa;
            float: right;
            font-size: 28px;
            font-weight: bold;
            cursor: pointer;
        }
    </style>
</head>
<body>
    <h1>Members of Category: <%= catName %></h1>

    <button id="openModal" style="padding: 10px; margin-bottom: 20px; background-color: var(--primary-color); color: white; border: none; border-radius: 5px; cursor: pointer;">
        Add Contact
    </button>

    <div id="myModal" class="modal">
        <div class="modal-content">
            <span class="close" id="closeModal">&times;</span>
            <h2>Add New Contact</h2>
            <form method="post" action="addService">
            	<input type="hidden" name="request" value="members">
            	<input type="hidden" name="cat_name" value="<%= catName %>">
                <label for="name">Name:</label>
                <input type="text" name="name" required>
                <br>
                <label for="email">Email:</label>
                <input type="email" name="email" required>
                <br>
                <label for="phone">Phone:</label>
                <input type="text" name="phone" required>
                <br>
                <input type="hidden" name="categoryName" value="<%= catName %>">
                <input type="submit" value="Add Contact" style="background-color: var(--primary-color); color: white; border: none; padding: 10px; border-radius: 5px; cursor: pointer;">
            </form>
        </div>
    </div>

    <% 
    if (li != null && !li.isEmpty()) {
        for (int i = 0; i < li.size(); i++) {
    %>
        <div class="contact-container">
            <div class="contact-info"><strong>Name:</strong> <%= li.get(i).getName() %></div>
            <div class="contact-info"><strong>Email:</strong> <%= li.get(i).getEmailID().get(i) %></div>
            <div class="contact-info"><strong>Phone:</strong> <%= li.get(i).getPhoneNumber().get(i) %></div>
            <form method="post" action="deleteContact">
                <input type="hidden" name="pid" value="<%= li.get(i).getPID() %>">
                <button type="submit" class="delete-button">Delete</button>
            </form>
        </div>
    <%
        }
    } else {
        out.println("<div class='error-message'>No members found in this category.</div>");
    }
    %>
    
    
	<!-- We need to change this or else the object is passed -->
    <form action="listService" method="post">
    	<input type="hidden" name="request" value="category">
    	<input type="submit" value="Back to Category" id="openModal" style="padding: 10px; margin-bottom: 20px; background-color: var(--primary-color); color: white; border: none; border-radius: 5px; cursor: pointer;">
    </form>

    <script>
        var modal = document.getElementById("myModal");
        var btn = document.getElementById("openModal");
        var span = document.getElementById("closeModal");

        btn.onclick = function() {
            modal.style.display = "block";
        }

        span.onclick = function() {
            modal.style.display = "none";
        }

        window.onclick = function(event) {
            if (event.target == modal) {
                modal.style.display = "none";
            }
        }
    </script>
</body>
</html>
