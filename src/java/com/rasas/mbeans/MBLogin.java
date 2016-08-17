package com.rasas.mbeans;

import com.rasas.entities.Users;
import java.io.Serializable;
import java.sql.SQLException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

@ManagedBean
@RequestScoped

public class MBLogin implements Serializable{

    private String userId;
    private String password;
    private MBUser userManager;    
    private Users user;
    
    public MBLogin(){

    }

    public String checkLoginCredentials() throws SQLException{
        
        if(userId.equals(null)){
            MBCommonMethods.getWarnMessage("تنبيه!", "يجب ادخال اسم المستخدم");
        }
        
        if(password.equals(null)){
            MBCommonMethods.getWarnMessage("تنبيه!", "يجب ادخال كلمة السر");
        }
        
        
        
        userManager = new MBUser();
        user = new Users();
        
        System.out.println("username, password" + userId + ", " + password);
        user = userManager.getUserByIdAndPassword(userId, password);

        if(user != null){
            System.out.println("zzzzzzzzzwwwwwwwwwwzz" + user.getUserId());
            return "main_page";
        }
        MBCommonMethods.getErrorMessage("خظأ", "خطأ في اسم المستخدم او كلمة السر!");
        return "";
    }
    
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
