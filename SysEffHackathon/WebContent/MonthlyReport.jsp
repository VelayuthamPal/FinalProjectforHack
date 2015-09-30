<%@page import="com.verizon.hackathon.ConnectionUtil"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="java.sql.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<style>
table, th, td {
    
    border-collapse: collapse;
}
th, td {
    padding: 15px;
}
</style>
</head>
<body>
<%
Connection con;

con=ConnectionUtil.getConnection();

PreparedStatement ps=con.prepareStatement("SELECT * FROM monthly_usage WHERE user_id=?");
ps.setInt(1, 1001);
ResultSet rs=ps.executeQuery();
%>

<table border=2 class="TFtable">
<tr><th>User Id</th>
    <th>Month</th>
    <th>CPU Usage</th>
    <th>RAM Usage</th>
    <th>HDD Usage</th>
    </tr>

<% while(rs.next()) //loop through theresult
{ %>
	<tr>
	<td><%= rs.getInt(4) %></td>
	<td><%= rs.getInt(5) %></td>
	<td><%= rs.getDouble(1) %></td>
	<td><%= rs.getDouble(2) %></td>
	<td><%= rs.getDouble(3)%></td>
    </tr>
	<% }%>

</body>
</html>