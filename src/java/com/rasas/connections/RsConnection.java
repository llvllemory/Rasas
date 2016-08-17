package com.rasas.connections;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

@ManagedBean
@RequestScoped
public class RsConnection{
    
    private final String URL = "jdbc:oracle:thin:@localhost:1521:XE";
    private final String USER = "rasas";
    private final String PASS = "123";
    
    private Connection rsConnection;
    
    public RsConnection() throws Exception{
       
        try {
            rsConnection = DriverManager.getConnection(URL, USER, PASS);
            System.out.println("com.rasas.connections.RsConnection.<init>() ---------------------- > connection established" );
        } catch (Exception e) {
            System.out.println("com.rasas.connections.RsConnection.<init> ----------------> " + e.getMessage());
        }
        
        
    }

    public Connection getRsConnection() {
        return rsConnection;
    }

    public void setRsConnection(Connection rsConnection) {
        this.rsConnection = rsConnection;
    }
    
    public void rsConnectionClose() throws SQLException{
        rsConnection.close();
    }
    
}
