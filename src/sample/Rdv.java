package sample;

import java.sql.Date;
import java.sql.Time;

public class Rdv {
    private final int id;
    private final int idPatient;
    private final String nomEtPrenomP;
    private final Date date;
    private final Time heure;
    private final String objet;

    public Rdv(int id, int idPatient, String nomEtPrenomPatient, Date date, Time heure, String objet) {
        this.id = id;
        this.idPatient = idPatient;
        this.nomEtPrenomP = nomEtPrenomPatient;
        this.date = date;
        this.heure = heure;
        this.objet = objet;
    }

    public int getId() {
        return id;
    }

    public int getIdPatient() {
        return idPatient;
    }

    public Date getDate() {
        return date;
    }

    public Time getHeure() {
        return heure;
    }

    public String getNomEtPrenomP() {
        return nomEtPrenomP;
    }

    public String getObjet() {
        return objet;
    }
}
