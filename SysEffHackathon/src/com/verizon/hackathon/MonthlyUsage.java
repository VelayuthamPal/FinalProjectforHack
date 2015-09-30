package com.verizon.hackathon;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/MonthlyUsage")
public class MonthlyUsage extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public MonthlyUsage() {
        super();
    }

    Connection con;
	int i=0;
	double cpuMonthly=0;
	double ramMonthly=0;
	double hddMonthly=0;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	
		con=ConnectionUtil.getConnection();
		response.setContentType("text/html");
		
		try {
			PreparedStatement ps=con.prepareStatement("SELECT * FROM COLL_USAGE WHERE to_char(DATE_OF_USAGE,'Month')=to_char(sysdate,'Month')AND USER_ID=?");
			ps.setInt(1, 1001);
			
			ResultSet rs=ps.executeQuery();
			
			while(rs.next()){
				i++;
				cpuMonthly+=rs.getDouble(1);
				ramMonthly+=rs.getDouble(2);
				hddMonthly+=rs.getDouble(3);
			}
			
			cpuMonthly=cpuMonthly/i;
			ramMonthly=ramMonthly/i;
			hddMonthly=hddMonthly/i;
			
			System.out.println(cpuMonthly);
			System.out.println(ramMonthly);
			System.out.println(hddMonthly);
			
			rs.close();
			ps.close();
			
			java.util.Date utilDate = new Date();

			java.sql.Date date = new java.sql.Date(utilDate.getTime());
			
			

			ps=con.prepareStatement("INSERT INTO Monthly_usage VALUES(?,?,?,?,?)");
			
			ps.setDouble(1, cpuMonthly);
			ps.setDouble(2, ramMonthly);
			ps.setDouble(3, hddMonthly);
			ps.setDouble(4, 1001);
			ps.setInt(5, date.getMonth());
			
			ps.executeQuery();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
