package project.rikogunawan;

import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DBRute {

    private RuteModel dt = new RuteModel();

    public RuteModel getRuteModel() {
        return dt;
    }

    public void setRuteModel(RuteModel s) {
        dt = s;
    }

    public ObservableList<RuteModel> Load(String idperjalanan) {
        try {
            ObservableList<RuteModel> tableData = FXCollections.observableArrayList();
            Koneksi con = new Koneksi();
            con.bukaKoneksi();
            con.statement = con.dbKoneksi.createStatement();
            ResultSet rs = con.statement.executeQuery("SELECT r.idrute, r.idperjalanan, r.urut, r.waktu, r.jarak, "
                    + "asal.namastasiun AS namastasiunasal, "
                    + "tujuan.namastasiun AS namastasiuntujuan "
                    + "FROM rute r "
                    + "JOIN stasiun asal ON r.idstasiunasal = asal.idstasiun "
                    + "JOIN stasiun tujuan ON r.idstasiuntujuan = tujuan.idstasiun "
                    + "WHERE r.idperjalanan LIKE '" + idperjalanan + "'"
                    + "ORDER BY r.urut ASC"); //Urut berdasarkan Urutan Perjalanannya

            while (rs.next()) {
                RuteModel d = new RuteModel();
                d.setIdrute(rs.getString("idrute"));
                d.setIdperjalanan(rs.getString("idperjalanan"));
                d.setUrut(rs.getInt("urut"));
                d.setWaktu(rs.getInt("waktu"));
                d.setJarak(rs.getDouble("jarak"));
                d.setNamastasiunasal(rs.getString("namastasiunasal"));
                d.setNamastasiuntujuan(rs.getString("namastasiuntujuan"));
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
            con.preparedStatement = con.dbKoneksi.prepareStatement(
                    "INSERT INTO rute (idrute, idperjalanan, idstasiunasal, idstasiuntujuan, urut, waktu, jarak) VALUES (?, ?, ?, ?, ?, ?, ?)");
            con.preparedStatement.setString(1, dt.getIdrute());
            con.preparedStatement.setString(2, dt.getIdperjalanan());
            con.preparedStatement.setString(3, dt.getIdstasiunasal());
            con.preparedStatement.setString(4, dt.getIdstasiuntujuan());
            con.preparedStatement.setInt(5, dt.getUrut());
            con.preparedStatement.setInt(6, dt.getWaktu());
            con.preparedStatement.setDouble(7, dt.getJarak());
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

    public boolean update() {
        boolean berhasil = false;
        Koneksi con = new Koneksi();
        try {
            con.bukaKoneksi();
            con.preparedStatement = con.dbKoneksi.prepareStatement(
                    "UPDATE rute SET idperjalanan = ?, idstasiunasal = ?, idstasiuntujuan = ?, urut = ?, waktu = ?, jarak = ? WHERE idrute = ?");
            con.preparedStatement.setString(1, dt.getIdperjalanan());
            con.preparedStatement.setString(2, dt.getIdstasiunasal());
            con.preparedStatement.setString(3, dt.getIdstasiuntujuan());
            con.preparedStatement.setInt(4, dt.getUrut());
            con.preparedStatement.setInt(5, dt.getWaktu());
            con.preparedStatement.setDouble(6, dt.getJarak());
            con.preparedStatement.setString(7, dt.getIdrute());
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

    public int validasi(String idPerjalanan) {
        int val = 0;
        try {
            // Membuka koneksi
            Koneksi con = new Koneksi();
            con.bukaKoneksi();

            // Query untuk mengecek jumlah baris dengan ID yang sama
            String query = "SELECT COUNT(*) AS jml FROM perjalanan WHERE idperjalanan = '" + idPerjalanan + "'";
            con.statement = con.dbKoneksi.createStatement();
            ResultSet rs = con.statement.executeQuery(query);

            // Mengambil hasil validasi
            while (rs.next()) {
                val = rs.getInt("jml"); // Jumlah data dengan ID yang sama
            }

            // Menutup koneksi
            con.tutupKoneksi();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return val;
    }

    public boolean delete(String id) {
        boolean berhasil = false;
        Koneksi con = new Koneksi();
        try {
            con.bukaKoneksi();
            con.preparedStatement = con.dbKoneksi.prepareStatement("DELETE FROM rute WHERE idrute = ?");
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

}
