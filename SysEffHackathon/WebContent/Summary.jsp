<%@page import="com.verizon.hackathon.ConnectionUtil"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="java.sql.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<body>
<%
Connection con;
con=ConnectionUtil.getConnection();

double cpuDaily=0;
double ramDaily=0;
double hddDaily=0;
String status;
double rating;


PreparedStatement ps=con.prepareStatement("SELECT * FROM daily_usage WHERE user_id=1001");
ResultSet rs=ps.executeQuery();
while(rs.next()){
	cpuDaily=rs.getDouble(1);
	ramDaily=rs.getDouble(2);
	hddDaily=rs.getDouble(3);
	
	break;

}

if(cpuDaily<30){
	status="Idle";
	rating=3;
}
	
	else if(cpuDaily<50){
		status="Average";
		rating=4.5;
	}
	else if(cpuDaily<65){
		status="Good";
		rating=7;
	}
	else if(cpuDaily<75){
		status="Excellent";
		rating=10;
	}
	else if(cpuDaily<90){
		status="Heavy";
		rating=6.5;
	}
	else{
		status="OverLoad";
		rating=5;
	}
System.out.println(status);

%>
<div>

<p>
<%
if(status=="idle"){
out.println("possible reduce of cpu resource");}
if(status=="OverLoad"){
out.println("possible upgrade of cpu resource");}%>
</p>
</div>
<%if(ramDaily<30){
	status="Idle";
	rating+=3;
}
	
	else if(ramDaily<50){
		status="Average";
		rating+=4.5;
	}
	else if(ramDaily<65){
		status="Good";
		rating+=7;
	}
	else if(ramDaily<75){
		status="Excellent";
		rating+=10;
	}
	else if(ramDaily<90){
		status="Heavy";
		rating+=6.5;
	}
	else{
		status="OverLoad";
		rating+=5;
	}
 %>
 

 <%
 if(status=="idle"){
 out.println("possible reduce of RAM resource");}
 if(status=="OverLoad"){
 out.println("possible upgrade of RAM");}%>
<br>

<%if(hddDaily<30){
	status="Idle";
	rating+=3;
}
	
	else if(hddDaily<50){
		status="Average";
		rating+=4.5;
	}
	else if(hddDaily<65){
		status="Good";
		rating+=7;
	}
	else if(hddDaily<75){
		status="Excellent";
		rating+=10;
	}
	else if(hddDaily<90){
		status="Heavy";
		rating+=6.5;
	}
	else{
		status="OverLoad";
		rating+=5;
	}
 %>
 
 <% if(status=="idle"){
 out.println("possible reduce of RAM resource");}
 if(status=="OverLoad"){
 out.println("possible upgrade of RAM resource");}%>
 
<%double avgRating=rating/3;
 if(avgRating<4){
 out.println("poor usage of the system resources");}
 if(avgRating>8.5){
 out.println("possible upgrade of all the resources");}%>
</body>
</html>