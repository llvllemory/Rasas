package com.rasas.mbeans;

import com.rasas.entities.Users;
import com.sun.faces.context.SessionMap;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

@ManagedBean
@RequestScoped
public class MBLogin implements Serializable{

    private String userId;
    private String password;
    
    private MBUsers mBUsers; 
    private List<Users> usersList;
    private Users loggedUser;
    
    public MBLogin(){

    }
    
    public String logIn() throws SQLException{
             
        
        if(userId.equals("")){
            MBCommonMethods.getWarnMessage("تنبيه!", "يجب ادخال اسم المستخدم");
            return "";
        }
        
        if(password.equals("")){
            MBCommonMethods.getWarnMessage("تنبيه!", "يجب ادخال كلمة السر");
            return "";
        }
          
        mBUsers = new MBUsers();
        usersList = mBUsers.getUserByIdAndPassword(userId, password);

        if(usersList.size() > 0){
            
            
            mBUsers = new MBUsers();
            mBUsers.updateUserLastLogin(userId);
                    
            MBCommonMethods.getInfoMessage("", "أهلا بك " + usersList.get(0).getUserName());
            MBCommonMethods.setLoggedUser((Users) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("0"));
            
            return "main_page";
            
        }else{
            MBCommonMethods.getErrorMessage("خظأ", "خطأ في اسم المستخدم او كلمة السر!");
            return "";    
        }
    }
    
    
    public void logOut(){
        
    }
    
    
    
    ///////////////
   
   
    
    ////////////////////////////////////////////////////////////////////////////
    public String getUserId() {
        return userId;
    }

    public void setUserId(String username) {
        this.userId = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    } 

    public Users getLoggedUser() {
        return loggedUser;
    }

    public void setLoggedUser(Users loggedUser) {
        this.loggedUser = loggedUser;
    }
    
    
}
