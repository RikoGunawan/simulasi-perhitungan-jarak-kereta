/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package project.rikogunawan;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

public class FXMLDataPerjalananController implements Initializable {

    @FXML
    private TextField txtidperjalanan;
    @FXML
    private TableView<PerjalananModel> tbvperjalanan;
    @FXML
    private TableView<RuteModel> tbvrute;
    @FXML
    private Button btnfirst;
    @FXML
    private Button btnprev;
    @FXML
    private Button btnnext;
    @FXML
    private Button btnlast;
    @FXML
    private Button btnhapus;
    @FXML
    private TextField txtjaraktotal;
    @FXML
    private TextField txtwaktutotal;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        showDataPerjalanan();

        // Validasi jika tabel tidak kosong
        if (!tbvperjalanan.getItems().isEmpty()) {
            tbvperjalanan.getSelectionModel().selectFirst();
            txtidperjalanan.setText(tbvperjalanan.getSelectionModel().getSelectedItem().getIdperjalanan());
            showDataRute();
        } else {
            // Jika data kosong, bersihkan teks dan tabel terkait
            txtidperjalanan.clear();
            tbvrute.getColumns().clear();
            tbvrute.getItems().clear();

            // Tampilkan alert untuk memberi tahu pengguna
            Alert a = new Alert(Alert.AlertType.WARNING, "Data perjalanan kosong. Mohon tambahkan data terlebih dahulu.", ButtonType.OK);
            a.showAndWait();
        }

        //Listener untuk Update Jarak + Waktu RealTime
        tbvperjalanan.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                showDataRute();
            }
        });

    }

    public void showDataPerjalanan() {
        ObservableList<PerjalananModel> data = FXMLDocumentController.dtPerjalanan.Load();
        if (data != null) {
            tbvperjalanan.getColumns().clear();
            tbvperjalanan.getItems().clear();

            TableColumn col = new TableColumn("ID Perjalanan");
            col.setCellValueFactory(new PropertyValueFactory<PerjalananModel, String>("idperjalanan"));
            tbvperjalanan.getColumns().addAll(col);

            col = new TableColumn("Kereta");
            col.setCellValueFactory(new PropertyValueFactory<PerjalananModel, String>("idkereta"));
            tbvperjalanan.getColumns().addAll(col);

            col = new TableColumn("Waktu Total");
            col.setCellValueFactory(new PropertyValueFactory<PerjalananModel, Integer>("waktutotal"));
            tbvperjalanan.getColumns().addAll(col);

            col = new TableColumn("Jarak Total");
            col.setCellValueFactory(new PropertyValueFactory<PerjalananModel, Double>("jaraktotal"));
            tbvperjalanan.getColumns().addAll(col);

            col = new TableColumn("Kota Asal");
            col.setCellValueFactory(new PropertyValueFactory<PerjalananModel, String>("kotaasal"));
            tbvperjalanan.getColumns().addAll(col);

            col = new TableColumn("Kota Tujuan");
            col.setCellValueFactory(new PropertyValueFactory<PerjalananModel, String>("kotatujuan"));
            tbvperjalanan.getColumns().addAll(col);

            tbvperjalanan.setItems(data);
        } else {
            Alert a = new Alert(Alert.AlertType.ERROR, "Data perjalanan kosong", ButtonType.OK);
            a.showAndWait();
        }
    }
// Metode untuk menghitung total jarak

    private double hitungJarakTotal(ObservableList<RuteModel> data) {
        return data.stream().mapToDouble(RuteModel::getJarak).sum();
    }

// Metode untuk memperbarui total jarak ke database
    private void updateJarakTotal(String idPerjalanan, double totalJarak) {
        FXMLDocumentController.dtPerjalanan.updateJarakTotal(idPerjalanan, totalJarak);
    }

    private double hitungWaktuTotal(String idKereta, double jarakTotal) {
        double kecepatan = FXMLDocumentController.dtkereta.getKecepatanKereta(idKereta);
        if (kecepatan <= 0) {
            throw new IllegalArgumentException("Kecepatan kereta tidak valid!");
        }
        return jarakTotal / kecepatan; // Waktu dalam jam
    }

    private void updateWaktuTotal(String idPerjalanan, double waktuTotal) {
        FXMLDocumentController.dtPerjalanan.updateWaktuTotal(idPerjalanan, waktuTotal);
    }

    private String formatWaktu(double waktuDalamMenit) {
        int totalMenit = (int) waktuDalamMenit;
        int jam = totalMenit / 60; // Hitung jam
        int menit = totalMenit % 60; // Hitung sisa menit
        return String.format("%d jam %d menit", jam, menit);
    }

    public void showDataRute() {
        if (tbvperjalanan.getSelectionModel().getSelectedItem() != null) {
            // Ambil ID Perjalanan yang dipilih
            String idperjalanan = tbvperjalanan.getSelectionModel().getSelectedItem().getIdperjalanan();
            ObservableList<RuteModel> data = FXMLDocumentController.dtRute.Load(idperjalanan);

            if (data != null) {
                // Bersihkan kolom dan item sebelumnya
                tbvrute.getColumns().clear();
                tbvrute.getItems().clear();

                // Tambahkan kolom ke TableView
                TableColumn<RuteModel, String> col = new TableColumn<>("ID Rute");
                col.setCellValueFactory(new PropertyValueFactory<>("idrute"));
                tbvrute.getColumns().addAll(col);

                col = new TableColumn<>("Urutan ke-");
                col.setCellValueFactory(new PropertyValueFactory<>("urut"));
                tbvrute.getColumns().addAll(col);

                col = new TableColumn<>("Waktu");
                col.setCellValueFactory(new PropertyValueFactory<>("waktu"));
                tbvrute.getColumns().addAll(col);

                col = new TableColumn<>("Jarak");
                col.setCellValueFactory(new PropertyValueFactory<>("jarak"));
                tbvrute.getColumns().addAll(col);

                col = new TableColumn<>("Nama Stasiun Asal");
                col.setCellValueFactory(new PropertyValueFactory<>("namastasiunasal"));
                tbvrute.getColumns().addAll(col);

                col = new TableColumn<>("Nama Stasiun Tujuan");
                col.setCellValueFactory(new PropertyValueFactory<>("namastasiuntujuan"));
                tbvrute.getColumns().addAll(col);

                // Set item ke TableView
                tbvrute.setItems(data);

                // Hitung total jarak
                double totalJarak = data.stream().mapToDouble(RuteModel::getJarak).sum();
                txtjaraktotal.setText(String.format("%.2f km", totalJarak));

                // Update total jarak ke database secara realtime
                FXMLDocumentController.dtPerjalanan.updateJarakTotal(idperjalanan, totalJarak);

                // Hitung waktu total secara realtime berdasarkan kecepatan kereta
                PerjalananModel perjalanan = tbvperjalanan.getSelectionModel().getSelectedItem();
                double kecepatanKereta = FXMLDocumentController.dtkereta.getKecepatanKereta(perjalanan.getIdkereta()); // Ambil kecepatan kereta
                double waktuTotalMenit = (totalJarak / kecepatanKereta) * 60; // Waktu dalam menit
                perjalanan.setWaktutotal((int) waktuTotalMenit); // Set waktu total dalam menit

                // Update waktu total ke database
                FXMLDocumentController.dtPerjalanan.updateWaktuTotal(idperjalanan, waktuTotalMenit);

                // Tampilkan waktu dalam format jam dan menit
                txtwaktutotal.setText(formatWaktu(waktuTotalMenit));

            } else {
                // Kosongkan TableView jika tidak ada data
                tbvrute.getColumns().clear();
                tbvrute.getItems().clear();
                txtjaraktotal.setText("0.00 km");
                txtwaktutotal.setText("0 Jam 0 Menit");
            }
        } else {
            // Jika tidak ada perjalanan yang dipilih, kosongkan TableView dan total jarak
            tbvrute.getColumns().clear();
            tbvrute.getItems().clear();
            txtjaraktotal.setText("0.00 km");
            txtwaktutotal.setText("0 Jam 0 Menit");
        }
    }

    @FXML
    private void firstclick(ActionEvent event) {
        tbvperjalanan.getSelectionModel().selectFirst();
        tbvperjalanan.requestFocus();
        txtidperjalanan.setText(tbvperjalanan.getSelectionModel().getSelectedItem().getIdperjalanan());
        showDataRute();
    }

    @FXML
    private void prevclick(ActionEvent event) {
        tbvperjalanan.getSelectionModel().selectPrevious();
        tbvperjalanan.requestFocus();
        txtidperjalanan.setText(tbvperjalanan.getSelectionModel().getSelectedItem().getIdperjalanan());
        showDataRute();
    }

    @FXML
    private void nextclick(ActionEvent event) {
        tbvperjalanan.getSelectionModel().selectNext();
        tbvperjalanan.requestFocus();
        txtidperjalanan.setText(tbvperjalanan.getSelectionModel().getSelectedItem().getIdperjalanan());
        showDataRute();
    }

    @FXML
    private void lastclick(ActionEvent event) {
        tbvperjalanan.getSelectionModel().selectLast();
        tbvperjalanan.requestFocus();
        txtidperjalanan.setText(tbvperjalanan.getSelectionModel().getSelectedItem().getIdperjalanan());
        showDataRute();
    }

    @FXML
    private void hapusclick(ActionEvent event) {
        Alert a = new Alert(Alert.AlertType.CONFIRMATION,
                "Data Perjalanan akan dihapus beserta rute terkait, lanjutkan?", ButtonType.YES, ButtonType.NO);
        a.showAndWait();

        if (a.getResult() == ButtonType.YES) {
            boolean hapusPerjalanan = FXMLDocumentController.dtPerjalanan.deleteall(txtidperjalanan.getText());

            if (hapusPerjalanan) {
                Alert b = new Alert(Alert.AlertType.INFORMATION,
                        "Data Perjalanan dan Rute terkait berhasil dihapus", ButtonType.OK);
                b.showAndWait();
            } else {
                Alert b = new Alert(Alert.AlertType.ERROR,
                        "Penghapusan gagal", ButtonType.OK);
                b.showAndWait();
            }
        }

        showDataPerjalanan();

        // Reset seleksi atau clear tabel jika kosong
        if (tbvperjalanan.getItems().isEmpty()) {
            txtidperjalanan.clear();
            tbvrute.getItems().clear();
        } else {
            tbvperjalanan.getSelectionModel().selectFirst();
            txtidperjalanan.setText(tbvperjalanan.getSelectionModel().getSelectedItem().getIdperjalanan());
            showDataRute();
        }
    }

    @FXML
    private void tableclick(MouseEvent event) {
    }

    @FXML
    private void pilihdata(MouseEvent event) {
        txtidperjalanan.setText(tbvperjalanan.getSelectionModel().getSelectedItem().getIdperjalanan());
        showDataRute();
    }

    @FXML
    private void cariData(KeyEvent event) {
    }
}
