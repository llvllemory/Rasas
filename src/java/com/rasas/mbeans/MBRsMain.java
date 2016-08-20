package com.rasas.mbeans;

import com.rasas.dao.RasasDAO;
import com.rasas.entities.RsMain;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

@ManagedBean
@RequestScoped
public class MBRsMain implements Serializable{
    private String centerNo;
    private String subCenterNo;
    private int rsFrom;
    private int rsTo;
    private int rsFound = 0;
    
    private RasasDAO rsCon;
    private Connection con;
    private List<RsMain> rasasList;
    private List<RsMain> rsList;
    private RsMain   rasas;
    private PreparedStatement preparedStatement;
    private ResultSet result;

    
    public MBRsMain(){
 
    }

    public String checkCenterRasas() throws SQLException{
        System.out.println("com.rasas.mbeans.MBRsMain.checkCenterRasas()---------->");
                
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
        
        rsList = new ArrayList<>();
        rsList = getRasasListByCenterAndYear(centerNo, MBCommonMethods.getCurrentYear());
        
        if(rsList.size() > 0){
            
            rsFound = 0;
            for(RsMain rs : rsList){
              for(int i = rsFrom; i <= rsTo; i++){
                  if(rs.getRsNo() == i){
                      rsFound++;
                  }
              }  
            }
            
            if (rsFound == ((rsTo - rsFrom) + 1)) {
                MBCommonMethods.getErrorMessage("عملية خاطئة", "هذا الرصاص مصروف مسبقا للمركز, الرجاء التأكد أولا!");
            }else if(rsFound > 0 && rsFound < ((rsTo - rsFrom) + 1)){
                MBCommonMethods.getErrorMessage("عملية خاطئة", "هنالك رصاص مصروف مسبقا من هذا الرصاص للمركز, الرجاء التأكد أولا!");
            }else if(rsFound == 0){
                
                int x = saveCenterRasas();

                if (x == 0) {
                    MBCommonMethods.getErrorMessage("عملية خاطئة", "فشل في عملية تخزين الرصاص, الرجاء المحاولة مرة اخرى او التأكد من أرقام الرصاص!");
                } else if (x == ((rsTo - rsFrom) + 1)) {
                    MBCommonMethods.getInfoMessage("عملية ناحجة", "تم صرف الرصاص للمركز ينجاح.");
                } else {
                    MBCommonMethods.getInfoMessage("عملية خاطئة", "هنالك رصاص لم يتم صرفه, الرجاء التأكد من الرصاص المصروف!");
                }    
            }

        } else {
            int x = saveCenterRasas();

            if (x == 0) {
                MBCommonMethods.getErrorMessage("عملية خاطئة", "فشل في عملية تخزين الرصاص, الرجاء المحاولة مرة اخرى او التأكد من أرقام الرصاص!");
            } else if(x == ((rsTo - rsFrom) + 1)){
                MBCommonMethods.getInfoMessage("عملية ناحجة", "تم صرف الرصاص للمركز ينجاح.");
            }else{
                MBCommonMethods.getInfoMessage("عملية خاطئة", "هنالك رصاص لم يتم صرفه, الرجاء التأكد من الرصاص المصروف!");
            }
        } 
        return "";
    }
    
    
    public String checkSubCenterRasas() throws SQLException{
        System.out.println("com.rasas.mbeans.MBRsMain.checkSubCenterRasas()---------->");
        
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
        
        rsList = new ArrayList<>();
        rsList = getRasasListByCenterAndYear("220", MBCommonMethods.getCurrentYear());
        
        if(rsList.size() > 0){
            rsFound = 0;
            for(RsMain rs : rsList){
              for(int i = rsFrom; i <= rsTo; i++){
                  if(rs.getRsNo() == i){
                      rsFound++;
                  }
              }  
            }
            
            if(rsFound == 0){
                MBCommonMethods.getErrorMessage("عملية خاطئة", "الرصاص غير موجود في ملف المركز. الرجاء إستلام الرصاص من اللوازم أولا!");
            }else if(rsFound > 0 && rsFound > ((rsTo - rsFrom) + 1)){
                MBCommonMethods.getErrorMessage("عملية خاطئة", "هنالك رصاص غير موجود في ملف الرصاص, الرجاء إستلام الرصاص من اللوازم أولا!");
            }else{
                
                rsList = new ArrayList<>();
                rsList = getNullRasasListByCenterAndYear("220", MBCommonMethods.getCurrentYear());
                
                if(rsList.size() > 0){
                    
                    rsFound = 0;
                    
                    for (RsMain rs : rsList) {
                        for (int i = rsFrom; i <= rsTo; i++) {
                            if (rs.getRsNo() == i) {
                                rsFound++;
                            }
                        }
                    }
                    
                    if(rsFound == 0){
                        MBCommonMethods.getErrorMessage("عملية خاطئة", "الرصاص مصروف مسبقا ومسدد, الرجاء التأكد والمحاولة مرة اخرى!");
                    }else if(rsFound > 0 && rsFound < ((rsTo - rsFrom) + 1)){
                        MBCommonMethods.getErrorMessage("عملية خاطئة", "هنالك رصاص مصروف مسبقا ومسدد, الرجاء التأكد والمحاولة مرة اخرى!");
                    }else{
                        
                        int x = saveSubCenterRasas();

                        if (x == 0) {
                            MBCommonMethods.getErrorMessage("عملية خاطئة", "فشل في عملية تخزين الرصاص, الرجاء المحاولة مرة اخرى او التأكد من أرقام الرصاص!");
                        } else if (x == ((rsTo - rsFrom) + 1)) {
                            MBCommonMethods.getInfoMessage("عملية ناحجة", "تم صرف الرصاص للمركز ينجاح.");
                        } else {
                            MBCommonMethods.getInfoMessage("عملية خاطئة", "هنالك رصاص لم يتم صرفه, الرجاء التأكد من الرصاص المصروف!");
                        }
                    }
                }else{
                   MBCommonMethods.getErrorMessage("عملية خاطئة", "هنالك رصاص مصروف مسبقا ومسدد, الرجاء التأكد والمحاولة مرة اخرى!");
                }
            }
        } else {
            MBCommonMethods.getErrorMessage("عملية خاطئة", "لا يوجد رصاص متاح للصرف في المركز, يجب إستلام الرصاص من اللوازم أولا!");
        }
        return "";
    }
    
    ////////////////////////////////////////////////////////////////////////////
    //////////////////// Query methods /////////////////////////////////////////
    
    public int saveCenterRasas() throws SQLException{
        System.out.println("com.rasas.mbeans.MBRsMain.saveCenterRasas()---------->");
        
        rsCon = new RasasDAO();
        rasasList = new ArrayList<>();
        preparedStatement = null;
        result            = null;
        int[] x = null;
        
        try {
            
            String sql = "insert into rs_main (rs_no, rs_year, rs_center, rs_center_date, rs_center_user_id) values (?, ?, ?, ?, ?)";

            con = rsCon.getRsConnection();
            preparedStatement = con.prepareStatement(sql);
            //preparedStatement.setString(1, rs_center);
            //preparedStatement.setString(2, rs_year);
           
            //result = preparedStatement.executeQuery();
            
            String rs_year = MBCommonMethods.getCurrentYear();
            String rs_date = MBCommonMethods.getCurrentDate();
            
            Date utilDate = new Date();
            Date sqlDate  = new java.sql.Date(utilDate.getTime());
            
            for(int i = rsFrom; i <= rsTo; i++){
                
                preparedStatement.setLong(1, i);
                preparedStatement.setString(2, rs_year);
                preparedStatement.setString(3, centerNo);
                preparedStatement.setDate(4, (java.sql.Date) sqlDate);
                preparedStatement.setString(5, "33476");
                
                preparedStatement.addBatch();
                
            }
      
            x = preparedStatement.executeBatch();
            
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
        return x.length;
    }
    
    ////////////////////////////////////////////////////////////////////////////
    
    public int saveSubCenterRasas() throws SQLException{
        System.out.println("com.rasas.mbeans.MBRsMain.saveSubCenterRasas()---------->");
        
        rsCon = new RasasDAO();
        rasasList = new ArrayList<>();
        preparedStatement = null;
        result = null;
        int[] x = null;

        try {

            String sql = "update rs_main set rs_sub_center = ?, rs_sub_center_date = ?, rs_sub_center_user_id = ?"
                    + " where rs_no = ? and rs_year = ? and rs_center = ?";

            con = rsCon.getRsConnection();
            preparedStatement = con.prepareStatement(sql);
            
            String rs_year = MBCommonMethods.getCurrentYear();
            Date utilDate = new Date();
            Date sqlDate = new java.sql.Date(utilDate.getTime());

            for (int i = rsFrom; i <= rsTo; i++) {

                preparedStatement.setString(1, subCenterNo);
                preparedStatement.setDate(2, (java.sql.Date) sqlDate);
                preparedStatement.setString(3, "33476");
                preparedStatement.setInt(4, i);
                preparedStatement.setString(5, rs_year);
                preparedStatement.setString(6, "220");

                preparedStatement.addBatch();

            }

            x = preparedStatement.executeBatch();

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
        return x.length;
    }
    
    ////////////////////////////////////////////////////////////////////////////
    
    public List<RsMain> getRasasListByCenterAndYear(String rs_center, String rs_year) throws SQLException{
        System.out.println("com.rasas.mbeans.MBRsMain.getRasasListByCenterAndYear()---------->");
        
        rsCon = new RasasDAO();
        rasasList = new ArrayList<>();
        preparedStatement = null;
        result            = null;
        
        try {
            
            String sql = "select * from rs_main where rs_center = ? and rs_year = ?";

            con = rsCon.getRsConnection();
            preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, rs_center);
            preparedStatement.setString(2, rs_year);
           
            result = preparedStatement.executeQuery();
            
            while(result.next()){
                
                rasas = new RsMain();
                
                rasas.setRsNo(result.getLong("RS_NO"));
                rasas.setRsYear(result.getString("RS_YEAR"));
                rasas.setRsCenter(result.getString("RS_CENTER"));
                rasas.setRsCenterDate(result.getDate("RS_CENTER_DATE"));
                rasas.setRsCenterUserId(result.getString("RS_CENTER_USER_ID"));
                rasas.setRsSubCenter(result.getString("RS_SUB_CENTER"));
                rasas.setRsSubCenterDate(result.getDate("RS_SUB_CENTER_DATE"));
                rasas.setRsSubCenterUserId(result.getString("RS_SUB_CENTER_USER_ID"));
                
                rasasList.add(rasas);
                
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
        
        return rasasList;
    }
    
    ////////////////////////////////////////////////////////////////////////////
    
    public List<RsMain> getNullRasasListByCenterAndYear(String rs_center, String rs_year) throws SQLException{
        System.out.println("com.rasas.mbeans.MBRsMain.getNullRasasListByCenterAndYear()---------->");
        
        rsCon = new RasasDAO();
        rasasList = new ArrayList<>();
        preparedStatement = null;
        result            = null;
        
        try {
            
            String sql = "select * from rs_main where rs_sub_center is null and rs_center = ? and rs_year = ?";

            con = rsCon.getRsConnection();
            preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, rs_center);
            preparedStatement.setString(2, rs_year);
           
            result = preparedStatement.executeQuery();
            
            while(result.next()){
                
                rasas = new RsMain();
                
                rasas.setRsNo(result.getLong("RS_NO"));
                rasas.setRsYear(result.getString("RS_YEAR"));
                rasas.setRsCenter(result.getString("RS_CENTER"));
                rasas.setRsCenterDate(result.getDate("RS_CENTER_DATE"));
                rasas.setRsCenterUserId(result.getString("RS_CENTER_USER_ID"));
                rasas.setRsSubCenter(result.getString("RS_SUB_CENTER"));
                rasas.setRsSubCenterDate(result.getDate("RS_SUB_CENTER_DATE"));
                rasas.setRsSubCenterUserId(result.getString("RS_SUB_CENTER_USER_ID"));
                
                rasasList.add(rasas);
                
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
        
        return rasasList;
    }
    
    ////////////////////////////////////////////////////////////////////////////
    
    public List<RsMain> getRasasListByCenterAndYearAndFromAndTo(String rs_center, String rs_year, String rs_from, String rs_to) throws SQLException{
        System.out.println("com.rasas.mbeans.MBRsMain.getRasasListByCenterAndYearAndFromAndTo()---------->");
        
        rsCon = new RasasDAO();
        rasasList = new ArrayList<>();
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
                
                rasas = new RsMain();
                
                rasas.setRsNo(result.getLong("RS_NO"));
                rasas.setRsYear(result.getString("RS_YEAR"));
                rasas.setRsCenter(result.getString("RS_CENTER"));
                rasas.setRsCenterDate(result.getDate("RS_CENTER_DATE"));
                rasas.setRsCenterUserId(result.getString("RS_CENTER_USER_ID"));
                rasas.setRsSubCenter(result.getString("RS_SUB_CENTER"));
                rasas.setRsSubCenterDate(result.getDate("RS_SUB_CENTER_DATE"));
                rasas.setRsSubCenterUserId(result.getString("RS_SUB_CENTER_USER_ID"));
                
                rasasList.add(rasas);
                
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
        
        return rasasList;
    }
    
    ////////////////////////////////////////////////////////////////////////////
    
    public List<RsMain> getNullRasasListByCenterAndYearAndFromAndTo(String rs_center, String rs_year, String rs_from, String rs_to) throws SQLException{
        System.out.println("com.rasas.mbeans.MBRsMain.getNullRasasListByCenterAndYearAndFromAndTo()---------->");
        
        rsCon = new RasasDAO();
        rasasList = new ArrayList<>();
        preparedStatement = null;
        result            = null;
        
        try {
            
            String sql = "select * from rs_main where rs_sub_center is null and rs_center = ? and rs_year = ? and rs_no between ? and ? ";

            con = rsCon.getRsConnection();
            preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, rs_center);
            preparedStatement.setString(2, rs_year);
            preparedStatement.setString(3, rs_from);
            preparedStatement.setString(4, rs_to);
           
            result = preparedStatement.executeQuery();
            
            while(result.next()){
                
                rasas = new RsMain();
                
                rasas.setRsNo(result.getLong("RS_NO"));
                rasas.setRsYear(result.getString("RS_YEAR"));
                rasas.setRsCenter(result.getString("RS_CENTER"));
                rasas.setRsCenterDate(result.getDate("RS_CENTER_DATE"));
                rasas.setRsCenterUserId(result.getString("RS_CENTER_USER_ID"));
                rasas.setRsSubCenter(result.getString("RS_SUB_CENTER"));
                rasas.setRsSubCenterDate(result.getDate("RS_SUB_CENTER_DATE"));
                rasas.setRsSubCenterUserId(result.getString("RS_SUB_CENTER_USER_ID"));
                
                rasasList.add(rasas);
                
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
        
        return rasasList;
    }

    
    ////////////////////////////////////////////////////////////////////////////
    ///////////////////////////// Getters and Setters //////////////////////////
    public String getCenterNo() {
        return centerNo;
    }

    public void setCenterNo(String centerNo) {
        this.centerNo = centerNo;
    }

    public String getSubCenterNo() {
        return subCenterNo;
    }

    public void setSubCenterNo(String subCenterNo) {
        this.subCenterNo = subCenterNo;
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
