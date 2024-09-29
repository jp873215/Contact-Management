<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.ex.pojo.contact" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Add Contact | Zoho Contacts</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
            margin: 0;
            padding: 20px;
        }
        h1 {
            color: #333;
        }
        form {
            background: white;
            padding: 50px;
            border-radius: 8px;
            box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
            max-width: 400px;
            margin: auto;
        }
        input[type="text"],
        input[type="number"],
        input[type="date"],
        textarea {
            width: 100%;
            padding: 10px;
            margin: 10px 0;
            border: 1px solid #ccc;
            border-radius: 4px;
        }
        input[type="submit"] {
            background-color: #007bff;
            color: white;
            border: none;
            margin-top: 10px;
            padding: 10px;
            border-radius: 4px;
            cursor: pointer;
            transition: background-color 0.3s;
        }
        input[type="submit"]:hover {
            background-color: #0056b3;
        }
        label {
            font-weight: bold;
            margin: 10px 0 5px;
            display: block;
        }
    </style>
</head>
<body>
	<% contact obj = (contact) request.getAttribute("object"); %>
    <center><h1><%= obj != null ? "Update Contact" : "Add Contact" %></h1></center>
    
    <form action="<%= obj != null ? "updateContact" : "addcontact" %>" method="post">
        <% if (obj != null) { %>
            <input type="hidden" name="PID" value="<%= obj.getPID() %>">
        <% } %>
        <label for="name">Name</label>
        <input type="text" id="name" name="name" placeholder="Enter Name" value="<%= obj != null ? obj.getName() : "" %>" required>

        <label for="related_person">Related Person</label>
        <input type="text" id="related_person" name="related_person" value="<%= obj != null ? obj.getRelatedPerson() : "" %>" placeholder="How is the person related to you">

        <label for="notes">Notes</label>
        <textarea id="notes" name="notes" placeholder="Note about the Person"><%= obj != null ? obj.getNotes() : "" %></textarea>

        <label for="phone_number">Phone Number</label>
        <input type="text" id="phone_number" name="phone_number" placeholder="Enter Phone Number" value="<%= obj != null ? obj.getPhoneNumber() : "" %>">
        
        <label for="phone_number_label">Phone Number Label</label>
        <input type="text" id="phone_number_label" name="phone_number_label" placeholder="Enter Label for Phone Number" value="<%= obj != null ? obj.getPhoneLabel() : "" %>">

        <label for="email_id">Email ID</label>
        <input type="text" id="email_id" name="email_id" placeholder="Enter Email ID" value="<%= obj != null ? obj.getEmailID() : "" %>">

        <label for="email_id_label">Email ID Label</label>
        <input type="text" id="email_id_label" name="email_id_label" placeholder="Enter Label for Email ID" value="<%= obj != null ? obj.getEmailIDLabel() : "" %>">

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
</body>
</html>
