package project.rikogunawan;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class FXMLSimulasiController implements Initializable {

    @FXML
    private Button btnsimulasi;
    @FXML
    private TextField txtjaraktotal;
    @FXML
    private ListView<String> lsttujuan;
    @FXML
    private ListView<String> lstasal;
    @FXML
    private TextField txtwaktutotal;
    @FXML
    private TableView<RuteModel> tbvrute;
    @FXML
    private Button btnrandomsimulasi;

    private ObservableList<String> listKota = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Mengambil data kota dari database dan menampilkannya di ListView
        loadKotaFromDatabase();

        // Mengatur kolom TableView
        setupTableView();
    }

    private void setupTableView() {
        TableColumn<RuteModel, String> colStasiunAsal = new TableColumn<>("Stasiun Asal");
        colStasiunAsal.setCellValueFactory(new PropertyValueFactory<>("namastasiunasal"));

        TableColumn<RuteModel, String> colStasiunTujuan = new TableColumn<>("Stasiun Tujuan");
        colStasiunTujuan.setCellValueFactory(new PropertyValueFactory<>("namastasiuntujuan"));

        TableColumn<RuteModel, Integer> colWaktu = new TableColumn<>("Waktu (Menit)");
        colWaktu.setCellValueFactory(new PropertyValueFactory<>("waktu"));

        TableColumn<RuteModel, Double> colJarak = new TableColumn<>("Jarak (KM)");
        colJarak.setCellValueFactory(new PropertyValueFactory<>("jarak"));

        tbvrute.getColumns().addAll(colStasiunAsal, colStasiunTujuan, colWaktu, colJarak);
    }

    private void loadKotaFromDatabase() {
        List<String> kotaList = new ArrayList<>();
        try {
            Koneksi con = new Koneksi();
            con.bukaKoneksi();
            Statement stmt = con.dbKoneksi.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT DISTINCT kota FROM stasiun");

            while (rs.next()) {
                String kota = rs.getString("kota");
                if (!kotaList.contains(kota)) {
                    kotaList.add(kota);
                }
            }

            listKota.setAll(kotaList);
            lstasal.setItems(listKota);
            lsttujuan.setItems(listKota);
            con.tutupKoneksi();
        } catch (Exception e) {
            System.err.println("Error saat memuat data kota dari database: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private ObservableList<RuteModel> cariRuteBerhubungan(String asal, String tujuan) {
        ObservableList<RuteModel> ruteTerhubung = FXCollections.observableArrayList();

        LinkedList<String> queue = new LinkedList<>();
        Set<String> visited = new HashSet<>();
        Map<String, String> parent = new HashMap<>();
        Map<String, RuteModel> ruteData = new HashMap<>();  // Untuk menyimpan data rute berdasarkan stasiun

        queue.add(asal);
        visited.add(asal);

        System.out.println("Memulai pencarian rute dari " + asal + " ke " + tujuan);

        while (!queue.isEmpty()) {
            String currentStasiun = queue.poll();
            System.out.println("Memproses stasiun: " + currentStasiun);

            // Query untuk mengambil rute yang berhubungan dengan currentStasiun
            String query = "SELECT r.idrute, r.idperjalanan, r.urut, IFNULL(r.waktu, 0) AS waktu, IFNULL(r.jarak, 0) AS jarak, "
                    + "asal.namastasiun AS namastasiunasal, "
                    + "tujuan.namastasiun AS namastasiuntujuan "
                    + "FROM rute r "
                    + "JOIN stasiun asal ON r.idstasiunasal = asal.idstasiun "
                    + "JOIN stasiun tujuan ON r.idstasiuntujuan = tujuan.idstasiun "
                    + "WHERE LOWER(asal.namastasiun) = LOWER('" + currentStasiun + "')";

            try {
                Koneksi con = new Koneksi();
                con.bukaKoneksi();
                con.statement = con.dbKoneksi.createStatement();
                ResultSet rs = con.statement.executeQuery(query);

                while (rs.next()) {
                    String nextStasiun = rs.getString("namastasiuntujuan");

                    if (!visited.contains(nextStasiun)) {
                        visited.add(nextStasiun);
                        queue.add(nextStasiun);
                        parent.put(nextStasiun, currentStasiun);

                        // Menyimpan informasi rute untuk setiap stasiun
                        RuteModel rute = new RuteModel();
                        rute.setNamastasiunasal(currentStasiun);
                        rute.setNamastasiuntujuan(nextStasiun);
                        rute.setWaktu(rs.getInt("waktu"));
                        rute.setJarak(rs.getDouble("jarak"));
                        ruteData.put(nextStasiun, rute);

                        if (nextStasiun.equals(tujuan)) {
                            System.out.println("Tujuan ditemukan! Membalikkan rute...");
                            String pathStation = tujuan;
                            // Menambahkan rute mulai dari tujuan ke asal
                            while (pathStation != null) {
                                RuteModel pathRute = ruteData.get(pathStation);
                                if (pathRute != null) {
                                    ruteTerhubung.add(0, pathRute);  // Menambahkan di awal untuk urutan yang benar
                                }
                                pathStation = parent.get(pathStation);
                            }
                            break;
                        }
                    }
                }
                con.tutupKoneksi();
            } catch (Exception e) {
                System.err.println("Error saat mengakses database untuk rute: " + e.getMessage());
                e.printStackTrace();
            }
        }

        System.out.println("Pencarian rute selesai");
        return ruteTerhubung;
    }

    private void tampilkanRuteDariDatabase(String asal, String tujuan) {
        ObservableList<RuteModel> rute = cariRuteBerhubungan(asal, tujuan);
        if (!rute.isEmpty()) {
            tbvrute.setItems(rute);

            // Hitung total jarak dan waktu
            int totalWaktu = rute.stream().mapToInt(RuteModel::getWaktu).sum();
            double totalJarak = rute.stream().mapToDouble(RuteModel::getJarak).sum();

            System.out.println("Total waktu: " + totalWaktu + " menit");
            System.out.println("Total jarak: " + totalJarak + " km");

            txtwaktutotal.setText(totalWaktu + " menit");
            txtjaraktotal.setText(totalJarak + " km");
        } else {
            clearTableView();
            txtwaktutotal.setText("Rute tidak tersedia");
            txtjaraktotal.setText("Rute tidak tersedia");
        }
    }

    @FXML
    private void btnsimulasiclick(ActionEvent event) {
        String asal = lstasal.getSelectionModel().getSelectedItem();
        String tujuan = lsttujuan.getSelectionModel().getSelectedItem();

        if (asal != null && tujuan != null && !asal.equals(tujuan)) {
            System.out.println("Simulasi manual dimulai: " + asal + " -> " + tujuan);
            tampilkanRuteDariDatabase(asal, tujuan);
        } else {
            clearTableView();
            txtwaktutotal.setText("Asal dan tujuan harus berbeda");
            txtjaraktotal.setText("");
        }
    }

    @FXML
    private void btnrandomsimulasiclick(ActionEvent event) {
        Random random = new Random();

        int asalIndex = random.nextInt(listKota.size());
        int tujuanIndex;

        do {
            tujuanIndex = random.nextInt(listKota.size());
        } while (tujuanIndex == asalIndex);

        String asal = listKota.get(asalIndex);
        String tujuan = listKota.get(tujuanIndex);

        lstasal.getSelectionModel().select(asal);
        lsttujuan.getSelectionModel().select(tujuan);

        System.out.println("Simulasi random dimulai: " + asal + " -> " + tujuan);
        tampilkanRuteDariDatabase(asal, tujuan);
    }

    private void clearTableView() {
        tbvrute.getItems().clear();
    }
}
