package app;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;

public class GUI {

    public GUI() {
    }

    public void menu() {
        System.out.println("\n        = = = = = = MAIN MENU:= = = = = = = = =");
        System.out.println("        =   Enter: c, Create Order              =");
        System.out.println("        =   Enter: v, View Order                =");
        System.out.println("        =   Enter: u, Update Order              =");
        System.out.println("        =   Enter: d, Delete Order              =");
        System.out.println("        =   Enter: q, Exit                      =");
        System.out.println("        = = = = = = = = = = = = = = = = = = = = =\n");
    }

    public void products(Connection connection) {
        System.out.println("\n= = = = = = = = = = = = = = = = = PRODUCTS: = = = = = = = = = = = = = = = = =");
        try {
            Statement statement = null;
            statement = connection.createStatement();
            ResultSet q = statement.executeQuery(
                    "SELECT products.p_id, vendors.v_name, products.p_name, products.p_price FROM products JOIN vendors ON products.v_id = vendors.v_id");

            String pID = "";
            String vName = "";
            String pName = "";
            String pPrice = "";
            System.out.format("| %10s | %20s | %30s | %5s |\n", "Product ID", "Product", "Vendor", "Price");
            while (q.next()) {
                pID = q.getString("p_id");
                vName = q.getString("v_name");
                pName = q.getString("p_name");
                pPrice = q.getString("p_price");
                System.out.format("| %10s | %20s | %30s | %5s |\n", pID, pName, vName, pPrice);
            }

        } catch (Exception e) {
            System.err.println("[ Connected: Exception:" + e.getMessage() + " ]\n");
        }
        System.out.println("= = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =\n");

    }

    public void orders(Connection connection) {
        System.out.println(
                "\n= = = = = = = = = = = = = = = = = = = = = Current Orders: = = = = = = = = = = = = = = = = = = = = = = = ");
        try {
            Statement statement = null;
            statement = connection.createStatement();
            ResultSet orders = statement.executeQuery(
                    "SELECT orders.o_id, line.l_id, products.p_name, line.l_price, line.l_quantity, line.l_item_cost, orders.o_date, orders.o_est FROM orders JOIN line ON orders.o_id = line.o_id JOIN products ON line.p_id = products.p_id ORDER BY orders.o_id, line.l_id ");
            String orderId = "";
            String orderDate = "";
            String orderEst = "";
            String line = "";
            String prodName = "";
            String linePrice = "";
            String lineQ = "";
            String lineItemCost = "";
            System.out.format("|%8s |%-4s|%-32s", "Order ID", "Line", "Product");
            System.out.format("|%-7s|%-10s|%-9s", "Price", "Quantity", "Cost");
            System.out.format("| %10s | %10s |\n", "Order Date", "Order Est.");
            while (orders.next()) {
                orderId = orders.getString("o_id");
                line = orders.getString("l_id");
                prodName = orders.getString("p_name");
                linePrice = orders.getString("l_price");
                lineQ = orders.getString("l_quantity");
                lineItemCost = orders.getString("l_item_cost");
                orderDate = orders.getString("o_date");
                orderEst = orders.getString("o_est");

                System.out.format("|  %-7s|  %-2s|%-32s", orderId, line, prodName);
                System.out.format("|%-7s|%-10s|%-9s", linePrice, lineQ, lineItemCost);
                System.out.format("| %10s | %10s |\n", orderDate, orderEst);
            }

        } catch (Exception e) {
            System.err.println("[ Connected: Exception:" + e.getMessage() + " ]\n");
        }
        System.out.println(
                "= = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =\n");
    }

    public void invoice(Connection connection, int orderID) {
        System.out.println(
                "\n= = = = = = = = = = = = = = = = = = = = = = = INVOICE: = = = = = = = = = = = = = = = = = = = = = = = = =");

        try {
            Statement statement = null;
            statement = connection.createStatement();
            ResultSet orders = statement.executeQuery(
                    "SELECT orders.o_id, line.l_id, products.p_name, line.l_price, line.l_quantity, line.l_item_cost, orders.o_date, orders.o_est FROM orders JOIN line ON orders.o_id = line.o_id JOIN products ON line.p_id = products.p_id WHERE orders.o_id = '"
                            + orderID + "' ORDER BY orders.o_id, line.l_id;");
            String orderId = "";
            String orderDate = "";
            String orderEst = "";
            String line = "";
            String prodName = "";
            String linePrice = "";
            String lineQ = "";
            String lineItemCost = "";
            System.out.format("|%8s |%-4s|%-32s", "Order ID", "Line", "Product");
            System.out.format("|%-7s|%-10s|%-9s", "Price", "Quantity", "Cost");
            System.out.format("| %10s | %10s |\n", "Order Date", "Order Est.");
            while (orders.next()) {
                orderId = orders.getString("o_id");
                line = orders.getString("l_id");
                prodName = orders.getString("p_name");
                linePrice = orders.getString("l_price");
                lineQ = orders.getString("l_quantity");
                lineItemCost = orders.getString("l_item_cost");
                orderDate = orders.getString("o_date");
                orderEst = orders.getString("o_est");

                System.out.format("|  %-7s|  %-2s|%-32s", orderId, line, prodName);
                System.out.format("|%-7s|%-10s|%-9s", linePrice, lineQ, lineItemCost);
                System.out.format("| %10s | %10s |\n", orderDate, orderEst);

            }

        } catch (Exception e) {
            System.err.println("[ Connected: Exception:" + e.getMessage() + " ]\n");
        }

        System.out.println(
                "= = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =\n");

    }

    public void clearTerminal() {
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
    }

}