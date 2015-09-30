package com.verizon.hackathon;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionUtil {
	
static java.sql.Connection con;
	
	public static Connection getConnection(){
		try{
			Class.forName("oracle.jdbc.driver.OracleDriver");
			if(con==null){
				con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "system", "password");
				return con;
			}
			
		}catch(ClassNotFoundException | SQLException e ){
			e.printStackTrace();
		}
		return con;
		
			
	}

	@Override
	protected void finalize() throws Throwable {
		// TODO Auto-generated method stub
		super.finalize();
	}


}
