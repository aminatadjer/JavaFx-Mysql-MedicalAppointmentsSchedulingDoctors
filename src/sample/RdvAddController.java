package sample;

import com.jfoenix.controls.JFXTimePicker;
import database.DataBaseHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;

import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.control.DatePicker;
import java.net.URL;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RdvAddController implements Initializable {
    @FXML
    public TextField idPatient;
    @FXML
    public TextField objet;
    @FXML
    public DatePicker date;
    @FXML
    public JFXTimePicker heure;
    @FXML
    public Button ajouterRdv;
    @FXML
    public Button annulerRdv;
    @FXML
    private AnchorPane rootPane;

    DataBaseHandler dataBaseHandler;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        dataBaseHandler=DataBaseHandler.getInstance();
    }

    @FXML
    private void addRdv(javafx.event.ActionEvent actionEvent) {
        String idPatientR = idPatient.getText();
        String objetR = objet.getText();
        LocalDate dateR = date.getValue();
        LocalTime heureR=heure.getValue();


        if (idPatientR.isEmpty() || objetR.isEmpty() || dateR.toString().isEmpty() || heureR.toString().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Veuillez remplir tous les champs");
            alert.showAndWait();
            return;
        }
        String query = "INSERT INTO `rdv` (`idPatient`, `date`,`heure`, `objet`) VALUES ('" + idPatientR + "', '" + dateR + "','" + heureR + "', '" + objetR + "')";
        System.out.println(query);
        if (dataBaseHandler.execAction(query)) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("Rendez-vous ajouté correctement");
            alert.showAndWait();
        } else { //Erreur
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Erreur!!");
            alert.showAndWait();
        }
    }

    @FXML
    private void cancel(javafx.event.ActionEvent event) {
        Stage stage = (Stage) rootPane.getScene().getWindow();
        stage.close();
    }

}
