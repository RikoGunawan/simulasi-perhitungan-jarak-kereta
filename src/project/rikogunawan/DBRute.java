
package project.rikogunawan;

import java.sql.ResultSet;
import java.sql.Statement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DBRute {
    private RuteModel dt=new RuteModel();    
    public RuteModel getRuteModel(){ return(dt);}
    public void setRuteModel(RuteModel s){ dt=s;}
    
    public ObservableList<RuteModel> Load() {
        try {
            ObservableList<RuteModel> tableData=FXCollections.observableArrayList();
            Koneksi con = new Koneksi();            
            con.bukaKoneksi();
            con.statement = (Statement) con.dbKoneksi.createStatement();
            ResultSet rs = con.statement.executeQuery("Select idrute, idstasiunasal, idstasiuntujuan, waktu, jarak from jual");

        int i = 1;
        while(rs.next()){
            RuteModel d = new RuteModel();
                d.setIdrute(rs.getString("idrute"));           
                d.setIdstasiunasal(rs.getString("idstasiunasal"));           
                d.setIdstasiuntujuan(rs.getString("idstasiuntujuan"));           
                d.setWaktu(rs.getInt("waktu"));  
                d.setJarak(rs.getDouble("jarak"));    

                tableData.add(d);                
                i++;            
            }
            con.tutupKoneksi();            
            return tableData;
        } catch (Exception e) {            
            e.printStackTrace();            
            return null;        
        }
    }
}
