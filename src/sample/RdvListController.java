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
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import database.DataBaseHandler;
import javafx.stage.Stage;

public class RdvListController implements Initializable {
    @FXML
    public AnchorPane rootPane;
    @FXML
    public TableView<Rdv> tableRdv;
    @FXML
    public TableColumn<Rdv,Integer> id;
    @FXML
    public TableColumn<Rdv,Integer> idPatient;
    @FXML
    public TableColumn<Rdv,String> nomEtPrenomP;
    @FXML
    public TableColumn<Rdv, LocalDate> date;
    @FXML
    public TableColumn<Rdv, LocalTime> heure;
    @FXML
    public TableColumn<Rdv,String> objet;


    ObservableList<Rdv> list = FXCollections.observableArrayList();


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initCol();
        loadData();
    }

    private Stage getStage() {
        return (Stage) tableRdv.getScene().getWindow();
    }
    private void loadData() {
        list.clear();
        DataBaseHandler handler=DataBaseHandler.getInstance();
        String qu = "SELECT * FROM rdv";
        ResultSet rs = handler.execQuery(qu);
        try {
            while (rs.next()) {
                int id=rs.getInt("id");
                int idPatient  = rs.getInt("idPatient");
                Date date = rs.getDate("date");
                Time heure = rs.getTime("heure");
                String objet = rs.getString("objet");
                String nomEtPrenomP=rs.getString("nomEtPrenomP");
                System.out.println(nomEtPrenomP);
                list.add(new Rdv(id,idPatient, nomEtPrenomP, date,heure,objet));
            }
        } catch (SQLException ex) {
            Logger.getLogger(PatientAddController.class.getName()).log(Level.SEVERE, null, ex);
        }

        tableRdv.setItems(list);
    }


    private void initCol() {
        id.setCellValueFactory(new PropertyValueFactory("id"));
        idPatient.setCellValueFactory(new PropertyValueFactory("idPatient"));
        nomEtPrenomP.setCellValueFactory(new PropertyValueFactory("nomEtPrenomP"));
        date.setCellValueFactory(new PropertyValueFactory("date"));
        heure.setCellValueFactory(new PropertyValueFactory("heure"));
        objet.setCellValueFactory(new PropertyValueFactory("objet"));

    }


}