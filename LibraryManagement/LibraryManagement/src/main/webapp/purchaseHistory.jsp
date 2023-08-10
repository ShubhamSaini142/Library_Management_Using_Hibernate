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
</head>
<body>
	<div class="container">
		<h1>Book Purchase History</h1>
		<table>
			<tr>
				<th>Book ID</th>
				<th>Book Name</th>
				<th>Book Author</th>
				<th>Book Cost</th>
				<th>Book Category</th>
			</tr>
			<% 
            String url = "jdbc:mysql://localhost:3306/library";
            String user = "root";
            String pwd = "root";

            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection con = DriverManager.getConnection(url, user, pwd);
                Statement pstmt = con.createStatement();
                ResultSet resultSet = pstmt.executeQuery("SELECT * FROM book");

                while (resultSet.next()) { %>
			<tr>
				<td><%= resultSet.getInt(1) %></td>
				<td><%= resultSet.getString(2) %></td>
				<td><%= resultSet.getString(3) %></td>
				<td><%= resultSet.getInt(4) %></td>
				<td><%= resultSet.getString(5) %></td>
			</tr>
			<% }
                con.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            %>
		</table>
		

		<br> <a href="adminHome.html">Go To Home</a>
	</div>
</body>
</html>
