
package cl.cstit.msd.ccs.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;


public class ConnectionFactory {
	
	private static ConnectionFactory singleInstance = null;
	private static ResourceBundle propertiesList = ResourceBundle.getBundle("configFileBundle");
	private static String motorDBKeyOracle = "connect.oracle";
	private static String motorDBKeyJde    = "connect.jde";
	
	static Logger logger = Logger.getLogger(ConnectionFactory.class.getName());


	public static ConnectionFactory getInstance() {
		if (null == singleInstance) {
			singleInstance = new ConnectionFactory();
		}
		return singleInstance; 
	}
	
	public Connection getConnectionJdeMSD() throws SQLException{
		Connection con = null;
		
		String driver  = propertiesList.getString(motorDBKeyJde+".driver");
		
		String url  = propertiesList.getString(motorDBKeyJde+".url");
		String ip   = propertiesList.getString(motorDBKeyJde+".ip");
		String port = propertiesList.getString(motorDBKeyJde+".port");
		String db   = propertiesList.getString(motorDBKeyJde+".db");
		String user = propertiesList.getString(motorDBKeyJde+".user");
		String pass = propertiesList.getString(motorDBKeyJde+".pass"); 
		
		String connString = url.concat(ip).concat(port).concat(db);
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(connString,user,pass);
			
		} catch (ClassNotFoundException e) {
			logger.error(e.getMessage());
		}
		return con;
	}
	
	
	public Connection getConnectionOracleMSD() throws SQLException{
		Connection con = null;
		
		String driver  = propertiesList.getString(motorDBKeyOracle+".driver");
		
		String url  = propertiesList.getString(motorDBKeyOracle+".url");
		String ip   = propertiesList.getString(motorDBKeyOracle+".ip");
		String port = propertiesList.getString(motorDBKeyOracle+".port");
		String db   = propertiesList.getString(motorDBKeyOracle+".db");
		String user = propertiesList.getString(motorDBKeyOracle+".user");
		String pass = propertiesList.getString(motorDBKeyOracle+".pass"); 
		
		String connString = url.concat(ip).concat(port).concat(db);
		
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(connString,user,pass);
			
		} catch (ClassNotFoundException e) {
			logger.error("", e);
		}catch (Exception e) {
			logger.error("", e);
		}
		return con;
	}
	
	
	public Connection getConnectionMysql() throws SQLException{
		Connection con = null;
		
//		String driver  = propertiesList.getString(motorDBKeyOracle+".driver");
//		
//		String url  = propertiesList.getString(motorDBKeyOracle+".url");
//		String ip   = propertiesList.getString(motorDBKeyOracle+".ip");
//		String port = propertiesList.getString(motorDBKeyOracle+".port");
//		String db   = propertiesList.getString(motorDBKeyOracle+".db");
//		String user = propertiesList.getString(motorDBKeyOracle+".user");
//		String pass = propertiesList.getString(motorDBKeyOracle+".pass"); 
//		
//		String connString = url.concat(ip).concat(port).concat(db);
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost/flt","flt","asdqwe123");
			
		} catch (ClassNotFoundException e) {
			logger.error("", e);
		}catch (Exception e) {
			logger.error("", e);
		}
		return con;
	}
}

