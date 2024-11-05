<%@ page import="POJO.*" %>
<%@ page import="DAOs.*" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Contact Profile</title>
    <link href="https://fonts.googleapis.com/css2?family=Roboto:wght@400;700&display=swap" rel="stylesheet">
    <style>
        body {
            font-family: 'Roboto', sans-serif;
            background-color: #f4f7fa;
            color: #333;
            margin: 0;
            padding: 0;
        }

        .header {
            background-color: #007BFF;
            color: white;
            padding: 10px;
            text-align: center;
        }

        .container {
            max-width: 800px;
            margin: 20px auto;
            background-color: white;
            border-radius: 8px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            padding: 20px;
        }

        .contact-image {
            width: 150px;
            height: 150px;
            border-radius: 50%;
            margin: 0 auto 20px;
            display: block;
            background-color: #ddd;
            object-fit: cover;
        }

        h1 {
            text-align: center;
            color: #007BFF;
        }

        .contact-info {
            margin-bottom: 20px;
        }

        .contact-info p {
            margin: 10px 0;
            font-size: 1.1em;
        }

        .contact-info strong {
            display: inline-block;
            width: 120px;
            font-weight: bold;
        }

        .notes {
            background-color: #f9f9f9;
            border-radius: 5px;
            padding: 15px;
            margin-top: 20px;
        }

        .notes h2 {
            color: #007BFF;
            margin-top: 0;
        }

        .button-group {
            display: flex;
            justify-content: center;
            gap: 10px;
            margin-top: 20px;
        }

        .button {
            background-color: #007BFF;
            color: white;
            border: none;
            padding: 10px 20px;
            border-radius: 5px;
            cursor: pointer;
            font-size: 1em;
            transition: background-color 0.3s;
        }

        .button:hover {
            background-color: #0056b3;
        }

        .button.delete {
            background-color: #dc3545;
        }

        .button.delete:hover {
            background-color: #c82333;
        }
    </style>
</head>
<body>
    <div class="header">
        <h1>Contact Profile</h1>
    </div>
	<%
        contact ct = (contact) request.getAttribute("contactObj");
        %>
    <div class="container">
        <img src="https://api.dicebear.com/7.x/avataaars/svg?seed=<%= ct.getName() %>" alt="Contact Picture" class="contact-image" id="contactImage">
        <div class="contact-info">
            <p><strong>Name:</strong> <span id="contactName"><%= ct.getName() %></span></p>
            <p><strong>Email:</strong> <span id="contactEmail"><%= ct.getEmailID().get(0) %> (<%= ct.getEmailIDLabel().get(0) %>)</span></p>
            <p><strong>Phone:</strong> <span id="contactPhone"><%= ct.getPhoneNumber().get(0) %> (<%= ct.getPhoneLabel().get(0) %>) </span></p>
            <p><strong>Relationship:</strong> <span id="contactRelationship"><%= ct.getRelatedPerson() %></span></p>
            <p><strong>Home Address:</strong> <span id="contactRelationship"><%= ct.getHomeAddress() %></span></p>
            <p><strong>Work Address:</strong> <span id="contactRelationship"><%= ct.getWorkAddress() %></span></p>
        </div>

        <div class="notes">
            <h2>Notes</h2>
            <p id="contactNotes"><%= ct.getNotes() %></p>
        </div>

        <div class="button-group">
            
            <form method="post" action="listService">
            	<input type="hidden" name="request" value="contactedit">
            	<input type="hidden" name="PID" value="<%= ct.getPID() %>">
				<input  class="button" type="submit" value="Edit Contact">
            </form>
            
            <form method="post" action="deleteService">
            	<input type="hidden" name="request" value="contact">
            	<input type="hidden" name="PID" value="<%= ct.getPID() %>">
				<input class="button" style="background-color: red" type="submit" value="Delete Contact">
            </form>
            
        </div>
    </div>

</body>
</html>