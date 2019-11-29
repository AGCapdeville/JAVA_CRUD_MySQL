package app;

import java.util.Arrays;
import java.util.Scanner;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.io.Console;

public class Login {

    public Login() {
    }

    public Boolean login(Connection connection, Scanner s) {
        System.out.println("\n= = = = = = = = = = LOGIN: = = = = = = = = = =\n");
        String username = "";

        System.out.print("\n[username:]");
        username = s.next();

        if (pass(username, connection)) {
            return true;
        } else {
            System.out.println("[ INVALID USERNAME OR PASSWORD ]");
        }

        return false;
    }

    private boolean pass(String username, Connection connection) {

        char[] password = new char[12];
        Console cons;

        if ((cons = System.console()) != null && (password = cons.readPassword("[%s]", "password:")) != null) {
            
            StringBuilder pass = new StringBuilder("");
            pass.append(password);
            String parse = ""+pass;

            if (chkCredentials(connection, username, parse)) {
                System.out.println("\n [credentials accepted] \n");
                return true;
            } else {
                System.out.println("\n [credentials rejected] \n");
            }

            Arrays.fill(password, ' ');
        }
        return false;

    }

    private Boolean chkCredentials(Connection connection, String username, String password) {

        try {
            Statement statement = null;
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select u.* from saleco_capdeville.users u");

            while (resultSet.next()) {
                String user = resultSet.getString("u_id");
                String pass = resultSet.getString("u_password");
                if (user.contentEquals(username) && pass.contentEquals(password))
                    return true;
            }
        } catch (Exception e) {
            System.err.println("[Conn:Credentials:Exception:" + e.getMessage() + "]");
        }
        return false;
    }

}