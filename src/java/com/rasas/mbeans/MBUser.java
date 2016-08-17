package com.rasas.mbeans;

import com.rasas.dao.RasasDAO;
import com.rasas.entities.Users;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

@ManagedBean
@RequestScoped

public class MBUser implements Serializable{
    
    private Users user = new Users();
    private RasasDAO rsCon = new RasasDAO();
    private Connection con;
    private PreparedStatement preparedStatement;
    private ResultSet result;
    
    public MBUser(){

    }

    public Users getUserByIdAndPassword(String userId, String password) throws SQLException{
        
        preparedStatement = null;
        result = null;
        
        try {
            
            con = rsCon.getRsConnection();
            preparedStatement = con.prepareStatement("select * from users where user_id = ? and password = ?");
            preparedStatement.setString(1, userId);
            preparedStatement.setString(2, password);
           
            result = preparedStatement.executeQuery();
            
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
            if (preparedStatement != null) {
		preparedStatement.close();
		}
            
            if (con != null) {
		con.close();
		}
	}
        return user;
    }  
}
