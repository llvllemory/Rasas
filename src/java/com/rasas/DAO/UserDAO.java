package com.rasas.DAO;

import com.rasas.connections.RsConnection;
import com.rasas.entities.Users;
import java.io.Serializable;
import java.math.BigInteger;
import java.sql.Connection;
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
  
    private String sql = "";
    private RsConnection rsCon;
    private Connection con = rsCon.getRsConnection();
    List<Users> userList = new ArrayList<>();
    Users user = new Users();

    public UserDAO() throws Exception {
        this.rsCon = new RsConnection();
    }
    
    public Users getUserById(String user_id) throws Exception{
        
        sql = "select * from users where user_id = 33476";
        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        
        while(rs.next()){
            
            user.setUserId(rs.getString("USER_ID"));
            user.setPassword(rs.getString("PASSWORD"));
            user.setUserName(rs.getString("USER_NAME"));
            user.setUserType(rs.getString("USER_TYPE"));
            user.setPrivilege(BigInteger.valueOf(rs.getInt("PRIVILEGE")));
            user.setUserCenter(BigInteger.valueOf(rs.getInt("USER_CENTER")));
            user.setEntryDate(rs.getDate("ENTRY_DATE"));
            user.setLastLogin(rs.getDate("LAST_LOGIN"));
            
        }
        
        System.out.println("------------------------------------------->" + user);
        con.close();
        return user;
    }
    
    
    public List<Users> getAllUsers() throws Exception{
        
        Connection con = rsCon.getRsConnection();
        sql = "select * from users";
        PreparedStatement ps = con.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        
        while(rs.next()){
            user.setUserId(rs.getString("USER_ID"));
            user.setPassword(rs.getString("PASSWORD"));
            user.setUserName(rs.getString("USER_NAME"));
            user.setUserType(rs.getString("USER_TYPE"));
            user.setPrivilege(BigInteger.valueOf(rs.getInt("PRIVILEGE")));
            user.setUserCenter(BigInteger.valueOf(rs.getInt("USER_CENTER")));
            user.setEntryDate(rs.getDate("ENTRY_DATE"));
            user.setLastLogin(rs.getDate("LAST_LOGIN"));
            
            userList.add(user);
        }
        con.close();
        return userList;
    }    
}
