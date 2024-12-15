
package project.rikogunawan;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Aspire 3
 */
public class DBStasiun {
        private StasiunModel dt=new StasiunModel();    
    public StasiunModel getStasiunModel(){ return(dt);}
    public void setStasiunModel(StasiunModel s){ dt=s;}
    
    public ObservableList<StasiunModel> Load() {
        try {
            ObservableList<StasiunModel> tableData=FXCollections.observableArrayList();
            Koneksi con = new Koneksi();            
            con.bukaKoneksi();
            con.statement = (Statement) con.dbKoneksi.createStatement();
            ResultSet rs = con.statement.executeQuery("Select idstasiun, namastasiun, alamat, kota, status from stasiun");

        int i = 1;
        while(rs.next()){
            StasiunModel d = new StasiunModel();
                d.setIdstasiun(rs.getString("idstasiun"));
                d.setNamastasiun(rs.getString("namastasiun"));
                d.setAlamat(rs.getString("alamat"));
                d.setKota(rs.getString("kota"));                
                d.setStatus(rs.getString("status"));
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
    
    public ObservableList<StasiunModel>  CariStasiun(String kode, String namastasiun) {
        try {    
            ObservableList<StasiunModel> 	tableData;
            tableData = FXCollections.observableArrayList();
            Koneksi con = new Koneksi(); 
            con.bukaKoneksi();
            con.statement = con.dbKoneksi.createStatement();
            ResultSet rs = (ResultSet) con.statement.executeQuery
        ("select * from stasiun WHERE idstasiun LIKE '" + kode + "%' OR namastasiun LIKE '" + namastasiun + "%'");
        int i = 1;
        while(rs.next()){
            StasiunModel d = new StasiunModel();
            d.setIdstasiun(rs.getString("idstasiun"));
            d.setNamastasiun(rs.getString("namastasiun"));
            d.setAlamat(rs.getString("alamat"));
            d.setKota(rs.getString("kota"));
            d.setStatus(rs.getString("status"));
            tableData.add(d);
            i++;
        }
        con.tutupKoneksi();
        return tableData;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
    
    public int validasi(String nomor) {
        int val = 0;
        try {         
            Koneksi con = new Koneksi();            
            con.bukaKoneksi();
            con.statement = con.dbKoneksi.createStatement();
            ResultSet rs = con.statement.executeQuery(  "select count(*) as jml from stasiun where idstasiun = '" + nomor + "'");
            while (rs.next()) {                
                val = rs.getInt("jml");            
            }            
            con.tutupKoneksi();
        } catch (SQLException e) {            
            e.printStackTrace();        
        }
        return val;
    }

    public boolean insert() {
        boolean berhasil = false;        
        Koneksi con = new Koneksi();
        try {       
            con.bukaKoneksi();
            con.preparedStatement = con.dbKoneksi.prepareStatement
        ("insert into stasiun (idstasiun,namastasiun, alamat, kota, status) values (?,?,?,?,?)");
            con.preparedStatement.setString(1, getStasiunModel().getIdstasiun());           
            con.preparedStatement.setString(2, getStasiunModel().getNamastasiun());
            con.preparedStatement.setString(3, getStasiunModel().getAlamat());
            con.preparedStatement.setString(4, getStasiunModel().getKota());           
            con.preparedStatement.setString(5, getStasiunModel().getStatus());        
            con.preparedStatement.executeUpdate();
            berhasil = true;
        } catch (Exception e) {            
            e.printStackTrace();            
            berhasil = false;
        } finally {            
            con.tutupKoneksi();            
            return berhasil;        
        }    
     }

    public boolean delete(String nomor) {
        boolean berhasil = false;        
        Koneksi con = new Koneksi();
        try {            
            con.bukaKoneksi();
            con.preparedStatement = con.dbKoneksi.prepareStatement
        ("delete from stasiun where idstasiun  = ? ");
            con.preparedStatement.setString(1, nomor);
            con.preparedStatement.executeUpdate();            
            berhasil = true;
        } catch (Exception e) {            
            e.printStackTrace();
        } finally {            
            con.tutupKoneksi();            
            return berhasil;        
        }
    }
    
    public boolean update() {
        boolean berhasil = false;        
        Koneksi con = new Koneksi();
        try {            
            con.bukaKoneksi();
            con.preparedStatement = con.dbKoneksi.prepareStatement
        ("update stasiun set namastasiun = ?, alamat = ?, kota = ?, status = ?  where  idstasiun = ? ");
            con.preparedStatement.setString(1, getStasiunModel().getNamastasiun());
            con.preparedStatement.setString(2, getStasiunModel().getAlamat());
            con.preparedStatement.setString(3, getStasiunModel().getKota());
            con.preparedStatement.setString(4, getStasiunModel().getStatus());
            con.preparedStatement.setString(5, getStasiunModel().getIdstasiun());
            con.preparedStatement.executeUpdate();            
            berhasil = true;
        } catch (Exception e) {            
            e.printStackTrace();            
            berhasil = false;
        } finally {            
            con.tutupKoneksi();            
            return berhasil;        
        }    
        }

}
