<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.ex.DAOs.contactDAO" %>
<%@ page import="com.ex.pojo.contact" %>
<%@ page import="java.sql.*" %>	
<%@ page import="java.util.*" %>
<html>
<head>
    <title>Contact Details</title>
</head>
<body>

<%
    int uid = (int) session.getAttribute("UID");
    contactDAO dao = new contactDAO();
    List<contact> li = dao.getAllContacts(uid);

    if (li.isEmpty()) {
        out.println("<h3>No contacts found.</h3>");
    } else {
        for (contact ct : li) {
%>
            <div>
                <h2>Contact Name: <%= ct.getName() %></h2>
                <p>Notes: <%= ct.getNotes() %></p>
                <p>Related Person: <%= ct.getRelatedPerson() %></p>
                <p>Home Address: <%= ct.getHomeAddress() %></p>
                <p>Work Address: <%= ct.getWorkAddress() %></p>
                <p>Email: <%= ct.getEmailID() %> (<%= ct.getEmailIDLabel() %>)</p>
                <p>Phone: <%= ct.getPhoneNumber() %> (<%= ct.getPhoneLabel() %>)</p>
                <p>Date: <%= ct.getDate() %> (<%= ct.getDateLabel() %>)</p>
                <hr/>
            </div>
<%
        }
    }
%>

</body>
</html>
