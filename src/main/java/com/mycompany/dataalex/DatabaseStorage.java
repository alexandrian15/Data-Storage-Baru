
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.dataalex;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author USER
 */
public class DatabaseStorage implements DataStorage {

    private Connection connection;

    public DatabaseStorage(String databasePath) {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:"
                    + databasePath);
            createDataTable();
        } catch (ClassNotFoundException | SQLException e) {
        }
    }

    private void createDataTable() throws SQLException {
        try ( Statement statement = connection.createStatement()) {
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS data (value TEXT)");
        }
    }

    public void writeData(String data) {
        try ( PreparedStatement statement = connection.prepareStatement("INSERT INTO data (value) VALUES (?)")) {
            statement.setString(1, data);
            statement.executeUpdate();
        } catch (SQLException e) {
        }
    }

    public String readData() {
        StringBuilder sb = new StringBuilder();
        try ( Statement statement = connection.createStatement();  ResultSet resultSet = statement.executeQuery("SELECT value FROM data")) {

            while (resultSet.next()) {
                sb.append(resultSet.getString("value"));
            }
        } catch (SQLException e) {
        }
        return sb.toString();
    }

//    private void createDataTable() {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
}
