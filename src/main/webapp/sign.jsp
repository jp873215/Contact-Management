<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
%>
<!DOCTYPE html> 
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>SignUp | Zoho Contacts</title>
    <style>
        body {
    font-family: 'Arial', sans-serif;
    background-color: #f4f4f4;
    color: #333;
    display: flex;
    justify-content: center;
    align-items: center;
    height: 100vh;
    margin: 0;
    }

    form {
        background: white;
        padding: 2rem;
        border-radius: 8px;
        box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
        width: 300px;
    }

    input[type="text"],
    input[type="password"],
    input[type="email"],
    input[type="number"] {
        width: 100%;
        padding: 10px;
        margin: 10px 0;
        border: 1px solid #ccc;
        border-radius: 4px;
        transition: border-color 0.3s;
    }

    input[type="text"]:focus,
    input[type="password"]:focus,
    input[type="email"]:focus,
    input[type="number"]:focus {
        border-color: #007BFF;
        outline: none;
    }

    input[type="submit"] {
        background-color: #007BFF;
        color: white;
        padding: 10px;
        border: none;
        border-radius: 4px;
        cursor: pointer;
        width: 100%;
        transition: background-color 0.3s;
    }

    input[type="submit"]:hover {
        background-color: #0056b3;
    }

    p {
        text-align: center;
        margin-top: 1rem;
    }

    a {
        color: #007BFF;
        text-decoration: none;
    }

    a:hover {
        text-decoration: underline;
    }

    .radio-group {
        display: flex;
        justify-content: center;
        margin: 10px 0;
    }

    .radio-group input {
        margin-right: 5px;
    }

    </style>
    
</head>
<body>
    <form action="addService" method="post">
    	<input type="hidden" name="request" value="signup" />
        <input type="text" name="name" placeholder="Enter Name" required>
        <input type="text" name="email" placeholder="Enter Email" required>
        <input type="text" name="elabel" placeholder="Enter Label for Email ID" required>
        <input type="password" name="password" placeholder="Enter Password" required>
        <input type="number" name="phone" placeholder="Enter Phone Number" required>
        <input type="text" name="plabel" placeholder="Enter Label for Phone Number" required>
        <input type="radio" name="gender" value="Male">Male
        <input type="radio" name="gender" value="Female">Female
        <input type="text" name="homeAddress" placeholder="Enter Home Address" required>
        <input type="text" name="workAddress" placeholder="Enter Work Address" required>
        
        <input type="submit">
        <p>Already have account? <a href="login.jsp">Login</a></p>
    </form>
</body>
</html>