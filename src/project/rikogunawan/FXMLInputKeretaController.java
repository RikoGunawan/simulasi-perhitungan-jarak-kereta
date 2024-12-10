package project.rikogunawan;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;

public class FXMLInputKeretaController implements Initializable {

    boolean editdata = false;

    @FXML
    private Button btngambar;
    @FXML
    private TextField txtgambar;
    @FXML
    private TextField txtnamakereta;
    @FXML
    private TextField txtkecepatan;
    @FXML
    private Button btnsave;
    @FXML
    private Button btncancel;
    @FXML
    private Button btnexit;
    @FXML
    private TextField txtidkereta;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public void execute(KeretaModel d) {
        if (!d.getIdkereta().isEmpty()) {
            editdata = true;
            txtidkereta.setText(d.getIdkereta());
            txtnamakereta.setText(d.getNamakereta());
            txtgambar.setText(d.getGambar());
            txtkecepatan.setText(String.valueOf(d.getKecepatan()));
            txtidkereta.setEditable(false);
            txtnamakereta.requestFocus();
        }
    }

    @FXML
    private void exitclick(ActionEvent event) {
        btnexit.getScene().getWindow().hide();
    }

    @FXML
    private void cancelclick(ActionEvent event) {
        txtidkereta.setText("");
        txtnamakereta.setText("");
        txtkecepatan.setText("");
        txtgambar.setText("");
        txtidkereta.requestFocus();
    }

    @FXML
    private void saveclick(ActionEvent event) {
        KeretaModel n = new KeretaModel();
        n.setIdkereta(txtidkereta.getText());
        n.setNamakereta(txtnamakereta.getText());
        n.setGambar(txtgambar.getText());
        n.setKecepatan(Double.parseDouble(txtkecepatan.getText()));

        FXMLDocumentController.dtkereta.setKeretaModel(n);
        if (editdata) {
            if (FXMLDocumentController.dtkereta.update()) {
                Alert a = new Alert(Alert.AlertType.INFORMATION, "Data berhasil diubah", ButtonType.OK);
                a.showAndWait();
                txtidkereta.setEditable(true);
                cancelclick(event);
            } else {
                Alert a = new Alert(Alert.AlertType.ERROR, "Data gagal diubah", ButtonType.OK);
                a.showAndWait();
            }
        } else if (FXMLDocumentController.dtkereta.validasi(n.getIdkereta()) <= 0) {
            if (FXMLDocumentController.dtkereta.insert()) {
                Alert a = new Alert(Alert.AlertType.INFORMATION, "Data berhasil disimpan", ButtonType.OK);
                a.showAndWait();
                cancelclick(event);
            } else {
                Alert a = new Alert(Alert.AlertType.ERROR, "Data gagal disimpan", ButtonType.OK);
                a.showAndWait();
            }
        } else {
            Alert a = new Alert(Alert.AlertType.ERROR, "Data sudah ada", ButtonType.OK);
            a.showAndWait();
            txtidkereta.requestFocus();
        }

    }

    @FXML
    private void gambarclick(ActionEvent event) {

        FileChooser fileChooser = new FileChooser();

        // Menambahkan filter file untuk gambar
        FileChooser.ExtensionFilter extFilterJPG = new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.jpg");
        FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.png");
        fileChooser.getExtensionFilters().addAll(extFilterJPG, extFilterPNG);

        // Membuka dialog file
        File selectedFile = fileChooser.showOpenDialog(null);

        if (selectedFile != null) {
            // Menampilkan path file di TextField
            txtgambar.setText(selectedFile.getAbsolutePath());
        } else {
            System.out.println("File tidak dipilih.");
        }
    }

}
