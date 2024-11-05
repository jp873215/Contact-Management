<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="java.io.PrintWriter" %>
<%@ page import="POJO.*" %>


<%
user userObj = (user) request.getAttribute("EmailList");
List<String> emailList = userObj.getEmailID();
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Email List</title>
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

        /* Scrollable email list */
        .email-list {
            width: 100%;
            max-width: 500px;
            max-height: 300px; /* Scrollable when emails exceed 5 items */
            overflow-y: auto;
            margin-bottom: 20px;
            border: 1px solid #ddd;
            border-radius: 5px;
            padding: 10px;
            box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
        }

        ul {
            list-style: none;
            padding: 0;
            margin: 0;
        }

        li {
            background-color: white;
            margin-bottom: 10px;
            padding: 15px;
            border-radius: 5px;
            box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
            transition: transform 0.3s ease;
            display: flex;
            justify-content: space-between;
            align-items: center;
        }

        li:hover {
            transform: translateY(-3px);
        }

        .error-message {
            color: var(--error-color);
            font-weight: bold;
        }

        .button-group {
            display: flex;
            gap: 10px;
        }

        .button-group button {
            background-color: var(--primary-color);
            border: none;
            color: white;
            padding: 8px 12px;
            border-radius: 5px;
            cursor: pointer;
            font-size: 14px;
            transition: background-color 0.3s ease;
        }

        .button-group button:hover {
            background-color: var(--secondary-color);
        }

        /* Email input form */
        .email-form {
            position: sticky;
            top: 0;
            width: 100%;
            max-width: 500px;
            padding: 10px;
            margin-bottom: 20px;
            z-index: 10;
            box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
        }

        .email-form input[type="text"] {
            padding: 10px;
            margin-bottom: 10px;
            border: 1px solid #ddd;
            border-radius: 5px;
            font-size: 16px;
            width: 100%;
        }

        .email-form input[type="submit"] {
            background-color: var(--primary-color);
            color: white;
            border: none;
            padding: 12px;
            border-radius: 5px;
            cursor: pointer;
            font-size: 16px;
            transition: background-color 0.3s ease;
            width: 100%;
        }

        .email-form input[type="submit"]:hover {
            background-color: var(--secondary-color);
        }

        a {
            color: var(--primary-color);
            text-decoration: none;
            margin-top: 20px;
            font-weight: bold;
            transition: color 0.3s ease;
        }

        a:hover {
            color: var(--secondary-color);
        }
    </style>
</head>
<body>
    <h1>Your Emails</h1>

    <!-- Email Input Form (always visible) -->
    <form action="addService?request=email" method="post" class="email-form">
        <input type="text" name="email" placeholder="Enter your Email" required>
        <input type="text" name="emaillabel" placeholder="Enter Label for Email">
        <input type="submit" value="Add Email">
    </form>

    <div class="email-list">
        <ul>
            <%
            if (emailList != null && !emailList.isEmpty()) {
                for (String email : emailList) {
            %>
                <li>
                    <span><%= email %></span>
                    <div class="button-group">
                    	<form action="deleteService" method="post">
                    	
                    		<input type="hidden" name="request" value="email"/>
                    		<input type="hidden" name="request_value" value="<%= email %>"/>
                    		<input type="submit" value="Delete"/>
                    	</form>
                        
                    </div>
                </li>
            <%
                }
            } else {
                out.println("<li class=\"error-message\">No emails found.</li>");
            }
            %>
        </ul>
    </div>

    <br>
    <a href="profile.jsp">Back to Profile Page</a>

</body>
</html>
