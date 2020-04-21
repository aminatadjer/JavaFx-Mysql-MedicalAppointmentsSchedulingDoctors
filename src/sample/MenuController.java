package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MenuController implements Initializable {
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
    @FXML
    public void loadAddPatient(){
        loadWindow("addPatient.fxml", "Ajouter un nouveau patient");
    }
    @FXML
    public void loadAddRdv(){
        loadWindow("addRdv.fxml", "Ajouter un rendez-vous");
    }
    @FXML
    public void loadListPatients(){
        loadWindow("patientList.fxml", "Liste des patients");
    }
    @FXML
    public void loadListRdv(){
        loadWindow("rdvList.fxml", "Liste des rendez-vous");
    }
    void loadWindow(String loc, String title) {
        try {
            Parent parent = FXMLLoader.load(getClass().getResource(loc));
            Stage stage = new Stage(StageStyle.DECORATED);
            stage.setTitle(title);
            stage.setScene(new Scene(parent));
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
