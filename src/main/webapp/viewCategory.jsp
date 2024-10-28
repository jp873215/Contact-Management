<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.ex.DAOs.contactDAO" %>
<%@ page import="com.ex.pojo.contact" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Manage Contacts - <%= request.getAttribute("cat_name") %></title>
    <style>
        table {
            width: 100%;
            border-collapse: collapse;
            margin-bottom: 20px;
        }
        table, th, td {
            border: 1px solid black;
        }
        th, td {
            padding: 8px;
            text-align: left;
        }
        input[type="submit"] {
            padding: 10px;
            background-color: #3498db;
            color: white;
            border: none;
            cursor: pointer;
        }
        input[type="submit"]:hover {
            background-color: #2980b9;
        }
        .user-list {
            margin-bottom: 20px;
        }
    </style>
</head>
<body>

<h1>Manage Contacts for <%= (String) request.getAttribute("cat_name") %></h1>

<form class="user-list" method="post" action="addSelectedContacts">
    <h2>Select Users to Add</h2>
    <%
        int uid = (int) session.getAttribute("UID");
        contactDAO dao = new contactDAO();
        List<contact> contacts = dao.getAllContacts(uid);

        if (contacts != null && !contacts.isEmpty()) {
    %>
    <div>
        <label for="contactDropdown">Choose a contact:</label>
        <select name="selectedContact" id="contactDropdown">
            <option value="">Select a contact</option>
            <%
                for (contact ct : contacts) {
            %>
            <option value="<%= ct.getName() %>" data-pid="<%= ct.getPID() %>">
                <%= ct.getName() %>
            </option>
            <%
                }
            %>
        </select>
    </div>
    <%
        } else {
            out.println("<p>No available contacts to add.</p>");
        }
    %>
    <input type="hidden" name="category_name" value="<%= request.getAttribute("cat_name") %>">
    <input type="submit" value="Add Selected Contact">
</form>

<h2>Contact List</h2>
<table>
    <thead>
        <tr>
            <th>Contact Name</th>
            <th>Action</th>
        </tr>
    </thead>
    <tbody>
        <%
            List<String> contactTable = (List<String>) request.getAttribute("contacts");

            if (contactTable != null && !contactTable.isEmpty()) {
                for (String contact : contactTable) {
        %>
        <tr>
            <td><%= contact %></td>
            <td>
                <form method="post" action="deleteContact">
                    <input type="hidden" name="contact_name" value="<%= contact %>">
                    <input type="hidden" name="category_name" value="<%= request.getAttribute("cat_name") %>">
                    <input type="submit" value="Delete">
                </form>
            </td>
        </tr>
        <%
                }
            } else {
                out.println("<tr><td colspan='2'>No contacts found for this category</td></tr>");
            }
        %>
    </tbody>
</table>

</body>
</html>
