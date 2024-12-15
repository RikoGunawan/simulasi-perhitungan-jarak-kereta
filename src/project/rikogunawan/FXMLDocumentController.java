/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXML2.java to edit this template
 */
package project.rikogunawan;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author Aspire 3
 */
public class FXMLDocumentController implements Initializable {
    public static DBStasiun dtstasiun=new DBStasiun();
    public static DBKereta dtkereta=new DBKereta();
    public static DBRute dtRute=new DBRute();
    public static DBPerjalanan dtPerjalanan=new DBPerjalanan();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void DataStasiunClick(ActionEvent event) {
        try{  
            FXMLLoader loader=new FXMLLoader(getClass().getResource("FXMLDataStasiun.fxml"));    
            Parent root = (Parent)loader.load();
            Scene scene = new Scene(root);
            Stage stg=new Stage();
            stg.initModality(Modality.APPLICATION_MODAL);
            stg.setResizable(false);
            stg.setIconified(false);
            stg.setScene(scene);
            stg.show();        
        } catch (IOException e){   e.printStackTrace();   } 
    }

    @FXML
    private void InputStasiunClick(ActionEvent event) {
        try{
        FXMLLoader loader=new FXMLLoader(getClass().getResource("FXMLInputStasiun.fxml"));    
        Parent root = (Parent)loader.load();        
        Scene scene = new Scene(root);        
        Stage stg=new Stage();
        stg.initModality(Modality.APPLICATION_MODAL);
        stg.setResizable(false);        
        stg.setIconified(false);        
        stg.setScene(scene);
        stg.showAndWait();
        } catch (IOException e){   
            e.printStackTrace();   }
       
    }

    @FXML
    private void DataKeretaClick(ActionEvent event) {
        try{  
            FXMLLoader loader=new FXMLLoader(getClass().getResource("FXMLDataKereta.fxml"));    
            Parent root = (Parent)loader.load();
            Scene scene = new Scene(root);
            Stage stg=new Stage();
            stg.initModality(Modality.APPLICATION_MODAL);
            stg.setResizable(false);
            stg.setIconified(false);
            stg.setScene(scene);
            stg.show();        
        } catch (IOException e){   e.printStackTrace();   } 
    }
    
    @FXML
    private void InputKeretaClick(ActionEvent event) {
        try{
        FXMLLoader loader=new FXMLLoader(getClass().getResource("FXMLInputKereta.fxml"));    
        Parent root = (Parent)loader.load();        
        Scene scene = new Scene(root);        
        Stage stg=new Stage();
        stg.initModality(Modality.APPLICATION_MODAL);
        stg.setResizable(false);        
        stg.setIconified(false);        
        stg.setScene(scene);
        stg.showAndWait();
        } catch (IOException e){   
            e.printStackTrace();   }
       
    }

//    private void DataRuteClick(ActionEvent event) {
//        try{  
//            FXMLLoader loader=new FXMLLoader(getClass().getResource("FXMLDataRute.fxml"));    
//            Parent root = (Parent)loader.load();
//            Scene scene = new Scene(root);
//            Stage stg=new Stage();
//            stg.initModality(Modality.APPLICATION_MODAL);
//            stg.setResizable(false);
//            stg.setIconified(false);
//            stg.setScene(scene);
//            stg.show();        
//        } catch (IOException e){   e.printStackTrace();   } 
//    }


    @FXML
    private void DataPerjalananClick(ActionEvent event) {
        try{  
            FXMLLoader loader=new FXMLLoader(getClass().getResource("FXMLDataPerjalanan.fxml"));    
            Parent root = (Parent)loader.load();
            Scene scene = new Scene(root);
            Stage stg=new Stage();
            stg.initModality(Modality.APPLICATION_MODAL);
            stg.setResizable(false);
            stg.setIconified(false);
            stg.setScene(scene);
            stg.show();        
        } catch (IOException e){   e.printStackTrace();   } 
    }

    @FXML
private void SimulasiClick(ActionEvent event) {
    try {
        // Muat file FXML
        FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLSimulasi.fxml"));
        Parent root = loader.load();

        // Buat scene baru
        Scene scene = new Scene(root);

        // Atur stage baru
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL); // Membuka sebagai jendela modal
        stage.setResizable(false); // Nonaktifkan resize
        stage.setIconified(false); // Nonaktifkan minimize
        stage.setScene(scene);

        // Tampilkan stage
        stage.show();
    } catch (IOException e) {
        e.printStackTrace();
    }
}

    @FXML
    private void InputPerjalananClick(ActionEvent event) {
        try{
        FXMLLoader loader=new FXMLLoader(getClass().getResource("FXMLInputPerjalanan.fxml"));    
        Parent root = (Parent)loader.load();        
        Scene scene = new Scene(root);        
        Stage stg=new Stage();
        stg.initModality(Modality.APPLICATION_MODAL);
        stg.setResizable(false);        
        stg.setIconified(false);        
        stg.setScene(scene);
        stg.showAndWait();
        } catch (IOException e){   
            e.printStackTrace();   }
    }

}
