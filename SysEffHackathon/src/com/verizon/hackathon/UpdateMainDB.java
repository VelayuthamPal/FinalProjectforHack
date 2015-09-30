package com.verizon.hackathon;

import static com.sun.jna.platform.win32.WinReg.HKEY_LOCAL_MACHINE;

import java.io.File;
import java.lang.management.ManagementFactory;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;

import com.sun.jna.platform.win32.Advapi32Util;
import com.sun.management.OperatingSystemMXBean;

public class UpdateMainDB {
	
	static int r=0;
	static int s=0;

	public static void insertData(double cpuUsage, double ramUsage, double hdPercent, int user_id) {

		Connection con;
		con = ConnectionUtil.getConnection();
		PreparedStatement ps;

		try {
			ps = con.prepareStatement("INSERT INTO coll_usage VALUES (?,?,?,?,?)");
			ps.setDouble(1, cpuUsage);

			ps.setDouble(2, (100.00 - ramUsage));

			ps.setDouble(3, hdPercent);

			ps.setInt(4, 1001);

			java.util.Date utilDate = new Date();

			java.sql.Date date = new java.sql.Date(utilDate.getTime());

			ps.setDate(5, date);

			r=ps.executeUpdate();

			ps.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void insertSysInfo(int userId, String processorType, double ramCapacity, double hdSpace) {

		Connection con;
		con = ConnectionUtil.getConnection();
		PreparedStatement ps;

		String CPUprocessor = Advapi32Util.registryGetStringValue(HKEY_LOCAL_MACHINE,
				"HARDWARE\\DESCRIPTION\\System\\CentralProcessor\\0\\", "ProcessorNameString");

		try {
			ps = con.prepareStatement("INSERT INTO user_details VALUES (?,?,?,?)");
			ps.setInt(1, 1001);

			ps.setString(2, CPUprocessor);

			ps.setDouble(3, ramCapacity);

			ps.setDouble(4, hdSpace);

			s=ps.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		OperatingSystemMXBean bean = (com.sun.management.OperatingSystemMXBean) ManagementFactory
				.getOperatingSystemMXBean();

		double freeSpace = 0;
		double totalSpace = 0;

		while (true) {

			try {
				Thread.sleep(5000);

				freeSpace = 0;
				totalSpace = 0;

				double cpuUsage = bean.getSystemCpuLoad() * 100;
				System.out.println("New Value: " + cpuUsage);

				double ramUsage = (bean.getTotalPhysicalMemorySize() - bean.getFreePhysicalMemorySize()) / 1000000000;
				System.out.println(ramUsage);

				double ramEff = (ramUsage / (bean.getTotalPhysicalMemorySize() / 1000000000)) * 100;
				System.out.println(ramEff);

				File[] drives = File.listRoots();
				if (drives != null && drives.length > 0) {
					for (File aDrive : drives) {
						System.out.println(aDrive);

						freeSpace += aDrive.getFreeSpace() / 1000000000;
						totalSpace += aDrive.getTotalSpace() / 1000000000;
					}
				}

				// System.out.println("Free Space " + freeSpace);
				// System.out.println("Total Space " + totalSpace);

				double hdPercent = ((totalSpace - freeSpace) / totalSpace) * 100;

				System.out.println(bean.getSystemCpuLoad() * 100);
				System.out.println(ramUsage);
				System.out.println(hdPercent);

				int userId = 1001;

				insertData(cpuUsage, ramUsage, hdPercent, userId);

				String CPUprocessor = Advapi32Util.registryGetStringValue(HKEY_LOCAL_MACHINE,
						"HARDWARE\\DESCRIPTION\\System\\CentralProcessor\\0\\", "ProcessorNameString");

				double ramCapacity = bean.getTotalPhysicalMemorySize() / 1000000000;

				insertSysInfo(userId, CPUprocessor, ramCapacity, totalSpace);
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

	}

}


