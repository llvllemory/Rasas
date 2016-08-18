package com.rasas.mbeans;

import com.rasas.entities.Users;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

@ManagedBean
@RequestScoped
public class MBLogin implements Serializable{

    private String userId;
    private String password;
    
    private MBUser userManager; 
    private Users user;
    private List<Users> usersList = new ArrayList<>();
    
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
          
        userManager = new MBUser();
        user = new Users();
        
        user = userManager.getUserByIdAndPassword(userId, password);

        if(user.getUserId() != null){
            MBCommonMethods.getInfoMessage("", "أهلا بك " + user.getUserName());
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
}
