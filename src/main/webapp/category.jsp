<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="POJO.*" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Category Management | Zoho Contacts</title>
    <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@300;400;700&display=swap" rel="stylesheet">
    <style>
        :root {
            --primary-color: #3498db;
            --secondary-color: #2980b9;
            --background-color: #f5f7fa;
            --text-color: #34495e;
            --error-color: #e74c3c;
            --success-color: #2ecc71;
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

        .form-container, .category-list {
            width: 100%;
            max-width: 500px;
            margin-bottom: 20px;
        }

        form {
            background: white;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
            margin-bottom: 20px;
        }

        input[type="text"] {
            width: 100%;
            padding: 12px;
            margin-bottom: 15px;
            border: 1px solid #ddd;
            border-radius: 5px;
            font-size: 16px;
            transition: border-color 0.3s ease;
        }

        input[type="text"]:focus {
            border-color: var(--primary-color);
        }

        input[type="submit"] {
            width: 100%;
            background-color: var(--primary-color);
            color: white;
            border: none;
            padding: 12px;
            border-radius: 5px;
            font-size: 16px;
            cursor: pointer;
            transition: background-color 0.3s ease;
        }

        input[type="submit"]:hover {
            background-color: var(--secondary-color);
        }

        .category-list ul {
            list-style: none;
            padding: 0;
            margin: 0;
        }

        .category-list ul li {
            background-color: white;
            margin-bottom: 15px;
            padding: 15px;
            border-radius: 5px;
            box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
            display: flex;
            justify-content: space-between;
            align-items: center;
        }

        .category-name {
            font-weight: bold;
        }

        .button-container {
            display: flex;
            gap: 10px; /* Space between buttons */
        }

        .button-container input[type="submit"] {
            padding: 10px 15px;
            font-size: 14px;
            cursor: pointer;
            border-radius: 4px;
        }

        .button-container form input[type="submit"].delete {
            background-color: var(--error-color);
            border: none;
            color: white;
        }

        .button-container form input[type="submit"].delete:hover {
            background-color: #c0392b;
        }

        .button-container form input[type="submit"].view {
            background-color: var(--primary-color);
            border: none;
            color: white;
        }

        .button-container form input[type="submit"].view:hover {
            background-color: var(--secondary-color);
        }
        
        a {
        display: inline-block;
        text-decoration: none;
        color: var(--primary-color);
        background-color: white;
        padding: 10px 20px;
        border: 1px solid var(--primary-color);
        border-radius: 5px;
        font-size: 16px;
        transition: background-color 0.3s ease, color 0.3s ease;
        font-weight: bold;
        margin: 0 auto;
	    }
	
	    a:hover {
	        background-color: var(--primary-color);
	        color: white;
	    }
	    
	    .category-list{
	    	text-align: center;
	    }

        /* Responsive Design */
        @media (max-width: 768px) {
            body {
                padding: 10px;
            }

            .form-container, .category-list {
                max-width: 100%;
            }

            .button-container {
                flex-direction: column;
                gap: 5px;
            }

            .button-container form input[type="submit"] {
                width: 100%;
            }
        }
    </style>
</head>
<body>
    <h1>Category Management</h1>

    <div class="form-container">
        <form action="addService" method="post">
            <input type="hidden" name="request" value="category">
            <input type="text" name="category_name" placeholder="Enter the Group Name" required>
            <input type="submit" value="Add Group">
        </form>
    </div>

    <div class="category-list">
        <ul>
            <%
            user userObj = (user) request.getAttribute("CatList");
            List<String> catList = null;
            List<Integer> catidList = null;

            if (userObj != null) {
                catList = userObj.getCategory();
                catidList = userObj.getCategoryID();
            }

            if (catList != null && catidList != null && catList.size() == catidList.size()) {
                for (int i = 0; i < catList.size(); i++) {
                    String categoryName = catList.get(i);
                    Integer categoryId = catidList.get(i);
            %>
                <li>
                    <div class="category-name"><%= categoryName %></div>
                    <div class="button-container">
                        <form method="post" action="listService" style="display:inline;">
                            <input type="hidden" name="request" value="categoryMembers">
                            <input type="hidden" name="cat_id" value="<%= categoryId %>">
                            <input type="hidden" name="cat_name" value="<%= categoryName %>">
                            <input type="submit" class="view" value="View Category">
                        </form>
                        <form method="post" action="deleteService" style="display:inline;">
                            <input type="hidden" name="request" value="category">
                            <input type="hidden" name="cat_id" value="<%= categoryId %>">
                            <input type="submit" class="delete" value="Delete">
                        </form>
                    </div>
                </li>
            <%
                }
            } else {
                out.println("<li class=\"error-message\">No categories found.</li>");
            }
            %>
        </ul>
        <a href="profile.jsp">Back to Profile</a>
        <a href="success.jsp">Back to Home</a>
    </div>
</body>
</html>
