package com.rasas.DAo;

import com.rasas.entities.Users;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

@ManagedBean
@RequestScoped

public class UserDAO implements Serializable{
  
    final String URL = "jdbc:oracle:thin:@localhost:1521:XE";
    final String USER = "rasas";
    final String PASS = "123";
    
       
    
    public List<Users> getUsers() throws SQLException{
        Connection conn = DriverManager.getConnection(URL, USER, PASS); 
        try {
            
            Class.forName("oracle.jdbc.driver.OracleDriver");
        } catch (Exception ex) {
            System.out.println("in exec");
            System.out.println(ex.getMessage());
        }
        
        List<Users> usersList = new ArrayList<Users>();
        PreparedStatement sql = conn.prepareStatement("select * users");
        ResultSet rs = sql.executeQuery();
        
        
        
        
        return null;
    }
    
    
}
