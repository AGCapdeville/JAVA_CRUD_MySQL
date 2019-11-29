package app;

import java.util.Scanner;
import java.sql.Connection;
import java.sql.Statement;

public class DeleteOrder {

    public DeleteOrder() {
    }

    public void deleteOrder(Connection connection, Scanner scanner, GUI display, ExistsQuery existsQuery) {

        display.orders(connection);
        System.out.println("=   What Order Would You Like To Delete?    =");

        String orderID = "";
        int id = -1;
        while (true) {
            System.out.print("= Order ID: ");
            orderID = scanner.next();
            if (existsQuery.check(connection, "orders", "o_id", orderID)) {
                try {
                    id = Integer.parseInt(orderID);
                } catch (NumberFormatException nfe) {
                    System.err.println("[ Not a valid Order ID: " + nfe.getMessage() + " ]\n");
                }

                try {
                    Statement statement = null;
                    statement = connection.createStatement();
                    statement.execute("DELETE FROM line WHERE o_id='" + id + "';");
                    statement.execute("DELETE FROM orders WHERE o_id='" + id + "';");
                } catch (Exception e) {
                    System.err.println("[ DELETE ERROR:Exception:" + e.getMessage() + " ]\n");
                }
                break;
            } else {
                System.err.println("[ INVALID Order ID, ( Enter A Valid Order ID ) ]\n");
            }
        }

    }

}