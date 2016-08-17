package com.rasas.dao;

import com.rasas.connections.RsConnection;
import com.rasas.entities.Users;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

@ManagedBean
@RequestScoped
public class UsersDAO {
    
    private RsConnection rsCon;
    private Users user = new Users();
    
    private PreparedStatement saveStmt;
    private PreparedStatement loadStmt;
    private ResultSet result;
  
    public Users getUserById(String userId) throws SQLException{
        
        user = null;
        loadStmt = rsCon.getRsConnection().prepareStatement("SELECT * FROM USERS WHERE USER_ID + ?");
        result = loadStmt.executeQuery();
        
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
        return user;
    }  
}
