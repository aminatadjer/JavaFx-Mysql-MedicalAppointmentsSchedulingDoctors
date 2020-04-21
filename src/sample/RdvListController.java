package sample;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import database.DataBaseHandler;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

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
                LocalDate date = rs.getDate("date").toLocalDate();
                LocalTime heure = rs.getTime("heure").toLocalTime();
                String objet = rs.getString("objet");
                String nomEtPrenomP=rs.getString("nomEtPrenomP");

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
 @FXML
    public void handleRdvDelete(){
        Rdv selectedRdv=tableRdv.getSelectionModel().getSelectedItem();
     if (selectedRdv == null) {
         Alert alert = new Alert(Alert.AlertType.ERROR);
         alert.setHeaderText(null);
         alert.setContentText("Vous n'avez selctionné aucun rendez-vous");
         alert.showAndWait();
         return;
     }
     DataBaseHandler handler= DataBaseHandler.getInstance();
     Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
     alert.setTitle("Supprimer Rendez-vous");
     alert.setContentText("Etes vous sure de vouloir supprimer le rendez-vous ?" );
     Optional<ButtonType> answer = alert.showAndWait();
     if (answer.get() == ButtonType.OK) {
         Boolean result = handler.getInstance().deleteRdv(selectedRdv);
         if (result) {
             Alert alert1 = new Alert(Alert.AlertType.ERROR);
             alert1.setHeaderText(null);
             alert1.setContentText("Le rendez-vous a été supprimé");
             alert1.showAndWait();

             list.remove(selectedRdv);
             return;
         } else {
             Alert alert1 = new Alert(Alert.AlertType.ERROR);
             alert1.setHeaderText(null);
             alert1.setContentText("Le rendez-vous n'a pas été suprpimé!! Error");
             alert1.showAndWait();
         }
     } else {
         Alert alert1 = new Alert(Alert.AlertType.ERROR);
         alert1.setHeaderText(null);
         alert1.setContentText("Suppression annulée");
         alert1.showAndWait();
     }
    }
    @FXML
    public void handlePrintRdv(){
        Rdv selectedForEdit = tableRdv.getSelectionModel().getSelectedItem();
        if (selectedForEdit == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Vous n'avez selctionné aucun rendez-vous");
            alert.showAndWait();
            return;
        }

    }
@FXML
public void handleRdvUpdate(ActionEvent event) {
        //Fetch the selected row
        Rdv selectedForEdit = tableRdv.getSelectionModel().getSelectedItem();
        if (selectedForEdit == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Vous n'avez selctionné aucun rendez-vous");
            alert.showAndWait();
            return;
        }
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("addRdv.fxml"));
            Parent parent = loader.load();
            RdvAddController controller = (RdvAddController) loader.getController();
            controller.inflateUI(selectedForEdit);
            Stage stage = new Stage(StageStyle.DECORATED);
            stage.setTitle("Edit Book");
            stage.setScene(new Scene(parent));
            stage.show();
            stage.setOnHiding((e) -> {
                handleRefresh(new ActionEvent());
            });

        } catch (IOException ex) {
            Logger.getLogger(MenuController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void handleRefresh(ActionEvent event) {
        loadData();
    }
}