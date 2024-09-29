<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login - Contacts | Zoho Contacts</title>
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
        input[type="password"] {
            width: 100%;
            padding: 10px;
            margin: 10px 0;
            border: 1px solid #ccc;
            border-radius: 4px;
            transition: border-color 0.3s;
        }
    
        input[type="text"]:focus,
        input[type="password"]:focus {
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
    </style>
    
</head>
<body>
    <form action="login" method="post">
        <input type="text" name="email" placeholder="Enter Mail" required>
        <input type="password" name="password" placeholder="Enter Password" required>
        <input type="submit">
        <p>No account? <a href="sign.jsp">Signup</a></p>
    </form>
</body>
</html>