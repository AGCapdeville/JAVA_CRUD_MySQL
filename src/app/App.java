package app;

import java.util.Scanner;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;



public class App {

    public static void main(String args[]) throws FileNotFoundException {
        Connection con = null;
        Scanner readingInput = new Scanner(System.in);
        Login login = new Login();
        Menu menu = new Menu();
        
        try {
            // --- ESTABLISH CONNECTION TO DB --- //
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            String uid="root"; 
            String pass=""; 
            con = DriverManager.getConnection("jdbc:mysql:///saleco_capdeville?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", uid, pass);
            if (!con.isClosed())
                System.out.println("\n [Connected to MySQL server] ");

            // --- LOGIN CREDENTIALS & then MENU --- //
            if (login.login(con, readingInput)){
                menu.menu(con, readingInput);
            }

        }
        catch (Exception e) {
            System.err.println("[Conn:Exception: " + e.getMessage() + " ]");
        }
        finally {
            try {
                if (con != null) {
                    con.close();
                	System.out.println("[Disconnected from MySQL server]");
                }
            } 
            catch (SQLException e) {
                System.err.println("[Disc:Exception:" + e.getMessage()+"]");
            }
        }
        readingInput.close();
    }

}