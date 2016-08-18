package com.rasas.mbeans;

import com.rasas.dao.RasasDAO;
import com.rasas.entities.RsMain;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

@ManagedBean
@RequestScoped
public class MBRsMain implements Serializable{
    private String centerNo;
    private int rsFrom;
    private int rsTo;
    private int rsFound = 0;
    
    private RasasDAO rsCon = new RasasDAO();
    private Connection con;
    private RsMain   rasas = new RsMain();
    private PreparedStatement preparedStatement;
    private ResultSet result;

    
    public MBRsMain(){
 
    }

    public String checkRasasIfFound(){
        
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
        
        
        List<RsMain> rsList = getRasasListByCenterYearFromTo(centerNo, MBCommonMethods.getCurrentYear(), Integer.toString(rsFrom), Integer.toString(rsTo));
        
        if(rsList != null){
            
            rsFound = 0;
            for(RsMain rs : rsList){
              for(int i = rsFrom; i <= rsTo; i++){
                  if(Integer.valueOf(rs.getRsNo()).equals(i)){
                      rsFound++;
                  }
              }  
            }
            
            if (rsFound > 0) {
                System.out.println("MBRsMain.RegRasas() --------> rsFound > 0");
                MBCommonMethods.getErrorMessage("خطأ", "هنالك رصاص مصروف من هذا الرصاص, الرجاء التأكد والمحاولة مرة اخرى.");
            } else {
                ///// insert list of rsmain
                System.out.println("MBRsMain.RegRasas() --------> rsFound < 0");
                MBCommonMethods.getInfoMessage("نجحت العملية", "تم صرف الرصاص للمركز ينجاح.");
            }
        } else {
            ///// insert list of rsmain
            MBCommonMethods.getInfoMessage("نجحت العملية", "تم صرف الرصاص للمركز ينجاح.");
        }
        return "";
    }
    
    
    ////////////////////////////////////////////////////////////////////////////
    public List<RsMain> getRasasListByCenterYearFromTo(String rs_center, String rs_year, String rs_from, String rs_to){
        List<RsMain> rasasList = new ArrayList<>();
        
        preparedStatement = null;
        result            = null;
        
        try {
            
            String sql = "select * from rs_main where rs_center = ? and rs_year = ? and rs_no between ? and ? ";

            con = rsCon.getRsConnection();
            preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, rs_center);
            preparedStatement.setString(2, rs_year);
            preparedStatement.setString(3, rs_from);
            preparedStatement.setString(4, rs_to);
           
            result = preparedStatement.executeQuery();
            
            while(result.next()){
                rasas.setRsNo(result.getString("RS_NO"));
                rasas.setRsYear(result.getString("RS_YEAR"));
                rasas.setRsCenter(result.getString("RS_CENTER"));
                rasas.setRsSubCenter(result.getString("RS_SUB_CENTER"));
                rasas.setEntryDate(result.getDate("ENTRY_DATE"));
                rasas.setUserId(result.getString("USER_ID"));
                
                rasasList.add(rasas);
            }
            
        } catch (Exception e) {
            System.out.println("com.rasas.mbeans.MBRsMain.getRasasList()" + e.getMessage());
        }
        
        return rasasList;
    }
    

    
    
    public int saveRasas(String rsFrom, String rsTo, String rsCenter, String rsSubCenter, Date entryDate, String userId){
        int rowInserted = 0;
        
        preparedStatement = null;
        result            = null;
        
        try {
            
            String sql = "insert into rs_main ";

            con = rsCon.getRsConnection();
            
           
            result = preparedStatement.executeQuery();
            
            while(result.next()){
                rasas.setRsNo(result.getString("RS_NO"));
                rasas.setRsYear(result.getString("RS_YEAR"));
                rasas.setRsCenter(result.getString("RS_CENTER"));
                rasas.setRsSubCenter(result.getString("RS_SUB_CENTER"));
                rasas.setEntryDate(result.getDate("ENTRY_DATE"));
                rasas.setUserId(result.getString("USER_ID"));
            }
            
        } catch (Exception e) {
            System.out.println("com.rasas.mbeans.MBRsMain.getRasasList()" + e.getMessage());
        }
        
        
        
        return rowInserted;
    }

    ////////////////////////////////////////////////////////////////////////////
    
    
    
    public String getCenterNo() {
        return centerNo;
    }

    public void setCenterNo(String centerNo) {
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
