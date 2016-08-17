package com.rasas.dao;

import com.rasas.connections.RsConnection;
import com.rasas.entities.Users;
import java.sql.Connection;
import java.sql.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.faces.bean.ManagedBean;

@ManagedBean
public class UsersDAO {
    
    private Users user = new Users();
    private RsConnection rsCon = new RsConnection();
    private Connection con;
  
    public Users getUserById(String userId) throws SQLException{
        
        PreparedStatement statement = null;
        ResultSet result = null;
        
        try {
            
            con = rsCon.getRsConnection();
            statement = con.prepareStatement("select * from users where user_id = ?");
            statement.setString(1, userId);
            result = statement.executeQuery();
            
            while(result.next()){
        
                user.setUserId(result.getString("USER_ID"));
                user.setPassword(result.getString("USER_NAME"));
                user.setUserName(result.getString("USER_TYPE"));
                user.setPassword(result.getString("PASSWORD"));
                user.setPrivilege(result.getInt("PRIVILEGE"));
                user.setEntryDate(result.getDate("ENTRY_DATE"));
                user.setLastLogin(result.getDate("LAST_LOGIN"));
                user.setUserCenter(result.getInt("USER_CENTER"));
            }
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
	} finally {
            if (statement != null) {
		statement.close();
		}
            
            if (con != null) {
		con.close();
		}
	}
        return user;
    }  
}
