package sample;

import database.DataBaseHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;

import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PatientAddController implements Initializable {
    @FXML
    public TextField textfieldnom;
    @FXML
    public TextField textfieldprenom;
    @FXML
    public TextField textfieldadresse;
    @FXML
    public TextField textfieldtel;
    @FXML
    public TextField textfieldmail;
    @FXML
    public TextField textfieldinfosMed;
    @FXML
    public Button buttonajouterPatient;
    @FXML
    public Button buttonannulerP;
    @FXML
    private AnchorPane rootPane;

    DataBaseHandler dataBaseHandler;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        dataBaseHandler=DataBaseHandler.getInstance();
    }

    @FXML
    private void addPatient(javafx.event.ActionEvent actionEvent) {
        String nomP = textfieldnom.getText();
        String prenomP = textfieldprenom.getText();
        String adresseP = textfieldadresse.getText();
        String telP = textfieldtel.getText();
        String mailP = textfieldmail.getText();
        String infosMedP = textfieldinfosMed.getText();


        if (nomP.isEmpty() || prenomP.isEmpty() || telP.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Veuillez remplir en moins le nom et le prénom et le numéro de téléphone");
            alert.showAndWait();
            return;
        }
        String query = "INSERT INTO `patient` (`nom`, `prenom`, `adresse`, `tel`, `mail`, `infosMed`) VALUES ('" + nomP + "', '" + prenomP + "', '" + adresseP + "', '" + telP + "', '" + mailP + "', '" + infosMedP + "')";

        if (dataBaseHandler.execAction(query)) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("Patient ajouté correctement");
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

    private void retrieveBooks() {
        String query = "Select nom from patient";
        ResultSet res = dataBaseHandler.execQuery(query);
        try {
            while (res.next()) {
                String nomtext = res.getString("nom");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            Logger.getLogger(PatientAddController.class.getName()).log(Level.SEVERE, null, e);
        }
    }
}
