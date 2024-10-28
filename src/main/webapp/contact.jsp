<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.ex.pojo.contact" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Add Contact | Zoho Contacts</title>
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

        form {
            background: white;
            padding: 30px;
            border-radius: 8px;
            box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
            width: 100%;
            max-width: 500px;
        }

        label {
            font-weight: bold;
            margin: 10px 0 5px;
            display: block;
        }

        input[type="text"],
        input[type="number"],
        input[type="date"],
        textarea {
            width: 100%;
            padding: 10px;
            margin: 10px 0;
            border: 1px solid #ddd;
            border-radius: 5px;
            font-size: 16px;
        }

        textarea {
            resize: vertical;
            min-height: 100px;
        }

        input[type="submit"] {
            background-color: var(--primary-color);
            color: white;
            border: none;
            margin-top: 10px;
            padding: 12px;
            border-radius: 5px;
            cursor: pointer;
            font-size: 16px;
            transition: background-color 0.3s ease;
        }

        input[type="submit"]:hover {
            background-color: var(--secondary-color);
        }

        .error-message {
            color: var(--error-color);
            font-weight: bold;
            margin-bottom: 20px;
        }
    </style>
</head>
<body>
    <% contact obj = (contact) request.getAttribute("object"); %>
    <h1><%= obj != null ? "Edit & Update Contact" : "Add Contact" %></h1>

    <form action="<%= obj != null ? "updateService" : "addService" %>" method="post">
        <% if (obj != null) { %>
            <input type="hidden" name="PID" value="<%= obj.getPID() %>">
        <% } %>
        
        <input type="hidden" name="request" value="contact" />
        
        <label for="name">Name</label>
        <input type="text" id="name" name="name" placeholder="Enter Name" value="<%= obj != null ? obj.getName() : "" %>" required>

        <label for="related_person">Related Person</label>
        <input type="text" id="related_person" name="related_person" value="<%= obj != null ? obj.getRelatedPerson() : "" %>" placeholder="How is the person related to you">

        <label for="notes">Notes</label>
        <textarea id="notes" name="notes" placeholder="Note about the Person"><%= obj != null ? obj.getNotes() : "" %></textarea>

        <label for="phone_number">Phone Number</label>
        <input type="text" id="phone_number" name="phone_number" placeholder="Enter Phone Number" value="<%= obj != null ? obj.getPhoneNumber().get(0) : "" %>">
        
        <label for="phone_number_label">Phone Number Label</label>
        <input type="text" id="phone_number_label" name="phone_number_label" placeholder="Enter Label for Phone Number" value="<%= obj != null ? obj.getPhoneLabel().get(0) : "" %>">

        <label for="email_id">Email ID</label>
        <input type="text" id="email_id" name="email_id" placeholder="Enter Email ID" value="<%= obj != null ? obj.getEmailID().get(0) : "" %>">

        <label for="email_id_label">Email ID Label</label>
        <input type="text" id="email_id_label" name="email_id_label" placeholder="Enter Label for Email ID" value="<%= obj != null ? obj.getEmailIDLabel().get(0) : "" %>">

        <label for="date">Date</label>
        <input type="date" id="date" name="date" value="<%= obj != null ? obj.getDate() : "" %>">

        <label for="datelabel">Date Label</label>
        <input type="text" id="datelabel" name="datelabel" placeholder="Enter Date Label" value="<%= obj != null ? obj.getDateLabel() : "" %>">

        <label for="home_address">Home Address</label>
        <input type="text" id="home_address" name="home_address" placeholder="Enter Home Address" value="<%= obj != null ? obj.getHomeAddress() : "" %>">

        <label for="work_address">Work Address</label>
        <input type="text" id="work_address" name="work_address" placeholder="Enter Work Address" value="<%= obj != null ? obj.getWorkAddress() : "" %>">

        <input type="submit" value="<%= obj != null ? "Update Contact" : "Add Contact" %>">
    </form>
        <form action="success.jsp">
    		<input type="submit" value="Back">
        </form>
</body>
</html>
