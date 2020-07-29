package databaseConnect;

import utils.Log;

import java.sql.*;

public class JDBCConnection {
    private static final String host = "192.168.100.185";
    private static final String url = "jdbc:mysql://" + host + ":3306/students";
    private static final String user = "senla";
    private static final String password = "senla2020";

    private static Connection con = null;
    private static Statement stmt = null;
    private static ResultSet rs = null;

    public static Connection connectToDB() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            Log.error(e.getMessage());
        }
        Log.info("Connect to DB " + url + " by user " + user);
        try {
            // opening database connection to MySQL server
            con = DriverManager.getConnection(url, user, password);
            Log.info("Connection to DB successful!");
        } catch (SQLException sqlEx) {
            Log.error("Connection to DB failed!");
            Log.error(sqlEx.getMessage());
        }

        return con;
    }

    public static void closeConnection() {
        if (con != null) {
            try {
                con.close();
                Log.info("Connection to DB closed");
            } catch (SQLException se) {
                Log.error(se.getMessage());
            }
        }

        if (stmt != null) {
            try {
                stmt.close();
                Log.info("Statement closed");
            } catch (SQLException se) {
                Log.error(se.getMessage());
            }
        }

        if (rs != null) {
            try {
                rs.close();
                Log.info("ResultSet closed");
            } catch (SQLException se) {
                Log.error(se.getMessage());
            }
        }
    }

    public static void createTable(String query) {
        try {
            stmt = connectToDB().prepareStatement(query);
            Log.info("Send request to DB: " + query);
            stmt.executeUpdate(query);
            Log.info("Table was created");
        } catch (SQLException se) {
            Log.error(se.getMessage());
        }
    }

    public static ResultSet selectFromDB(String query) {
        try {
            stmt = connectToDB().createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            Log.info("Send request to DB: " + query);
            rs = stmt.executeQuery(query);
            Log.info("Data from table was got");
        } catch (SQLException se) {
            Log.error(se.getMessage());
        }
        return rs;
    }

    public static void insertIntoDB(String query) {
        try {
            stmt = connectToDB().createStatement();
            Log.info("Send request to DB: " + query);
            stmt.executeUpdate(query);
            Log.info("New data inserted into table");
        } catch (SQLException se) {
            Log.error(se.getMessage());
        }
    }

    public static void updateInDB(String query) {
        try {
            stmt = connectToDB().createStatement();
            Log.info("Send request to DB: " + query);
            stmt.executeUpdate(query);
            Log.info("Data in table was updated");
        } catch (SQLException se) {
            Log.error(se.getMessage());
        }
    }

    public static void deleteFromDB(String query) {
        try {
            Log.info("Send request to DB: " + query);
            stmt = connectToDB().createStatement();
            stmt.executeUpdate(query);
            Log.info("Data from table was deleted");
        } catch (SQLException se) {
            Log.error(se.getMessage());
        }
    }

}
