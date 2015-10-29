/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package databaseconnector;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class OracleJDBC {

    public static void main(String[] argv) {

        System.out.println("-------- Oracle JDBC Connection Testing ------");

        try {

            Class.forName("oracle.jdbc.driver.OracleDriver");

        } catch (ClassNotFoundException e) {

            System.out.println("Where is your Oracle JDBC Driver?");
            e.printStackTrace();
            return;

        }

        System.out.println("Oracle JDBC Driver Registered!");

        Connection connection = null;

        try {

            connection = DriverManager.getConnection(
                    "jdbc:oracle:thin:@129.194.102.102:1521:xe", "I2B2HUGpilot",
                    "mypassword");

        } catch (SQLException e) {

            System.out.println("Connection Failed! Check output console");
            e.printStackTrace();
            return;

        }

        if (connection != null) {
            System.out.println("You made it, take control your database now!");
        } else {
            System.out.println("Failed to make connection!");
        }

        try {
            Statement stmt = connection.createStatement();
            String query = "SELECT A.CONCEPT_CD, Count (1) as CNT \n"
                    + "		FROM OBSERVATION_FACT A, PATIENT_DIMENSION S\n"
                    + "		WHERE A.PATIENT_NUM = S.PATIENT_NUM AND A.CONCEPT_CD "
                    + "         like 'CIM10:%' Group by A.CONCEPT_CD having COUNT(1)>0 Order by count(1) desc";
            ResultSet result = stmt.executeQuery(query);

            while (result.next()) {
                System.out.println(result.getString("CONCEPT_CD")+ "    "+ result.getString("CNT"));
            }
            System.out.println(result);

        } catch (SQLException ex) {
            Logger.getLogger(OracleJDBC.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
