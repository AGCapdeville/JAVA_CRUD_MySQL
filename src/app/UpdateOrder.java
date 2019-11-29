package app;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class UpdateOrder {

    public UpdateOrder() {
    }

    public void updateOrder(Connection connection, Scanner scanner, GUI display, ExistsQuery existsQuery) {

        display.orders(connection);

        String orderID = askOrderID(connection, scanner, display, existsQuery);
        display.invoice(connection, Integer.parseInt(orderID));

        String modLine = askLineID(connection, scanner, existsQuery, orderID);
        int newQuantity = askNewQuantity(scanner);

        double linePrice = getOrderLinePrice(connection, orderID, modLine);
        String oldEst = alterOrderEta(connection, orderID);

        DateHandler dh = new DateHandler();
        String newEst = dh.etaDate(oldEst);
        double newTotal = (newQuantity * linePrice);

        System.out.println("Line to be altered:" + modLine + " Price:" + linePrice + " * " + newQuantity + "\n");
        System.out.println("Old Order EST: " + oldEst + " New Order EST:" + newEst + "\n");

        Statement statement = null;
        try {
            statement = connection.createStatement();
            statement.execute("UPDATE orders SET  o_est = '" + newEst + "' WHERE o_id = '" + orderID + "';");
            statement.execute("UPDATE line SET l_quantity = '" + newQuantity + "', l_item_cost= '" + newTotal
                    + "' WHERE o_id = '" + orderID + "' AND l_id = '" + modLine + "';");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        System.out.println(
                "\n= = = = = = = = = = = = = = = = = = = = = = = UPDATED: = = = = = = = = = = = = = = = = = = = = = = = = =");
        display.invoice(connection, Integer.parseInt(orderID));
    }

    private String askOrderID(Connection connection, Scanner scanner, GUI display, ExistsQuery existsQuery) {
        System.out.println("= Which Order Would You Like To Update? ( Enter In Order ID )\n");
        String id = "";
        while (true) {
            id = scanner.next();
            if (existsQuery.check(connection, "orders", "o_id", id)) {
                return id;
            } else {
                System.err.println(" = INVALID Order ID, ( Enter A Valid Order ID )\n");
            }
        }
    }

    private String askLineID(Connection connection, Scanner scanner, ExistsQuery existsQuery, String orderID) {
        System.out.println("= Which Line Would You Like To Modify?      =");
        String modLine = "";
        while (true) {
            System.out.println("= Enter A Valid Line Number                 =");

            modLine = scanner.next();
            if (existsQuery.orderLine(connection, orderID, modLine)) {
                return modLine;
            }
        }
    }

    private int askNewQuantity(Scanner scanner) {
        System.out.println("= Enter New Quantity (Between 1 & 100)      =");
        int newQuantity = -1;
        while (true) {
            System.out.print("= New Quantity: ");
            String input = scanner.next();
            try {
                newQuantity = Integer.parseInt(input);
            } catch (Exception e) {
                System.err.println("[ INVALID INT SYNTAX: " + e.getMessage() + " ]\n");
            }
            if (newQuantity > 0 && newQuantity < 100) {
                return newQuantity;
            }
            System.err.println("[ INVALID QUANTITY (QUANTITY MUST BE BETWEEN 1 & 100) ]\n");
        }
    }

    private double getOrderLinePrice(Connection connection, String orderID, String line) {
        String priceStr = "";
        Statement statement = null;
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(
                    "SELECT * FROM line WHERE line.o_id = '" + orderID + "' AND line.l_id = '" + line + "';");
            while (resultSet.next()) {
                if (resultSet.getString("l_id").contentEquals(line)) {
                    priceStr = resultSet.getString("l_price");
                }
            }
            return Double.parseDouble(priceStr);
        } catch (Exception e) {
            System.err.println("[ getOrderLinePrice ERROR: Exception: " + e + " ]\n");
        }
        return -1;
    }

    private String alterOrderEta(Connection connection, String orderID) {
        try {
            Statement statement = null;
            statement = connection.createStatement();
            ResultSet q = statement
                    .executeQuery(" SELECT * FROM saleco_capdeville.orders WHERE o_id='" + orderID + "'; ");
            String orderEta = "";
            while (q.next()) {
                orderEta = q.getString("o_est");
            }
            return orderEta;
        } catch (Exception e) {
            System.err.println("[ Connected: Exception:" + e.getMessage() + "]\n");
        }
        return "null";
    }

}