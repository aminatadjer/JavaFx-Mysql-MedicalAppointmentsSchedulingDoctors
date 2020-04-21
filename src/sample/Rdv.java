package sample;

import javafx.fxml.Initializable;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;

public class Rdv {
    private final int id;
    private final int idPatient;
    private final String nomEtPrenomP;
    private final LocalDate date;
    private final LocalTime heure;
    private final String objet;

    public Rdv(int id, int idPatient, String nomEtPrenomPatient, LocalDate date, LocalTime heure, String objet) {
        this.id = id;
        this.idPatient = idPatient;
        this.nomEtPrenomP = nomEtPrenomPatient;
        this.date = date;
        this.heure = heure;
        this.objet = objet;
    }

    public Integer getId() {
        return id;
    }

    public Integer getIdPatient() {
        return idPatient;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getHeure() {
        return heure;
    }

    public String getNomEtPrenomP() {
        return nomEtPrenomP;
    }

    public String getObjet() {
        return objet;
    }

}
