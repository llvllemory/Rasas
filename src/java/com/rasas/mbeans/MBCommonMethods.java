package com.rasas.mbeans;

import com.rasas.entities.Users;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.primefaces.context.RequestContext;


@ManagedBean
@RequestScoped
public class MBCommonMethods implements Serializable{
     
   public MBCommonMethods(){
       
   }
   
   public static void getErrorMessage(String messageLabel, String message){
       RequestContext.getCurrentInstance().update("growl");
       FacesContext fc = FacesContext.getCurrentInstance();
       
       fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, messageLabel, message));
   }
   
   public static void getInfoMessage(String messageLabel, String message){
       RequestContext.getCurrentInstance().update("growl");
       FacesContext fc = FacesContext.getCurrentInstance();
       
       fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, messageLabel, message));
   }
   
   public static void getFatalMessage(String messageLabel, String message){
       RequestContext.getCurrentInstance().update("growl");
       FacesContext fc = FacesContext.getCurrentInstance();
       
       fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, messageLabel, message));
   }
   
   public static void getWarnMessage(String messageLabel, String message){
       RequestContext.getCurrentInstance().update("growl");
       FacesContext fc = FacesContext.getCurrentInstance();
       
       fc.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, messageLabel, message));
   }
   
   public static String getCurrentDate(){
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        return dateFormat.format(cal.getTime());
   }
   
   public static String getCurrentYear(){
        DateFormat dateFormat = new SimpleDateFormat("yyyy");
        Calendar cal = Calendar.getInstance();
        return dateFormat.format(cal.getTime());
   } 
   
   /////////////////////////////////////////////////////////////////////////////
   
    public static void userLogin(Users user){
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("user", user);

    }
    
    public static Users getLoggedUser(){
        return (Users) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("user");
    }
    
    public static String userLogout(){
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "login_page";
    }
   
}
