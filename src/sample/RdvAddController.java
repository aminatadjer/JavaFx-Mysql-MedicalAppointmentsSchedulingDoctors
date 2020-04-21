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

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
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
    private Boolean isInEditMode = Boolean.FALSE;
    DataBaseHandler dataBaseHandler=DataBaseHandler.getInstance();

int id;
String nometprenom;
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
        if (isInEditMode){
            handleEditOperation();
            return;
        }
        String q="Select nom,prenom from patient where id="+idPatientR.toString();

        ResultSet rs = dataBaseHandler.execQuery(q);
        int cpt=0;
        String nomEtPrenomP="";
        try {
            while (rs.next()) {
                String nom=rs.getString("nom");
                String prenom=rs.getString("prenom");
                nomEtPrenomP=nom+" "+prenom;
                cpt++;

            }
        } catch (SQLException ex) {
            Logger.getLogger(PatientAddController.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (cpt==0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Cet Id de patient n'existe pas, veuillez vérifier la table des patients");
            alert.showAndWait();
            return;
        }
        String query = "INSERT INTO `rdv` (`idPatient`, `nomEtPrenomP`, `date`,`heure`, `objet`) VALUES ('" + idPatientR + "', '" + nomEtPrenomP + "','" + dateR + "','" + heureR + "', '" + objetR + "')";

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
    public void inflateUI(Rdv rdv) {
        id=rdv.getId();
        idPatient.setText(rdv.getIdPatient().toString());
        nometprenom=rdv.getNomEtPrenomP();
        date.setValue(rdv.getDate());
        heure.setValue(rdv.getHeure());
        objet.setText(rdv.getObjet());
        idPatient.setEditable(false);
        isInEditMode = Boolean.TRUE;
    }
    private void handleEditOperation() {
        Rdv rdv = new Rdv(id,Integer.parseInt(idPatient.getText()),nometprenom,date.getValue(),heure.getValue(),objet.getText());
        if (dataBaseHandler.updateAppointment(rdv)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Modification réussie");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Modification echouée");
            alert.showAndWait();
        }
    }
}


