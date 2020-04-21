package database;

import sample.Rdv;

import javax.swing.*;
import java.sql.*;
import java.util.Properties;

public class DataBaseHandler {
    private static final String DATABASE_DRIVER = "com/mysql/jdbc/Driver";
    private static DataBaseHandler handler;
    private static final String DB_URL = "jdbc:mysql://localhost:3306/alog";
    private static Connection conn = null;
    private static Statement stmt = null;
    private static Properties properties;

    public static DataBaseHandler getInstance() {
        if (handler == null) {
            handler = new DataBaseHandler();
        }
        return handler;
    }

    private DataBaseHandler() {
        createConnection();
    }

    private static Properties getProperties() {
        if (properties == null) {
            properties = new Properties();
            properties.setProperty("user", "root");
            properties.setProperty("password", "");
            properties.setProperty("MaxPooledStatements", "250");
        }
        return properties;
    }

    private static void createConnection() {

        try {
            conn = DriverManager.getConnection(DB_URL, "root", "");
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Cant load database", "Database Error", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }
    }

    public ResultSet execQuery(String query) {
        ResultSet result;
        try {
            stmt = conn.createStatement();
            result = stmt.executeQuery(query);
        } catch (SQLException ex) {
            System.out.println("Exception at execQuery:dataHandler" + ex.getLocalizedMessage());
            return null;
        } finally {
        }
        return result;
    }

    public boolean execAction(String qu) {
        try {
            stmt = conn.createStatement();
            stmt.execute(qu);
            return true;
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error:" + ex.getMessage(), "Error Occured", JOptionPane.ERROR_MESSAGE);
            System.out.println("Exception at execQuery:dataHandler" + ex.getLocalizedMessage());
            return false;
        } finally {
        }
    }

    public Boolean deleteRdv(Rdv selectedRdv) {
        try {
            String deleteStatement = "DELETE FROM rdv WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(deleteStatement);
            String id=selectedRdv.getId().toString();
            stmt.setString(1,id);
            int res = stmt.executeUpdate();
            if (res == 1) {
                return true;
            }
        }
        catch (SQLException ex) {

        }
        return false;
    }
    public boolean updateAppointment(Rdv rdv) {
        try {
            String update = "UPDATE rdv SET date=?, heure=?, objet=? WHERE ID=?";
            PreparedStatement stmt = conn.prepareStatement(update);
            stmt.setString(1, rdv.getDate().toString());
            stmt.setString(2, rdv.getHeure().toString());
            stmt.setString(3, rdv.getObjet());
            stmt.setString(4, rdv.getId().toString());
            int res = stmt.executeUpdate();
            return (res > 0);
        }
        catch (SQLException ex) {

        }
        return false;
    }

}
