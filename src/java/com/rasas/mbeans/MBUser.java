package com.rasas.mbeans;

import com.rasas.entities.Users;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.persistence.*;

@ManagedBean
@RequestScoped

public class MBUser implements Serializable{
    
    EntityManagerFactory emf;
    EntityManager em;
    
    public MBUser(){
        emf = Persistence.createEntityManagerFactory("RasasPU");
        em  = emf.createEntityManager();
        em.getTransaction().begin();
    }
    
    public boolean checkUsernameAndPassword(String username, String password){
        
        try {
            Users u = em.createNamedQuery("Users.chkUserCredentials", Users.class)
                    .setParameter("userId", username).setParameter("password", password).getSingleResult();
          
            if(u != null){
                System.out.println("MBUser().chkUser  if(u != null) --->");
                return true;
            }
            return false;
        } catch (Exception e) {
            System.out.println("MBUser().chkUser -- e.getMessage() ---> " + e.getMessage());
            return false;
        }  
    }
}
