<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="java.io.PrintWriter" %>

<%
//     if ((String) session.getAttribute("email") == null){
//         response.sendRedirect("login.jsp");
//         return;
//     }
    
    List<String> emailList = (List<String>) request.getAttribute("email");
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Email List</title>
    <style>
        ul {
            list-style: none;
            padding: 0;
        }
        .error-message {
            color: red;
        }
    </style>
</head>
<body>
    <h1>Your Emails</h1>
    <ul>
    <%
    if (emailList != null && !emailList.isEmpty()) {
        for (String email : emailList) {
            out.println("<li>" + email + "</li>");
        }
    } else {
        out.println("<li class=\"error-message\">No emails found.</li>");
    }
    %>
    </ul>
    <form action="addemail" method="post">
        <input type="text" name="email" placeholder="Enter your Email">
        <input type="text" name="emaillabel" placeholder="Enter Label for Email">
        <input type="submit" value="Add Email">
    </form>
    <br>
    <a href="success.jsp">Back to Welcome Page</a>
</body>
</html>