package project.rikogunawan;

import java.sql.ResultSet;
import java.sql.Statement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

public class FXMLInputPerjalananController {

    boolean editdata = false;

    @FXML
    private Button btnresetperjalanan;
    @FXML
    private Button btnresetrute;
    @FXML
    private Button btnsimpanrute;
    @FXML
    private TextField txtidrute;
    @FXML
    private TextField txturut;
    @FXML
    private TextField txtwaktu;
    @FXML
    private TextField txtjarak;
    @FXML
    private TableView<StasiunModel> tbvstasiunasal;
    @FXML
    private TableView<StasiunModel> tbvstasiuntujuan;
    @FXML
    private ComboBox<String> cmbidperjalanan;
    @FXML
    private Button btnsimpanperjalanan;
    @FXML
    private TextField txtidperjalanan;
    @FXML
    private ComboBox<String> cmbidkereta;

    public void initialize() {
        showDataStasiunAsal();
        showDataStasiunTujuan();
        loadIdPerjalananToComboBox();
        loadIdKeretaToComboBox();
        // Validasi input hanya angka untuk waktu dan urut
        txtwaktu.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                txtwaktu.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });

        txturut.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                txturut.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });

        // Validasi input hanya angka desimal untuk jarak
        txtjarak.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*\\.?\\d*")) {
                txtjarak.setText(newValue.replaceAll("[^\\d.]", ""));
            }
        });
    }

    public void showDataStasiunAsal() {
        ObservableList<StasiunModel> dataAsal = FXMLDocumentController.dtstasiun.Load();
        if (dataAsal != null) {
            tbvstasiunasal.getColumns().clear();
            tbvstasiunasal.getItems().clear();

            TableColumn<StasiunModel, String> colId = new TableColumn<>("ID Stasiun");
            colId.setCellValueFactory(new PropertyValueFactory<>("idstasiun"));
            tbvstasiunasal.getColumns().add(colId);

            TableColumn<StasiunModel, String> colNama = new TableColumn<>("Nama Stasiun");
            colNama.setCellValueFactory(new PropertyValueFactory<>("namastasiun"));
            tbvstasiunasal.getColumns().add(colNama);

            TableColumn<StasiunModel, String> colKota = new TableColumn<>("Kota");
            colKota.setCellValueFactory(new PropertyValueFactory<>("kota"));
            tbvstasiunasal.getColumns().add(colKota);

            TableColumn<StasiunModel, String> colStatus = new TableColumn<>("Status");
            colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
            tbvstasiunasal.getColumns().add(colStatus);

            tbvstasiunasal.setItems(dataAsal);
        } else {
            Alert a = new Alert(Alert.AlertType.ERROR, "Data Stasiun Asal kosong", ButtonType.OK);
            a.showAndWait();
        }
    }

    public void showDataStasiunTujuan() {
        ObservableList<StasiunModel> dataTujuan = FXMLDocumentController.dtstasiun.Load();
        if (dataTujuan != null) {
            tbvstasiuntujuan.getColumns().clear();
            tbvstasiuntujuan.getItems().clear();

            TableColumn<StasiunModel, String> colId = new TableColumn<>("ID Stasiun");
            colId.setCellValueFactory(new PropertyValueFactory<>("idstasiun"));
            tbvstasiuntujuan.getColumns().add(colId);

            TableColumn<StasiunModel, String> colNama = new TableColumn<>("Nama Stasiun");
            colNama.setCellValueFactory(new PropertyValueFactory<>("namastasiun"));
            tbvstasiuntujuan.getColumns().add(colNama);

            TableColumn<StasiunModel, String> colKota = new TableColumn<>("Kota");
            colKota.setCellValueFactory(new PropertyValueFactory<>("kota"));
            tbvstasiuntujuan.getColumns().add(colKota);

            TableColumn<StasiunModel, String> colStatus = new TableColumn<>("Status");
            colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
            tbvstasiuntujuan.getColumns().add(colStatus);

            tbvstasiuntujuan.setItems(dataTujuan);
        } else {
            Alert a = new Alert(Alert.AlertType.ERROR, "Data Stasiun Tujuan kosong", ButtonType.OK);
            a.showAndWait();
        }
    }

    private void loadIdPerjalananToComboBox() {
        try {
            // Membuka koneksi ke database
            Koneksi con = new Koneksi();
            con.bukaKoneksi();
            Statement stmt = con.dbKoneksi.createStatement();

            // Query untuk mendapatkan data idperjalanan
            ResultSet rs = stmt.executeQuery("SELECT idperjalanan FROM perjalanan");

            // ObservableList untuk menyimpan data
            ObservableList<String> idPerjalananList = FXCollections.observableArrayList();

            // Tambahkan data ke list
            while (rs.next()) {
                idPerjalananList.add(rs.getString("idperjalanan"));
            }
            con.tutupKoneksi();

            // Atur data ke ComboBox
            cmbidperjalanan.setItems(idPerjalananList);
            // Tambahkan event handler setelah data berhasil di-load
            cmbidperjalanan.setOnAction(event -> {
                String selectedId = cmbidperjalanan.getSelectionModel().getSelectedItem();
                if (selectedId != null) {
                    System.out.println("ID Perjalanan yang dipilih: " + selectedId);
                    // Lakukan sesuatu dengan ID Perjalanan yang dipilih
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR, "Gagal memuat data idperjalanan: " + e.getMessage(), ButtonType.OK);
            alert.showAndWait();
        }
    }

    private void loadIdKeretaToComboBox() {
        try {
            // Membuka koneksi ke database
            Koneksi con = new Koneksi();
            con.bukaKoneksi();
            Statement stmt = con.dbKoneksi.createStatement();

            // Query untuk mendapatkan data
            ResultSet rs = stmt.executeQuery("SELECT idkereta FROM kereta");

            // ObservableList untuk menyimpan data
            ObservableList<String> idKeretaList = FXCollections.observableArrayList();

            // Tambahkan data ke list
            while (rs.next()) {
                idKeretaList.add(rs.getString("idkereta"));
            }
            con.tutupKoneksi();

            // Atur data ke ComboBox
            cmbidkereta.setItems(idKeretaList);
            // Tambahkan event handler setelah data berhasil di-load
            cmbidkereta.setOnAction(event -> {
                String selectedId = cmbidkereta.getSelectionModel().getSelectedItem();
                if (selectedId != null) {
                    System.out.println("ID Kereta yang dipilih: " + selectedId);
                    // Lakukan sesuatu dengan ID Perjalanan yang dipilih
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR, "Gagal memuat data idperjalanan: " + e.getMessage(), ButtonType.OK);
            alert.showAndWait();
        }
    }

    @FXML
    private void resetperjalananclick(ActionEvent event) {
        txtidperjalanan.clear();
        cmbidkereta.getSelectionModel().clearSelection();
        cmbidkereta.setValue(null); // Untuk memastikan tampilannya juga kosong
    }

    @FXML
    private void resetruteclick(ActionEvent event) {
        txtidrute.clear();
        txturut.clear();
        txtwaktu.clear();
        txtjarak.clear();
        cmbidperjalanan.getSelectionModel().clearSelection();
        cmbidperjalanan.setValue(null);
        tbvstasiunasal.getSelectionModel().clearSelection();
        tbvstasiuntujuan.getSelectionModel().clearSelection();
    }

    @FXML
    private void simpanruteclick(ActionEvent event) {
        // Buat objek RuteModel
        RuteModel r = new RuteModel();

        // Ambil nilai dari TextField, ComboBox, dan TableView
        String idRute = txtidrute.getText();
        String idPerjalanan = cmbidperjalanan.getSelectionModel().getSelectedItem();
        StasiunModel stasiunAsal = tbvstasiunasal.getSelectionModel().getSelectedItem();
        StasiunModel stasiunTujuan = tbvstasiuntujuan.getSelectionModel().getSelectedItem();

        // Validasi input kosong
        if (idRute == null || idRute.trim().isEmpty()) {
            showAlertAndFocus("ID Rute tidak boleh kosong!", txtidrute);
            return;
        }

        if (idPerjalanan == null || idPerjalanan.trim().isEmpty()) {
            showAlertAndFocus("Perjalanan harus dipilih!", cmbidperjalanan);
            return;
        }

        if (stasiunAsal == null) {
            showAlertAndFocus("Stasiun Asal harus dipilih!", tbvstasiunasal);
            return;
        }

        if (stasiunTujuan == null) {
            showAlertAndFocus("Stasiun Tujuan harus dipilih!", tbvstasiuntujuan);
            return;
        }

        // Validasi dan konversi input waktu
        int waktu;
        try {
            waktu = Integer.parseInt(txtwaktu.getText());
        } catch (NumberFormatException e) {
            showAlertAndFocus("Waktu harus berupa angka!", txtwaktu);
            return;
        }

        // Validasi dan konversi input jarak
        double jarak;
        try {
            jarak = Double.parseDouble(txtjarak.getText());
        } catch (NumberFormatException e) {
            showAlertAndFocus("Jarak harus berupa angka desimal!", txtjarak);
            return;
        }

        // Validasi dan konversi input urut
        int urut;
        try {
            urut = Integer.parseInt(txturut.getText());
        } catch (NumberFormatException e) {
            showAlertAndFocus("Urut harus berupa angka!", txturut);
            return;
        }

        // Set nilai ke model
        r.setIdrute(idRute);
        r.setIdperjalanan(idPerjalanan);
        r.setIdstasiunasal(stasiunAsal.getIdstasiun());
        r.setIdstasiuntujuan(stasiunTujuan.getIdstasiun());
        r.setWaktu(waktu);
        r.setJarak(jarak);
        r.setUrut(urut);

        // Validasi duplikasi ID Rute
        if (!editdata && FXMLDocumentController.dtRute.validasi(idRute) > 0) {
            showAlertAndFocus("ID Rute sudah ada, gunakan ID lain!", txtidrute);
            return;
        }

        // Simpan atau update ke database
        FXMLDocumentController.dtRute.setRuteModel(r);
        if (editdata) {
            if (FXMLDocumentController.dtRute.update()) {
                showSuccess("Data berhasil diubah!", event);
            } else {
                showError("Data gagal diubah!");
            }
        } else {
            if (FXMLDocumentController.dtRute.insert()) {
                showSuccess("Data berhasil disimpan!", event);
            } else {
                showError("Data gagal disimpan!");
            }
        }
    }

    private void showAlertAndFocus(String message, Control control) {
        Alert alert = new Alert(Alert.AlertType.ERROR, message, ButtonType.OK);
        alert.showAndWait();
        control.requestFocus();
    }

    private void showSuccess(String message, ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, message, ButtonType.OK);
        alert.showAndWait();
        resetruteclick(event);
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR, message, ButtonType.OK);
        alert.showAndWait();
    }

    @FXML
    private void simpanperjalananclick(ActionEvent event) {
        // Buat objek PerjalananModel
        PerjalananModel n = new PerjalananModel();

        // Ambil nilai dari TextField dan ComboBox
        String idPerjalanan = txtidperjalanan.getText();
        String idKereta = cmbidkereta.getSelectionModel().getSelectedItem(); // Ambil nilai yang dipilih

        // Validasi input
        if (idPerjalanan == null || idPerjalanan.trim().isEmpty()) {
            Alert a = new Alert(Alert.AlertType.ERROR, "ID Perjalanan tidak boleh kosong!", ButtonType.OK);
            a.showAndWait();
            txtidperjalanan.requestFocus();
            return;
        }

        if (idKereta == null || idKereta.trim().isEmpty()) {
            Alert a = new Alert(Alert.AlertType.ERROR, "Kereta harus dipilih!", ButtonType.OK);
            a.showAndWait();
            cmbidkereta.requestFocus();
            return;
        }

        // Set nilai ke model
        n.setIdperjalanan(idPerjalanan);
        n.setIdkereta(idKereta);

        // Validasi duplikasi ID Perjalanan
        if (!editdata && FXMLDocumentController.dtPerjalanan.validasi(idPerjalanan) > 0) {
            Alert a = new Alert(Alert.AlertType.ERROR, "ID Perjalanan sudah ada, gunakan ID lain!", ButtonType.OK);
            a.showAndWait();
            txtidperjalanan.requestFocus();
            return;
        }

        // Simpan atau update ke database
        FXMLDocumentController.dtPerjalanan.setPerjalananModel(n);
        if (editdata) {
            // Jika sedang edit data
            if (FXMLDocumentController.dtPerjalanan.update()) {
                Alert a = new Alert(Alert.AlertType.INFORMATION, "Data berhasil diubah", ButtonType.OK);
                a.showAndWait();
                txtidperjalanan.setEditable(true);
                resetperjalananclick(event);
            } else {
                Alert a = new Alert(Alert.AlertType.ERROR, "Data gagal diubah", ButtonType.OK);
                a.showAndWait();
            }
        } else {
            // Insert data baru
            if (FXMLDocumentController.dtPerjalanan.insert()) {
                Alert a = new Alert(Alert.AlertType.INFORMATION, "Data berhasil disimpan", ButtonType.OK);
                a.showAndWait();
                resetperjalananclick(event);
            } else {
                Alert a = new Alert(Alert.AlertType.ERROR, "Data gagal disimpan", ButtonType.OK);
                a.showAndWait();
            }
        }
    }

    @FXML
    private void tbvcustclick(MouseEvent event) {
    }

}
