package com.rasas.mbeans;

import com.rasas.DAO.UserDAO;
import com.rasas.entities.Users;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.persistence.PersistenceContext;


@ManagedBean
@RequestScoped

public class MBLogin {

    private String userId;
    private String password;
    private MBUser userManager;
    
    public MBLogin(){
        
    }
   
    public String checkCredentials() throws Exception{
        
        userManager = new MBUser();
        
        if(userManager.checkUsernameAndPassword(userId, password)){
            return "main_page";
        }
        
        MBCommonMethods.getErrorMessage("خظأ", "خطأ في اسم المستخدم او كلمة السر!");
        return "";
    }
    
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
