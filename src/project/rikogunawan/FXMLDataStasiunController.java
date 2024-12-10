/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package project.rikogunawan;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
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
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Aspire 3
 */
public class FXMLDataStasiunController implements Initializable {

    @FXML
    private Button btnsearch;
    @FXML
    private TextField searchstasiun;
    @FXML
    private Button btnexit;
    @FXML
    private Button btndelete;
    @FXML
    private Button btnupdate;
    @FXML
    private Button btninsert;
    @FXML
    private Button btnlast;
    @FXML
    private Button btnnext;
    @FXML
    private Button btnprevious;
    @FXML
    private Button btnfirst;
    @FXML
    private TableView<StasiunModel> tbvstasiun;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        showdata();
    }

    public void showdata() {
        ObservableList<StasiunModel> data = FXMLDocumentController.dtstasiun.Load();
        if (data != null) {
            tbvstasiun.getColumns().clear();
            tbvstasiun.getItems().clear();

            TableColumn col = new TableColumn("idstasiun");
            col.setCellValueFactory(new PropertyValueFactory<StasiunModel, String>("idstasiun"));
            tbvstasiun.getColumns().addAll(col);
            col = new TableColumn("namastasiun");
            col.setCellValueFactory(new PropertyValueFactory<StasiunModel, String>("namastasiun"));
            tbvstasiun.getColumns().addAll(col);
            col = new TableColumn("alamat");
            col.setCellValueFactory(new PropertyValueFactory<StasiunModel, String>("alamat"));
            tbvstasiun.getColumns().addAll(col);
            col = new TableColumn("kota");
            col.setCellValueFactory(new PropertyValueFactory<StasiunModel, String>("kota"));
            tbvstasiun.getColumns().addAll(col);

            col = new TableColumn("status");
            col.setCellValueFactory(new PropertyValueFactory<StasiunModel, String>("status"));
            tbvstasiun.getColumns().addAll(col);

            tbvstasiun.setItems(data);
        } else {
            Alert a = new Alert(Alert.AlertType.ERROR, "Data kosong", ButtonType.OK);
            a.showAndWait();
            tbvstasiun.getScene().getWindow().hide();
        }
        
    }

    @FXML
    private void cariData(KeyEvent event) {
        StasiunModel s = new StasiunModel();
        String key = searchstasiun.getText();
        if (key != "") {
            ObservableList<StasiunModel> data = FXMLDocumentController.dtstasiun.CariStasiun(key, key);
            if (data != null) {
                tbvstasiun.getColumns().clear();
                tbvstasiun.getItems().clear();
                TableColumn col = new TableColumn("idstasiun");
                col.setCellValueFactory(new PropertyValueFactory<StasiunModel, String>("idstasiun"));
                tbvstasiun.getColumns().addAll(col);
                col = new TableColumn("namastasiun");
                col.setCellValueFactory(new PropertyValueFactory<StasiunModel, String>("namastasiun"));
                tbvstasiun.getColumns().addAll(col);
                col = new TableColumn("alamat");
                col.setCellValueFactory(new PropertyValueFactory<StasiunModel, String>("alamat"));
                tbvstasiun.getColumns().addAll(col);
                col = new TableColumn("kota");
                col.setCellValueFactory(new PropertyValueFactory<StasiunModel, String>("kota"));
                tbvstasiun.getColumns().addAll(col);
                col = new TableColumn("status");
                col.setCellValueFactory(new PropertyValueFactory<StasiunModel, String>("status"));
                tbvstasiun.getColumns().addAll(col);
                tbvstasiun.setItems(data);
            } else {
                Alert a = new Alert(Alert.AlertType.ERROR, "Data kosong", ButtonType.OK);
                a.showAndWait();
                tbvstasiun.getScene().getWindow().hide();
            }
        } else {
            showdata();
        }
    }

    @FXML
    private void firstclick(ActionEvent event) {
        tbvstasiun.getSelectionModel().selectFirst();
        tbvstasiun.requestFocus();
    }

    @FXML
    private void previousclick(ActionEvent event) {
        tbvstasiun.getSelectionModel().selectPrevious();
        tbvstasiun.requestFocus();
    }

    @FXML
    private void nextclick(ActionEvent event) {
        tbvstasiun.getSelectionModel().selectNext();
        tbvstasiun.requestFocus();
    }

    @FXML
    private void lastclick(ActionEvent event) {
        tbvstasiun.getSelectionModel().selectLast();
        tbvstasiun.requestFocus();
    }

    @FXML
    private void insertclick(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLInputStasiun.fxml"));
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
    private void updateclick(ActionEvent event) {
        StasiunModel s = new StasiunModel();
        s = tbvstasiun.getSelectionModel().getSelectedItem();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLInputStasiun.fxml"));
            Parent root = (Parent) loader.load();
            FXMLInputStasiunController isidt = (FXMLInputStasiunController) loader.getController();
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
    private void deleteclick(ActionEvent event) {
        StasiunModel s = new StasiunModel();
        s = tbvstasiun.getSelectionModel().getSelectedItem();
        Alert a = new Alert(Alert.AlertType.CONFIRMATION, "Mau dihapus?", ButtonType.YES, ButtonType.NO);
        a.showAndWait();
        if (a.getResult() == ButtonType.YES) {
            if (FXMLDocumentController.dtstasiun.delete(s.getIdstasiun())) {
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
    private void exitclick(ActionEvent event) {
        btnexit.getScene().getWindow().hide();
    }

    @FXML
    private void searchclick(ActionEvent event) {
        StasiunModel s = new StasiunModel();
        String key = searchstasiun.getText();
        if (key != "") {
            ObservableList<StasiunModel> data = FXMLDocumentController.dtstasiun.CariStasiun(key, key);
            if (data != null) {
                tbvstasiun.getColumns().clear();
                tbvstasiun.getItems().clear();
                TableColumn col = new TableColumn("idstasiun");
                col.setCellValueFactory(new PropertyValueFactory<StasiunModel, String>("idstasiun"));
                tbvstasiun.getColumns().addAll(col);
                col = new TableColumn("namastasiun");
                col.setCellValueFactory(new PropertyValueFactory<StasiunModel, String>("namastasiun"));
                tbvstasiun.getColumns().addAll(col);
                col = new TableColumn("alamat");
                col.setCellValueFactory(new PropertyValueFactory<StasiunModel, String>("alamat"));
                tbvstasiun.getColumns().addAll(col);
                col = new TableColumn("kota");
                col.setCellValueFactory(new PropertyValueFactory<StasiunModel, String>("kota"));
                tbvstasiun.getColumns().addAll(col);
                col = new TableColumn("status");
                col.setCellValueFactory(new PropertyValueFactory<StasiunModel, String>("status"));
                tbvstasiun.getColumns().addAll(col);
                tbvstasiun.setItems(data);
            } else {
                Alert a = new Alert(Alert.AlertType.ERROR, "Data kosong", ButtonType.OK);
                a.showAndWait();
                tbvstasiun.getScene().getWindow().hide();
            }
        } else {
            showdata();
        }
    }

}
