package org.myshop.shop.dao.jdbc.connection;

import java.sql.*;

public class Connector {
	
	private static String url = "jdbc:mysql://127.0.0.1:3306/?user=root";      
    private static String username = "root";   
    private static String password = "warfare1944";
    private static Connection con;
    private static String driverName= "com.mysql.jdbc.Driver";

    public Connection getConnection() {
        
            try {
            	Class.forName(driverName);
                con = DriverManager.getConnection(url, username, password);
            } catch (SQLException e) {
         
               return null;
            }     
            catch(ClassNotFoundException e){
            	return null;
            }
            
            return con;
    }
}
