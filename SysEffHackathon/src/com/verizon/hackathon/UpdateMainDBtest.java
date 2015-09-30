package com.verizon.hackathon;

import java.sql.Connection;
import java.sql.PreparedStatement;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;





public class UpdateMainDBtest {

	UpdateMainDB b;
	
	@Before
	public void setUp() throws Exception {
	b = new UpdateMainDB();
	
	}

	@After
	public void tearDown() throws Exception {
 
		//pst.deleteData(100003 );
		Connection con = ConnectionUtil.getConnection();
		System.out.println("Connection established TEAR");
		PreparedStatement ps = con.prepareStatement("delete from coll_usage where user_id = 1003");
		PreparedStatement pd = con.prepareStatement("delete from user_details where user_id=1003");
		ps.executeUpdate();
		pd.executeUpdate();
	}

	@Test
	public void testInsertData() {
		
		
		b.insertData(10.33, 94.00, 22.40, 1003);	
		Assert.assertEquals(1, b.r);
		}

	@Test
	public void testInsertSysInfo(){
		
		b.insertSysInfo(1003, "i5 OctaCore", 4, 120);
		
		Assert.assertEquals(1, b.s);
	}
}
