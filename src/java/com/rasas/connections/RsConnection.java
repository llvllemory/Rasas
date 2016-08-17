package com.rasas.connections;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

@ManagedBean
@RequestScoped
public class RsConnection{
    
    private static final String DB_DRIVER = "oracle.jdbc.driver.OracleDriver";
    private static final String DB_CONNECTION = "jdbc:oracle:thin:@localhost:1521:XE";
    private static final String DB_USER = "rasas";
    private static final String DB_PASSWORD = "123";
    private static Connection rsConnection = null;
    
    public RsConnection(){
        
        try {
            
            Class.forName(DB_DRIVER);
            rsConnection = DriverManager.getConnection(DB_CONNECTION, DB_USER,DB_PASSWORD);
        
        } catch (ClassNotFoundException | SQLException e) {
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
