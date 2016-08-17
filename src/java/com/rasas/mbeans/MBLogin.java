package com.rasas.mbeans;

import com.rasas.entities.Users;
import java.sql.SQLException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

@ManagedBean
@RequestScoped

public class MBLogin {

    private String userId;
    private String password;
    private MBUser userManager;    
    private Users user;
    
    public MBLogin(){
        
    }

    public String checkLoginCredentials() throws SQLException{
     
        userManager = new MBUser();
        user = new Users();
        
        user = userManager.getUserByIdAndPassword(userId, password);
        if(user != null){
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
