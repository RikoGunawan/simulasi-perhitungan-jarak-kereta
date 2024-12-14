/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package project.rikogunawan;

import java.sql.ResultSet;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DBPerjalanan {
    private PerjalananModel dt = new PerjalananModel();    
    public PerjalananModel getPerjalananModel() { return dt; }
    public void setPerjalananModel(PerjalananModel s) { dt = s; }

    public ObservableList<PerjalananModel> Load() {
    try {
        ObservableList<PerjalananModel> tableData = FXCollections.observableArrayList();
        Koneksi con = new Koneksi();
        con.bukaKoneksi();
        con.statement = con.dbKoneksi.createStatement();
        ResultSet rs = con.statement.executeQuery(
            "SELECT p.idperjalanan, p.idkereta, p.waktutotal, p.jaraktotal, " +
            "kota_asal.kota AS kotaasal, kota_tujuan.kota AS kotatujuan " +
            "FROM perjalanan p " +
            "LEFT JOIN ( " +
            "    SELECT r.idperjalanan, s.kota " +
            "    FROM rute r " +
            "    JOIN stasiun s ON r.idstasiunasal = s.idstasiun " +
            "    WHERE r.urut = 1 " +
            ") kota_asal ON p.idperjalanan = kota_asal.idperjalanan " +
            "LEFT JOIN ( " +
            "    SELECT r.idperjalanan, s.kota " +
            "    FROM rute r " +
            "    JOIN stasiun s ON r.idstasiuntujuan = s.idstasiun " +
            "    WHERE r.urut = (SELECT MAX(urut) " +
            "                    FROM rute r2 " +
            "                    WHERE r2.idperjalanan = r.idperjalanan) " +
            ") kota_tujuan ON p.idperjalanan = kota_tujuan.idperjalanan"
        );

        while (rs.next()) {
            PerjalananModel d = new PerjalananModel();
            d.setIdperjalanan(rs.getString("idperjalanan"));
            d.setIdkereta(rs.getString("idkereta"));
            d.setWaktutotal(rs.getInt("waktutotal"));
            d.setJaraktotal(rs.getDouble("jaraktotal"));
            d.setKotaasal(rs.getString("kotaasal"));
            d.setKotatujuan(rs.getString("kotatujuan"));
            tableData.add(d);
        }
        con.tutupKoneksi();
        return tableData;
    } catch (Exception e) {
        e.printStackTrace();
        return null;
    }
}


    public boolean insert() {
        boolean berhasil = false;        
        Koneksi con = new Koneksi();
        try {       
            con.bukaKoneksi();
            con.preparedStatement = con.dbKoneksi.prepareStatement
        ("INSERT INTO perjalanan (idperjalanan, idkereta, waktutotal, jaraktotal) VALUES (?, ?, ?, ?)");
            con.preparedStatement.setString(1, dt.getIdperjalanan());           
            con.preparedStatement.setString(2, dt.getIdkereta());                     
            con.preparedStatement.setInt(3, dt.getWaktutotal());            
            con.preparedStatement.setDouble(4, dt.getJaraktotal());
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

    public boolean delete(String id) {
        boolean berhasil = false;        
        Koneksi con = new Koneksi();
        try {            
            con.bukaKoneksi();
            con.preparedStatement = con.dbKoneksi.prepareStatement("DELETE FROM perjalanan WHERE idperjalanan = ?");
            con.preparedStatement.setString(1, id);
            con.preparedStatement.executeUpdate();            
            berhasil = true;
        } catch (Exception e) {            
            e.printStackTrace();
        } finally {            
            con.tutupKoneksi();            
            return berhasil;        
        }
    }
    
    public boolean deleteall(String idperjalanan) {
    boolean berhasil = false;
    Koneksi con = new Koneksi();
    try {
        con.bukaKoneksi();

        // Hapus data di tabel rute yang terkait dengan idperjalanan
        con.preparedStatement = con.dbKoneksi.prepareStatement("DELETE FROM rute WHERE idperjalanan = ?");
        con.preparedStatement.setString(1, idperjalanan);
        con.preparedStatement.executeUpdate();

        // Hapus data di tabel perjalanan
        con.preparedStatement = con.dbKoneksi.prepareStatement("DELETE FROM perjalanan WHERE idperjalanan = ?");
        con.preparedStatement.setString(1, idperjalanan);
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
        ("UPDATE perjalanan SET idkereta = ?, waktutotal = ?, jaraktotal = ? WHERE idperjalanan = ?");
            con.preparedStatement.setString(1, dt.getIdkereta());
            con.preparedStatement.setInt(2, dt.getWaktutotal());
            con.preparedStatement.setDouble(3, dt.getJaraktotal());
            con.preparedStatement.setString(4, dt.getIdperjalanan());
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

