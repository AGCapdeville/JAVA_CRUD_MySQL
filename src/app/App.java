package app;

import java.util.Scanner;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class App {

    public static void main(String args[]) throws FileNotFoundException {
        Connection connection = null;
        Scanner scanner = new Scanner(System.in);
        Login login = new Login();
        Menu menu = new Menu();

        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            String uid = "root";
            String pass = "";
            connection = DriverManager.getConnection(
                    "jdbc:mysql:///saleco_capdeville?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC",
                    uid, pass);
            if (!connection.isClosed())
                System.out.println("[ Connected to MySQL server ] \n");

            while (true) {

                if (login.login(connection, scanner)) {
                    menu.menu(connection, scanner);
                }
                System.out.println("\n = Would you like to continue?, Enter: Y(Yes) OR N(No) =\n");
                pass = scanner.next();
                if (pass.contains("N") | pass.contains("n")) {
                    break;
                }
            }

        } catch (Exception e) {
            System.err.println("[ Conn: Exception: " + e.getMessage() + " ]\n");
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                    System.out.println("[ Disconnected from MySQL server ]");
                }
            } catch (SQLException e) {
                System.err.println("[ Disc: Exception:" + e.getMessage() + " ]\n");
            }
        }
        scanner.close();
    }

}