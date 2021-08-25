package com.bookapp.dao;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class ModelDAO {

	static Connection connection;
	
	public static Connection openConnection() {
		Properties properties=new Properties();
		try {
			properties.load(new FileReader("jdbc.properties"));
		}catch(FileNotFoundException e1){
				e1.printStackTrace();
			}catch (IOException e1) {
				e1.printStackTrace();
			}
        //String drivername="com.db.cj.jdbc.Driver";
		String url= (String) properties.get("driver");
		String username=(String) properties.get("username");
		String password= (String) properties.get("password");
		connection = null;
		
		try {
			//Class.forName(drivername);
			connection=DriverManager.getConnection(url, username,password);
			
		}catch(SQLException e) {
			  e.printStackTrace();
		}
		return connection;
		}
		public static void closeConnection() {
			try {
				if(connection!=null)
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}
}
