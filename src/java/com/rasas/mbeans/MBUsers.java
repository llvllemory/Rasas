package com.rasas.mbeans;

import com.rasas.dao.RasasDAO;
import com.rasas.entities.Users;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.persistence.*;

@ManagedBean
@RequestScoped

public class MBUsers implements Serializable{
    
    private List<Users> usersList;
    private Users user;
    private RasasDAO rsCon;
    private Connection con;
    private PreparedStatement preparedStatement;
    private ResultSet result;
    
    
    EntityManagerFactory emf;
    EntityManager em;
    
    public MBUsers(){
        emf = Persistence.createEntityManagerFactory("RasasPU");
        em  = emf.createEntityManager();
        
    }
    
    
    ///////////////////Query methods ///////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////
    
    public List<Users> getUserByIdAndPassword(String userId, String password) throws SQLException{
        
        usersList = new ArrayList<>();
        rsCon = new RasasDAO();
        preparedStatement = null;
        result = null;
        
        try {
            
            con = rsCon.getRsConnection();
            preparedStatement = con.prepareStatement("select * from users where user_id = ? and password = ?");
            preparedStatement.setString(1, userId);
            preparedStatement.setString(2, password);
           
            result = preparedStatement.executeQuery();
            
            while(result.next()){

                user = new Users();
                
                user.setUserId(result.getString("USER_ID"));
                user.setUserName(result.getString("USER_NAME"));
                user.setUserType(result.getString("USER_TYPE"));
                user.setPassword(result.getString("PASSWORD"));
                user.setPrivilege(result.getInt("PRIVILEGE"));
                user.setEntryDate(result.getDate("ENTRY_DATE"));
                user.setLastLogin(result.getDate("LAST_LOGIN"));
                user.setUserCenter(result.getInt("USER_CENTER"));
                
                usersList.add(user);
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
        
       return usersList; 
    }
    
    ///////////////////////////////////////////////////////////////////////////
    public List<Users> getAllUsers() throws SQLException{
        
        rsCon = new RasasDAO();
        usersList = new ArrayList<>();
        preparedStatement = null;
        result = null;
         
        
        try {
            
            con = rsCon.getRsConnection();
            preparedStatement = con.prepareStatement("select * from users");
            result = preparedStatement.executeQuery();
            
            while(result.next()){
                
                user = new Users();
                
                user.setUserId(result.getString("USER_ID"));
                user.setUserName(result.getString("USER_NAME"));
                user.setUserType(result.getString("USER_TYPE"));
                user.setPassword(result.getString("PASSWORD"));
                user.setPrivilege(result.getInt("PRIVILEGE"));
                user.setEntryDate(result.getDate("ENTRY_DATE"));
                user.setLastLogin(result.getDate("LAST_LOGIN"));
                user.setUserCenter(result.getInt("USER_CENTER"));
                
                usersList.add(user);
                
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
       return usersList; 
    }  
    
    
    ////////////////////////////////////////////////////////////////////////////////

    public void updateUserLastLogin(String userId) {
        System.out.println("com.rasas.mbeans.MBUsers.updateUserLastLogin()---------->");
        
        user = new Users();
        em.getTransaction().begin();
        user = em.find(Users.class, userId);
        user.setLastLogin(new java.util.Date());
        em.getTransaction().commit();
    }
}
