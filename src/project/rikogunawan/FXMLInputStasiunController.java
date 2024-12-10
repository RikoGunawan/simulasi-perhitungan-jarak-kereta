/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package project.rikogunawan;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Aspire 3
 */
public class FXMLInputStasiunController implements Initializable {

    boolean editdata = false;
    
    @FXML
    private Button btnexit;
    @FXML
    private Button btncancel;
    @FXML
    private Button btnsave;
    @FXML
    private TextField txtkota;
    @FXML
    private TextField txtalamat;
    @FXML
    private TextField txtidstasiun;
    @FXML
    private TextField txtstatus;
    @FXML
    private TextField txtnamastasiun;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    public void execute(StasiunModel d){
        if(!d.getIdstasiun().isEmpty()){
          editdata=true;
          txtidstasiun.setText(d.getIdstasiun());
          txtnamastasiun.setText(d.getNamastasiun());          
          txtalamat.setText(d.getAlamat());
          txtkota.setText(d.getKota());    
          txtstatus.setText(d.getStatus());  
          txtidstasiun.setEditable(false);          
          txtnamastasiun.requestFocus();         
        }
    }

    @FXML
    private void saveclick(ActionEvent event) {
        StasiunModel n=new StasiunModel();        
        n.setIdstasiun(txtidstasiun.getText());
        n.setNamastasiun(txtnamastasiun.getText());     
        n.setAlamat(txtalamat.getText());  
        n.setKota(txtkota.getText());  
        n.setStatus(txtstatus.getText());  
        
        FXMLDocumentController.dtstasiun.setStasiunModel(n);
        if(editdata){
            if(FXMLDocumentController.dtstasiun.update()){
               Alert a=new Alert(Alert.AlertType.INFORMATION,"Data berhasil diubah",ButtonType.OK);
               a.showAndWait();   
               txtidstasiun.setEditable(true);        
               cancelclick(event);                
            } else {               
                Alert a=new Alert(Alert.AlertType.ERROR,"Data gagal diubah",ButtonType.OK);
                a.showAndWait(); 
                }            
        }else if(FXMLDocumentController.dtstasiun.validasi(n.getIdstasiun())<=0){
            if(FXMLDocumentController.dtstasiun.insert()){
               Alert a=new Alert(Alert.AlertType.INFORMATION,"Data berhasil disimpan",ButtonType.OK);
               a.showAndWait();            
               cancelclick(event);
            } else {
               Alert a=new Alert(Alert.AlertType.ERROR,"Data gagal disimpan",ButtonType.OK);
               a.showAndWait();            
            }
        }else{Alert a=new Alert(Alert.AlertType.ERROR,"Data sudah ada",ButtonType.OK);
            a.showAndWait();
            txtidstasiun.requestFocus();
        }
    }

    @FXML
    private void cancelclick(ActionEvent event) {
        txtidstasiun.setText("");        
        txtnamastasiun.setText("");
        txtalamat.setText("");       
        txtkota.setText("");  
        txtstatus.setText("");  
        txtidstasiun.requestFocus();

    }

    @FXML
    private void exitclick(ActionEvent event) {
        btnexit.getScene().getWindow().hide();
    }
    
}
