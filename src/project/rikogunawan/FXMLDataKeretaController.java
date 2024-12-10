/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package project.rikogunawan;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Aspire 3
 */
public class FXMLDataKeretaController implements Initializable {

    @FXML
    private ImageView imgkereta;
    @FXML
    private TextField searchkereta;
    @FXML
    private Button btnsearch;
    @FXML
    private TableView<KeretaModel> tbvkereta;
    @FXML
    private Button btnfirst;
    @FXML
    private Button btnprevious;
    @FXML
    private Button btnnext;
    @FXML
    private Button btnlast;
    @FXML
    private Button btninsert;
    @FXML
    private Button btnupdate;
    @FXML
    private Button btndelete;
    @FXML
    private Button btnexit;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        showdata();
        tbvkereta.getSelectionModel().selectFirst();
        showgambar();
    }

    public void showdata() {
        ObservableList<KeretaModel> data = FXMLDocumentController.dtkereta.Load();
        if (data != null) {
            tbvkereta.getColumns().clear();
            tbvkereta.getItems().clear();

            TableColumn col = new TableColumn("idkereta");
            col.setCellValueFactory(new PropertyValueFactory<KeretaModel, String>("idkereta"));
            tbvkereta.getColumns().addAll(col);
            col = new TableColumn("namakereta");
            col.setCellValueFactory(new PropertyValueFactory<KeretaModel, String>("namakereta"));
            tbvkereta.getColumns().addAll(col);
            col = new TableColumn("kecepatan");
            col.setCellValueFactory(new PropertyValueFactory<KeretaModel, String>("kecepatan"));
            tbvkereta.getColumns().addAll(col);
            col = new TableColumn("tipe");
            col.setCellValueFactory(new PropertyValueFactory<KeretaModel, String>("tipe"));
            tbvkereta.getColumns().addAll(col);

//            col = new TableColumn("promo");
//            col.setCellValueFactory(new PropertyValueFactory<KeretaModel, String>("promo"));
//            tbvkereta.getColumns().addAll(col);

            tbvkereta.setItems(data);
        } else {
            Alert a = new Alert(Alert.AlertType.ERROR, "Data kosong", ButtonType.OK);
            a.showAndWait();
            tbvkereta.getScene().getWindow().hide();
        }
    }

    @FXML
    private void cariData(KeyEvent event) {
        KeretaModel s = new KeretaModel();
        String key = searchkereta.getText();
        if (key != "") {
            ObservableList<KeretaModel> data = FXMLDocumentController.dtkereta.CariKereta(key, key);
            if (data != null) {
                tbvkereta.getColumns().clear();
                tbvkereta.getItems().clear();
                TableColumn col = new TableColumn("idkereta");
                col.setCellValueFactory(new PropertyValueFactory<KeretaModel, String>("idkereta"));
                tbvkereta.getColumns().addAll(col);
                col = new TableColumn("namakereta");
                col.setCellValueFactory(new PropertyValueFactory<KeretaModel, String>("namakereta"));
                tbvkereta.getColumns().addAll(col);
                col = new TableColumn("gambar");
                col.setCellValueFactory(new PropertyValueFactory<KeretaModel, String>("gambar"));
                tbvkereta.getColumns().addAll(col);
                col = new TableColumn("tipe");
                col.setCellValueFactory(new PropertyValueFactory<KeretaModel, String>("tipe"));
                tbvkereta.getColumns().addAll(col);
//                col = new TableColumn("promo");
//                col.setCellValueFactory(new PropertyValueFactory<KeretaModel, String>("promo"));
//                tbvkereta.getColumns().addAll(col);
                col = new TableColumn("kecepatan");
                col.setCellValueFactory(new PropertyValueFactory<KeretaModel, String>("kecepatan"));
                tbvkereta.getColumns().addAll(col);

                tbvkereta.setItems(data);
            } else {
                Alert a = new Alert(Alert.AlertType.ERROR, "Data kosong", ButtonType.OK);
                a.showAndWait();
                tbvkereta.getScene().getWindow().hide();
            }
        } else {
            showdata();
        }
    }

    public void showgambar() {
        Image gambar = null;
        try {
            gambar = new Image(new FileInputStream(tbvkereta.getSelectionModel().getSelectedItem().getGambar()));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FXMLDataKeretaController.class.getName()).log(Level.SEVERE, null, ex);
        }

        imgkereta.setImage(gambar);
    }

    @FXML
    private void exitclick(ActionEvent event) {
        btnexit.getScene().getWindow().hide();
    }

    @FXML
    private void deleteclick(ActionEvent event) {
        KeretaModel s = new KeretaModel();
        s = tbvkereta.getSelectionModel().getSelectedItem();
        Alert a = new Alert(Alert.AlertType.CONFIRMATION, "Mau dihapus?", ButtonType.YES, ButtonType.NO);
        a.showAndWait();
        if (a.getResult() == ButtonType.YES) {
            if (FXMLDocumentController.dtkereta.delete(s.getIdkereta())) {
                Alert b = new Alert(Alert.AlertType.INFORMATION, "Data berhasil dihapus", ButtonType.OK);
                b.showAndWait();
            } else {
                Alert b = new Alert(Alert.AlertType.ERROR, "Data gagal dihapus", ButtonType.OK);
                b.showAndWait();
            }
            showdata();
            firstclick(event);
        }

    }

    @FXML
    private void updateclick(ActionEvent event) {
        KeretaModel s = new KeretaModel();
        s = tbvkereta.getSelectionModel().getSelectedItem();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLInputKereta.fxml"));
            Parent root = (Parent) loader.load();
            FXMLInputKeretaController isidt = (FXMLInputKeretaController) loader.getController();
            isidt.execute(s);
            Scene scene = new Scene(root);
            Stage stg = new Stage();
            stg.initModality(Modality.APPLICATION_MODAL);
            stg.setResizable(false);
            stg.setIconified(false);
            stg.setScene(scene);
            stg.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
        showdata();
        firstclick(event);

    }

    @FXML
    private void insertclick(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLInputKereta.fxml"));
            Parent root = (Parent) loader.load();
            Scene scene = new Scene(root);
            Stage stg = new Stage();
            stg.initModality(Modality.APPLICATION_MODAL);
            stg.setResizable(false);
            stg.setIconified(false);
            stg.setScene(scene);
            stg.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
        showdata();
        firstclick(event);
    }

    @FXML
    private void lastclick(ActionEvent event) {
        tbvkereta.getSelectionModel().selectLast();
        showgambar();
        tbvkereta.requestFocus();
    }

    @FXML
    private void nextclick(ActionEvent event) {
        tbvkereta.getSelectionModel().selectNext();
        showgambar();
        tbvkereta.requestFocus();
    }

    @FXML
    private void previousclick(ActionEvent event) {
        tbvkereta.getSelectionModel().selectPrevious();
        showgambar();
        tbvkereta.requestFocus();
    }

    @FXML
    private void firstclick(ActionEvent event) {
        tbvkereta.getSelectionModel().selectFirst();
        showgambar();
        tbvkereta.requestFocus();
    }

    @FXML
    private void tableclick(MouseEvent event) {
        showgambar();
    }

    @FXML
    private void searchclick(ActionEvent event) {
        KeretaModel s = new KeretaModel();
        String key = searchkereta.getText();
        if (key != "") {
            ObservableList<KeretaModel> data = FXMLDocumentController.dtkereta.CariKereta(key, key);
            if (data != null) {
                tbvkereta.getColumns().clear();
                tbvkereta.getItems().clear();
                TableColumn col = new TableColumn("idkereta");
                col.setCellValueFactory(new PropertyValueFactory<KeretaModel, String>("idkereta"));
                tbvkereta.getColumns().addAll(col);
                col = new TableColumn("namakereta");
                col.setCellValueFactory(new PropertyValueFactory<KeretaModel, String>("namakereta"));
                tbvkereta.getColumns().addAll(col);
                col = new TableColumn("gambar");
                col.setCellValueFactory(new PropertyValueFactory<KeretaModel, String>("gambar"));
                tbvkereta.getColumns().addAll(col);
                col = new TableColumn("tipe");
                col.setCellValueFactory(new PropertyValueFactory<KeretaModel, String>("tipe"));
                tbvkereta.getColumns().addAll(col);
//                col = new TableColumn("promo");
//                col.setCellValueFactory(new PropertyValueFactory<KeretaModel, String>("promo"));
//                tbvkereta.getColumns().addAll(col);
                col = new TableColumn("kecepatan");
                col.setCellValueFactory(new PropertyValueFactory<KeretaModel, String>("kecepatan"));
                tbvkereta.getColumns().addAll(col);
                tbvkereta.setItems(data);
            } else {
                Alert a = new Alert(Alert.AlertType.ERROR, "Data kosong", ButtonType.OK);
                a.showAndWait();
                tbvkereta.getScene().getWindow().hide();
            }
        } else {
            showdata();
        }
    }
}
