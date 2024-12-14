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

    @Override
    public void initialize(URL url, ResourceBundle rb) {
//        showDataPerjalanan();
//        tbvperjalanan.getSelectionModel().selectFirst();
//        txtidperjalanan.setText(tbvperjalanan.getSelectionModel().getSelectedItem().getIdperjalanan());
//        showDataRute();
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

    public void showDataRute() {
        if (tbvperjalanan.getSelectionModel().getSelectedItem() != null) {
            String idperjalanan = tbvperjalanan.getSelectionModel().getSelectedItem().getIdperjalanan();
            ObservableList<RuteModel> data = FXMLDocumentController.dtRute.Load(idperjalanan);
            if (data != null) {
                tbvrute.getColumns().clear();
                tbvrute.getItems().clear();

                TableColumn col = new TableColumn("ID Rute");
                col.setCellValueFactory(new PropertyValueFactory<RuteModel, String>("idrute"));
                tbvrute.getColumns().addAll(col);

                col = new TableColumn("Urutan ke-");
                col.setCellValueFactory(new PropertyValueFactory<RuteModel, Integer>("urut"));
                tbvrute.getColumns().addAll(col);

                col = new TableColumn("Waktu");
                col.setCellValueFactory(new PropertyValueFactory<RuteModel, Integer>("waktu"));
                tbvrute.getColumns().addAll(col);

                col = new TableColumn("Jarak");
                col.setCellValueFactory(new PropertyValueFactory<RuteModel, Double>("jarak"));
                tbvrute.getColumns().addAll(col);

                col = new TableColumn("Nama Stasiun Asal");
                col.setCellValueFactory(new PropertyValueFactory<RuteModel, String>("namastasiunasal"));
                tbvrute.getColumns().addAll(col);

                col = new TableColumn("Nama Stasiun Tujuan");
                col.setCellValueFactory(new PropertyValueFactory<RuteModel, String>("namastasiuntujuan"));
                tbvrute.getColumns().addAll(col);

                tbvrute.setItems(data);
            } else {
                tbvrute.getColumns().clear();
                tbvrute.getItems().clear();
            }
        } else {
            tbvrute.getColumns().clear();
            tbvrute.getItems().clear();
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
