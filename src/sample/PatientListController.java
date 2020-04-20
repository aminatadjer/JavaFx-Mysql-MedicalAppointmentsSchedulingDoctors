package sample;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import database.DataBaseHandler;
import javafx.stage.Stage;

public class PatientListController implements Initializable {
    @FXML
    public AnchorPane rootPane;
    @FXML
    public TableView<Patient> tablePatient;
    @FXML
    public TableColumn<Patient,Integer> id;
    @FXML
    public TableColumn<Patient,String> nom;
    @FXML
    public TableColumn<Patient,String> prenom;
    @FXML
    public TableColumn<Patient,String> adresse;
    @FXML
    public TableColumn<Patient,String> tel;
    @FXML
    public TableColumn<Patient,String> mail;
    @FXML
    public TableColumn<Patient,String> infosMed;

    ObservableList<Patient> list = FXCollections.observableArrayList();


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initCol();
        loadData();
    }

    private Stage getStage() {
        return (Stage) tablePatient.getScene().getWindow();
    }
    private void loadData() {
        list.clear();
        DataBaseHandler handler=DataBaseHandler.getInstance();
        String qu = "SELECT * FROM patient";
        ResultSet rs = handler.execQuery(qu);
        try {
            while (rs.next()) {
                int id=rs.getInt("id");
                String nom = rs.getString("nom");
                String prenom = rs.getString("prenom");
                String adresse = rs.getString("adresse");
                String tel = rs.getString("tel");
                String mail = rs.getString("mail");
                String infosMed = rs.getString("infosMed");


                list.add(new Patient(id,nom,prenom,adresse,tel,mail,infosMed));

            }
        } catch (SQLException ex) {
            Logger.getLogger(PatientAddController.class.getName()).log(Level.SEVERE, null, ex);
        }

        tablePatient.setItems(list);
    }


    private void initCol() {
        id.setCellValueFactory(new PropertyValueFactory("id"));
        nom.setCellValueFactory(new PropertyValueFactory("nom"));
        prenom.setCellValueFactory(new PropertyValueFactory("prenom"));
        adresse.setCellValueFactory(new PropertyValueFactory("adresse"));
        tel.setCellValueFactory(new PropertyValueFactory("tel"));
        mail.setCellValueFactory(new PropertyValueFactory("mail"));
        infosMed.setCellValueFactory(new PropertyValueFactory("infosMed"));


    }


}