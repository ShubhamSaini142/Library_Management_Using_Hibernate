<%@ page import="java.sql.ResultSet"%>

<%@ page import="java.sql.Statement"%>

<%@ page import="java.sql.DriverManager"%>

<%@ page import="java.sql.Connection"%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html>

<html>

<head>

<meta charset="UTF-8">

<title>Transaction History</title>

<link rel="stylesheet" type="text/css" href="sty.css">
    <style>
        body {
            margin: 0;
            font-family: Arial, sans-serif;
            background-color: #f4f4f4; /* Light gray background */
            color: #333; /* Dark gray text color */
        }

        form {
            max-width: 400px;
            margin: 0 auto;
            padding: 20px;
            background-color: #fff; /* White background */
            border-radius: 8px;
  
        }

        h2 {
            text-align: center;
            margin-bottom: 30px;
            color: #333; /* Dark gray heading color */
        }

        label {
            font-weight: bold;
            color: #555; /* Medium gray label text color */
        }

        input[type="text"] {
            width: 100%;
            padding: 10px;
            border: 1px solid #ccc;
            border-radius: 4px;
            margin-bottom: 20px;
        }

        button {
            background-color: #008080; /* Dark teal button background color */
            color: #fff; /* White button text color */
            border: none;
            border-radius: 4px;
            padding: 12px 20px;
            font-size: 16px;
            cursor: pointer;
            transition: background-color 0.3s;
            width: 100%;
        }

        button:hover {
            background-color: #006666; /* Lighter teal on hover */
        }

        .home-link {
            text-align: center;
            margin-top: 20px;
            display: block;
            color: #008080; /* Dark teal link color */
            text-decoration: none;
        }

        .home-link:hover {
            color: #006666; /* Lighter teal link color on hover */
        }

    </style>

</head>

<body>

	<div class="container">

		<h1>Select User to Remove</h1>

		<table>

			<tr>

				<th>User ID</th>

				<th>User Name</th>

				<th>User Phone</th>

				<th>User Email</th>

				<th>User Status</th>

			</tr>

			<%
			String url = "jdbc:mysql://localhost:3306/library";

			String user = "root";

			String pwd = "root";

			try {

				Class.forName("com.mysql.cj.jdbc.Driver");

				Connection con = DriverManager.getConnection(url, user, pwd);

				Statement pstmt = con.createStatement();

				ResultSet resultSet = pstmt.executeQuery("SELECT * FROM user");

				while (resultSet.next()) {
			%>

			<tr>

				<td><%=resultSet.getInt(1)%></td>

				<td><%=resultSet.getString(2)%></td>

				<td><%=resultSet.getString(4)%></td>
				<td><%=resultSet.getString(5)%></td>

				<td><%=resultSet.getInt(6)%></td>


			</tr>

			<%
			}

			con.close();

			} catch (Exception e) {

			e.printStackTrace();

			}
			%>

		</table>
		 <form class="form-horizontal" role="form" action="RemoveUser" method="post">
		<label>User Id</label><br>
		<input type="text" name="user_id"><br><br>
        <button type="submit" class="btn btn-primary btn-dark">Remove</button>
    </form>
  
    <!-- Bootstrap JS (optional) -->
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>



		<br> <a href="adminHome.html">Go To Home</a>

	</div>

</body>

</html>