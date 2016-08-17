package com.rasas.connections;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

@ManagedBean
public class RsConnection{
    
    private final String DB_DRIVER = "oracle.jdbc.driver.OracleDriver";
    private final String DB_CONNECTION = "jdbc:oracle:thin:@localhost:1521:XE";
    private final String DB_USER = "rasas";
    private final String DB_PASSWORD = "123";
    private Connection rsConnection = null;
    
    public RsConnection(){
        
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

    public Connection getRsConnection() {
        return rsConnection;
    }
    
    public void rsConnectionClose() throws SQLException{
        rsConnection.close();
    }
    
}
