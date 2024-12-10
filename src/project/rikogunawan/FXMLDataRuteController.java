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

/**
 * FXML Controller class
 *
 * @author Aspire 3
 */
public class FXMLDataRuteController implements Initializable {

    @FXML
    private Button btnhapus;
    @FXML
    private TextField txtjaraktotal;
    @FXML
    private TableView<?> tbvperjalanan;
    @FXML
    private Button btnlast;
    @FXML
    private Button btnnext;
    @FXML
    private Button btnprev;
    @FXML
    private Button btnfirst;
    @FXML
    private TableView<RuteModel> tbvrute;
    @FXML
    private TextField txtidrute;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        showdata();
    }    

    public void showdata() {
        ObservableList<RuteModel> data = FXMLDocumentController.dtrute.Load();
        if (data != null) {
            tbvrute.getColumns().clear();
            tbvrute.getItems().clear();

            TableColumn col = new TableColumn("idrute");
            col.setCellValueFactory(new PropertyValueFactory<RuteModel, String>("idrute"));
            tbvrute.getColumns().addAll(col);
            
            col = new TableColumn("idstasiunasal");
            col.setCellValueFactory(new PropertyValueFactory<RuteModel, String>("idstasiunasal"));
            tbvrute.getColumns().addAll(col);

            col = new TableColumn("idstasiuntujuan");
            col.setCellValueFactory(new PropertyValueFactory<RuteModel, String>("idstasiuntujuan"));
            tbvrute.getColumns().addAll(col);
            
            tbvrute.setItems(data);
        } else {
            Alert a = new Alert(Alert.AlertType.ERROR, "Data kosong", ButtonType.OK);
            a.showAndWait();
            tbvrute.getScene().getWindow().hide();
        }
    }
    
    @FXML
    private void cariData(KeyEvent event) {
    }

    @FXML
    private void tableclick(MouseEvent event) {
    }

    @FXML
    private void firstclick(ActionEvent event) {
    }

    @FXML
    private void prevclick(ActionEvent event) {
    }

    @FXML
    private void nextclick(ActionEvent event) {
    }

    @FXML
    private void lastclick(ActionEvent event) {
    }

    @FXML
    private void hapusclick(ActionEvent event) {
    }
    
}
