/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package project.rikogunawan;

import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Aspire 3
 */
public class DBKereta {

    private KeretaModel dt = new KeretaModel();

    public KeretaModel getKeretaModel() {
        return (dt);
    }

    public void setKeretaModel(KeretaModel s) {
        dt = s;
    }

    public ObservableList<KeretaModel> Load() {
        try {
            ObservableList<KeretaModel> tableData = FXCollections.observableArrayList();
            Koneksi con = new Koneksi();
            con.bukaKoneksi();
            con.statement = con.dbKoneksi.createStatement();
            ResultSet rs = con.statement.executeQuery("Select idkereta, namakereta, gambar, kecepatan, tipe from kereta");

            int i = 1;
            while (rs.next()) {
                KeretaModel d = new KeretaModel();
                d.setIdkereta(rs.getString("idkereta"));
                d.setNamakereta(rs.getString("namakereta"));
                d.setGambar(rs.getString("gambar"));
                d.setKecepatan(rs.getDouble("kecepatan"));

                double kecepatan = rs.getDouble("kecepatan");
                String tipe;

                if (kecepatan <= 100) {
                    tipe = "Ekonomi";
                } else if (kecepatan <= 110) {
                    tipe = "Bisnis";
                } else if (kecepatan <= 120) {
                    tipe = "Eksekutif";
                } else {
                    tipe = "Petir";
                }
                d.setTipe(tipe);

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

    public ObservableList<KeretaModel> CariKereta(String kode, String nama) {
        try {
            ObservableList<KeretaModel> tableData;
            tableData = FXCollections.observableArrayList();
            Koneksi con = new Koneksi();
            con.bukaKoneksi();
            con.statement = con.dbKoneksi.createStatement();
            ResultSet rs = (ResultSet) con.statement.executeQuery("select * from kereta WHERE idkereta LIKE '" + kode + "%' OR namakereta LIKE '" + nama + "%'");
            int i = 1;
            while (rs.next()) {
                KeretaModel d = new KeretaModel();
                d.setIdkereta(rs.getString("idkereta"));
                d.setNamakereta(rs.getString("namakereta"));
                d.setGambar(rs.getString("gambar"));
                d.setKecepatan(rs.getDouble("kecepatan"));
                d.setTipe(rs.getString("tipe"));

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

    public int validasi(String nomor) {
        int val = 0;
        try {
            Koneksi con = new Koneksi();
            con.bukaKoneksi();
            con.statement = con.dbKoneksi.createStatement();
            ResultSet rs = con.statement.executeQuery("select count(*) as jml from kereta where idkereta = '" + nomor + "'");
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
            // Tipe Berdasar Kecepatan
            double kecepatan = getKeretaModel().getKecepatan();
            String tipe;
            if (kecepatan <= 100) {
                tipe = "Ekonomi";
            } else if (kecepatan <= 110) {
                tipe = "Bisnis";
            } else if (kecepatan <= 120) {
                tipe = "Eksekutif";
            } else {
                tipe = "Petir";
            }
            getKeretaModel().setTipe(tipe);

            con.bukaKoneksi();
            con.preparedStatement = con.dbKoneksi.prepareStatement("insert into kereta (idkereta ,namakereta, gambar, kecepatan, tipe) values (?,?,?,?,?)");
            con.preparedStatement.setString(1, getKeretaModel().getIdkereta());
            con.preparedStatement.setString(2, getKeretaModel().getNamakereta());
            con.preparedStatement.setString(3, getKeretaModel().getGambar());
            con.preparedStatement.setDouble(4, getKeretaModel().getKecepatan());
            con.preparedStatement.setString(5, getKeretaModel().getTipe());
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
            con.preparedStatement = con.dbKoneksi.prepareStatement("delete from kereta where idkereta  = ? ");
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
            // Tipe Berdasar Kecepatan
            double kecepatan = getKeretaModel().getKecepatan();
            String tipe;
            if (kecepatan <= 100) {
                tipe = "Ekonomi";
            } else if (kecepatan <= 110) {
                tipe = "Bisnis";
            } else if (kecepatan <= 120) {
                tipe = "Eksekutif";
            } else {
                tipe = "Petir";
            }
            getKeretaModel().setTipe(tipe);

            con.bukaKoneksi();
            con.preparedStatement = con.dbKoneksi.prepareStatement("update kereta set namakereta = ?, kecepatan = ?, gambar = ?, tipe = ?  where  idkereta = ? ");
            con.preparedStatement.setString(1, getKeretaModel().getNamakereta());
            con.preparedStatement.setDouble(2, getKeretaModel().getKecepatan());
            con.preparedStatement.setString(3, getKeretaModel().getGambar());
            con.preparedStatement.setString(4, getKeretaModel().getTipe());
            con.preparedStatement.setString(5, getKeretaModel().getIdkereta());
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

    public double getKecepatanKereta(String idKereta) {
        double kecepatan = 0.0;
        Koneksi con = new Koneksi();
        try {
            con.bukaKoneksi();
            String query = "SELECT kecepatan FROM kereta WHERE idkereta = ?";
            con.preparedStatement = con.dbKoneksi.prepareStatement(query);
            con.preparedStatement.setString(1, idKereta);
            ResultSet rs = con.preparedStatement.executeQuery();
            if (rs.next()) {
                kecepatan = rs.getDouble("kecepatan");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            con.tutupKoneksi();
        }
        return kecepatan;
    }

}
