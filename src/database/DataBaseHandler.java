package database;

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

    public DataBaseHandler() {
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
}
