package app;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class ExistsQuery {

    public ExistsQuery() {
    }

    public boolean check(Connection connection, String table, String coloumn, String field ) {
        try {
            Statement statement = null;
            statement = connection.createStatement();
            ResultSet q = statement.executeQuery("select p.* from saleco_capdeville." + table + " p");
            while (q.next()) {
                String checker = q.getString(coloumn);
                if (field.contentEquals(checker))
                    return true;
            }
        } catch (Exception e) {
            System.err.println("[Connected: Exception:" + e.getMessage() + "]");
        }
        return false;
    }

    public boolean orderLine(Connection connection, String orderID, String line) {
        try {
            Statement statement = null;
            statement = connection.createStatement();
            ResultSet q = statement.executeQuery(
                    "SELECT * FROM line WHERE line.o_id='" + orderID + "' AND line.l_id='" + line + "' ;");
            while (q.next()) {
                String check = q.getString("l_id");
                if (check.contentEquals(line)) {
                    return true;
                }
            }

        } catch (Exception e) {
            System.err.println("[ IN: checkLine, SQL: Exception:" + e.getMessage() + "]");
        }
        return false;
    }

}