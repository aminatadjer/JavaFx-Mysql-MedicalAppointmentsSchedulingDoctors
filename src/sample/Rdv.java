package sample;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.text.SimpleDateFormat;
import java.util.SimpleTimeZone;

public class Rdv {
    private final SimpleIntegerProperty id;
    private final SimpleIntegerProperty idPatient;
    private final SimpleDateFormat date;
    private final SimpleTimeZone heure;
    private final SimpleStringProperty objet;

    public Rdv(SimpleIntegerProperty id, SimpleIntegerProperty idPatient, SimpleDateFormat date, SimpleTimeZone heure, SimpleStringProperty objet) {
        this.id = id;
        this.idPatient = idPatient;
        this.date = date;
        this.heure = heure;
        this.objet = objet;
    }

    public int getId() {
        return id.get();
    }

    public SimpleIntegerProperty idProperty() {
        return id;
    }

    public int getIdPatient() {
        return idPatient.get();
    }

    public SimpleIntegerProperty idPatientProperty() {
        return idPatient;
    }

    public SimpleDateFormat getDate() {
        return date;
    }

    public SimpleTimeZone getHeure() {
        return heure;
    }

    public String getObjet() {
        return objet.get();
    }

    public SimpleStringProperty objetProperty() {
        return objet;
    }
}
