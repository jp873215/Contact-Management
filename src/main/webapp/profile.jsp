<%@ page import="POJO.*" %>
<%@ page import="DAOs.*" %>
<%@ page import="Extras.*" %>
<%@ page import="java.util.*" %>

<%
userDAO userObj = new userDAO();
String SID = GetCookie.myCookie(request, "SessionID");
String UID = GetCookie.myCookie(request, "UID");
user userRes = userObj.getUserFromSession(Integer.parseInt(UID));
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title><%= userRes.getName() %> | Profile | Zoho</title>
    <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@300;400;700&display=swap" rel="stylesheet">
    <style>
        :root {
            --primary-color: #4a90e2;
            --secondary-color: #f5f7fa;
            --text-color: #333;
            --card-bg: #ffffff;
        }

        body {
            font-family: 'Roboto', sans-serif;
            background-color: var(--secondary-color);
            color: var(--text-color);
            margin: 0;
            padding: 0;
            line-height: 1.6;
        }

        .header {
            background-color: var(--primary-color);
            color: white;
            text-align: center;
            padding: 1rem 0;
        }

        .container {
            max-width: 900px;
            margin: 2rem auto;
            background-color: var(--card-bg);
            border-radius: 12px;
            box-shadow: 0 5px 15px rgba(0, 0, 0, 0.1);
            padding: 2rem;
        }

        .profile-image {
            width: 180px;
            height: 180px;
            border-radius: 50%;
            margin: 0 auto 1.5rem;
            display: block;
            border: 4px solid var(--primary-color);
            box-shadow: 0 5px 15px rgba(0, 0, 0, 0.2);
        }

        h1 {
            margin: 0;
            font-weight: 700;
        }

        .profile-info {
            background-color: var(--secondary-color);
            border-radius: 8px;
            padding: 1.5rem;
            margin-bottom: 2rem;
        }

        .profile-info p {
            margin: 0.8rem 0;
            font-size: 1.1rem;
            display: flex;
            align-items: center;
        }

        .profile-info strong {
            min-width: 120px;
            font-weight: 700;
            color: var(--primary-color);
        }

        .dashboard {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
            gap: 1.5rem;
            margin-top: 2rem;
        }

        .card {
            background-color: var(--card-bg);
            border: 1px solid #e1e1e1;
            border-radius: 8px;
            padding: 1.5rem;
            text-align: center;
            box-shadow: 0 3px 10px rgba(0, 0, 0, 0.1);
            transition: all 0.3s ease;
        }

        .card:hover {
            transform: translateY(-5px);
            box-shadow: 0 5px 15px rgba(0, 0, 0, 0.2);
        }

        .card input[type="submit"] {
            background-color: var(--primary-color);
            color: white;
            border: none;
            padding: 0.8rem 1.2rem;
            border-radius: 5px;
            cursor: pointer;
            font-size: 1rem;
            transition: all 0.3s ease;
            width: 100%;
        }

        .card input[type="submit"]:hover {
            background-color: #3a7bc8;
            transform: scale(1.05);
        }

        .back-button {
            text-align: center;
            margin-top: 2rem;
        }

        .back-button input {
            padding: 0.8rem 1.5rem;
            border: none;
            background-color: var(--primary-color);
            color: white;
            border-radius: 5px;
            cursor: pointer;
            font-size: 1rem;
            transition: all 0.3s ease;
        }

        .back-button input:hover {
            background-color: #3a7bc8;
            transform: scale(1.05);
        }

        @media (max-width: 768px) {
            .container {
                padding: 1.5rem;
            }

            .profile-image {
                width: 150px;
                height: 150px;
            }

            .profile-info p {
                flex-direction: column;
                align-items: flex-start;
            }

            .profile-info strong {
                margin-bottom: 0.3rem;
            }

            .dashboard {
                grid-template-columns: 1fr;
            }
        }
    </style>
</head>
<body>
    <div class="header">
        <h1>User Profile</h1>
    </div>

    <div class="container">
        <img src="https://api.dicebear.com/7.x/avataaars/svg?seed=<%= userRes.getName() %>" alt="Profile Picture" class="profile-image">

        <div class="profile-info">
            <p><strong>User ID:</strong> <span id="userId"><%= userRes.getUID() %></span></p>
            <p><strong>Name:</strong> <span id="userId"><%= userRes.getName() %></span></p>
            <p><strong>Email ID:</strong> <span id="userId"><% 
                List<String> emails = userRes.getEmailID();
                if (emails != null && !emails.isEmpty()) {
                    for (String email : emails) {
                        out.println(email + "<br/>");
                    }
                } else {
                    out.println("No email available.");
                } %></span></p>
        </div>

        <div class="dashboard">
            <div class="card">
                <h3>Emails</h3>
                <form action="listService" method="post">
                    <input type="hidden" name="request" value="email">
                    <input type="submit" value="Add Email">
                </form>
            </div>

            <div class="card">
                <h3>Categories</h3>
                <form action="listService" method="post">
                    <input type="hidden" name="request" value="category">
                    <input type="submit" value="Manage Categories">
                </form>
            </div>

            <div class="card">
                <h3>Contact Management</h3>
                <form action="contact.jsp">
                    <input type="submit" value="Add Contact">
                </form>
            </div>
        </div>

        <div class="back-button">
            <form action="success.jsp">
                <input type="submit" value="Go Back to Home">
            </form>
        </div>
    </div>
</body>
</html>
