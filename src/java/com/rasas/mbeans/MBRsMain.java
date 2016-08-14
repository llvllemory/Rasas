package com.rasas.mbeans;

import com.rasas.entities.RsMain;
import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.persistence.*;

@ManagedBean
@RequestScoped

public class MBRsMain implements Serializable{
    private String centerNo;
    private int rsFrom;
    private int rsTo;
    private int rsFound = 0;
    
    EntityManagerFactory emf;
    EntityManager em;
    
    public MBRsMain(){
        emf = Persistence.createEntityManagerFactory("RasasPU");
        em  = emf.createEntityManager();
        em.getTransaction().begin();   
    }
    
    public String regRasas(){
        
        if(rsFrom == 0){
            MBCommonMethods.getWarnMessage("إنتبه", "بداية الرصاص يجب ان يكون اكبر من صفر!");
            return "";
        }
        
        if(rsTo == 0){
            MBCommonMethods.getWarnMessage("إنتبه", "نهاية الرصاص يجب ان يكون اكبر من صفر!");
            return "";
        }
        
        if(rsTo < rsFrom){
            MBCommonMethods.getWarnMessage("إنتبه", "يجب ان يكون بداية الرصاص اقل او يساوي نهايته!");
            return "";
        }
        
        
        try {
            
            List<RsMain> rs = em.createNamedQuery("RsMain.findByRsYear", RsMain.class).setParameter("rsYear", MBCommonMethods.getCurrentYear()).getResultList();
       
            if(rs != null){
                
                rsFound = 0;
                for(RsMain r: rs){
                    for(int i = rsFrom; i <= rsTo; i++){
                        if(Integer.valueOf(r.getRsNo()).equals(i)){
                            rsFound++;
                        }
                    }
                }
                
                
                if(rsFound > 0){
                    System.out.println("MBRsMain.RegRasas() -------- > rsFound > 0");
                    MBCommonMethods.getErrorMessage("خطأ", "هنالك رصاص مصروف من هذا الرصاص, الرجاء التأكد والمحاولة مرة اخرى.");
                }else{
                    ///// insert list of rsmain
                    System.out.println("MBRsMain.RegRasas() -------- > rsFound < 0");
                    MBCommonMethods.getInfoMessage("نجحت العملية", "تم صرف الرصاص للمركز ينجاح.");      
                }
            }else{
                ///// insert list of rsmain
                MBCommonMethods.getInfoMessage("نجحت العملية", "تم صرف الرصاص للمركز ينجاح.");      
            }
            return "";
        } catch (Exception e) {
            System.out.println("MBRsMain.RegRasas() -------- > " + e.getMessage());
            return "";
        }
    }

    
    public String getCenterNo() {
        return centerNo;
    }

    public void setRsFrom(String centerNo) {
        this.centerNo = centerNo;
    }
    
    public int getRsFrom() {
        return rsFrom;
    }

    public void setRsFrom(int rsFrom) {
        this.rsFrom = rsFrom;
    }

    public int getRsTo() {
        return rsTo;
    }

    public void setRsTo(int rsTo) {
        this.rsTo = rsTo;
    }
    
    
}
