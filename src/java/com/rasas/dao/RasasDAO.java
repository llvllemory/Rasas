package com.rasas.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.faces.bean.ManagedBean;

@ManagedBean

public class RasasDAO {
    
    private static final String DB_DRIVER = "oracle.jdbc.driver.OracleDriver";
    private static final String DB_CONNECTION = "jdbc:oracle:thin:@localhost:1521:XE";
    private static final String DB_USER = "rasas";
    private static final String DB_PASSWORD = "123";
    private static Connection rsConnection = null;
    
    public RasasDAO(){
        
        try {
            Class.forName(DB_DRIVER);
            System.out.println("------------> JDBC Class Driver Registered!");
        } catch (ClassNotFoundException e) {
            System.out.println("------------> JDBC Class Driver Failed To Register!");
            System.out.println(e.getMessage());
        }
        
        try {
            rsConnection = DriverManager.getConnection(DB_CONNECTION, DB_USER,DB_PASSWORD);
            System.out.println("------------> DataBase Connection Established!");
        } catch (SQLException e) {
            System.out.println("------------> DataBase Connection Failed!");
            System.out.println(e.getMessage());
        }
    }
    
    public static Connection getRsConnection() {
        return rsConnection;
    }
    
    public static void rsConnectionClose() throws SQLException{
        rsConnection.close();
    }
}
