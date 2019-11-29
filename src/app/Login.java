package app;

import java.util.Scanner;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;

public class Login {

    public Login() {
    }

    public Boolean login(Connection con, Scanner s) {
        System.out.println("\n= = = = = = = = = = LOGIN: = = = = = = = = = =\n");
        String username = "";
        String password = "";
        String q = "q!";
        boolean quit = false;
        while (!quit) {
            System.out.println("= If you would like to quit, just enter '" + q + "' =\n");

            System.out.print("= username: ");
            username = s.next();
            quit = username.contentEquals(q);
            if (quit)
                break;
            System.out.print("= password: ");
            password = s.next();
            quit = password.contentEquals(q);
            if (quit)
                break;

            if (chkCredentials(con, username, password)) {
                System.out.println("\n [credentials accepted] \n");
                return true;
            } else {
                System.out.println("\n [credentials rejected] \n");
            }
        }
        return false;
    }

    private Boolean chkCredentials(Connection con, String username, String password) {

        try {
            Statement statement = null;
            statement = con.createStatement();
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