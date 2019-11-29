package app;

import java.util.Scanner;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;

public class CreateOrder {

    public CreateOrder() {
    }

    public void createOrder(Connection connection, Scanner scanner, GUI display) {

        System.out.println("\n= = = = = = = = = = = = = = = = = NEW ORDER: = = = = = = = = = = = = = = = = =");
        int lines = askQuantity(scanner);
        String[] products = new String[lines];
        int[] amounts = new int[lines];

        display.products(connection);

        for (int i = 0; i < lines; i++) {
            System.out.println("\n= = = = = = = = = = = = = = = = = PRODUCT [" + i + "]: = = = = = = = = = = = = = = = = =");
            products[i] = askProductID(connection, scanner);
            amounts[i] = askAmount(scanner);
        }

        int newOrderID = newOrderId(connection);
        if (newOrderID == -1) {
            System.err.print("[ ERROR, new order id can not be created ]\n");
        } else {
            String[] linePrice = getPrices(products, lines, connection);
            DateHandler dateHandler = new DateHandler();

            String orderDate = dateHandler.newDate();
            String orderEta = "null";
            try {
                orderEta = dateHandler.etaDate(orderDate);
            } catch (Exception e) {
                System.err.println("[ DateHandler: etaDate: Exception: " + e + " ]\n");
            }

            pushOrder(connection, newOrderID, products, amounts, lines, linePrice, orderDate, orderEta);
            display.invoice(connection, newOrderID);

            System.out.println("= Enter any character and then press:'Enter/Return' to return to Main Menu =\n");
            scanner.next();
        }
    }

    private void pushOrder(Connection con, int newOrderID, String[] products, int[] amounts, int lines,
            String[] linePrice, String orderDate, String orderEta) {
        try {
            Statement statement = null;
            statement = con.createStatement();

            statement.execute("INSERT INTO saleco_capdeville.orders (o_id, o_date, o_est) VALUES ('" + newOrderID
                    + "', '" + orderDate + "', '" + orderEta + "');");

            for (int i = 0; i < lines; i++) {
                int a = amounts[i];
                double p = Double.parseDouble(linePrice[i]);
                double total = a * p;
                String sTotal = "" + total;
                statement.execute(
                        "INSERT INTO saleco_capdeville.line (l_id, o_id, p_id, l_price, l_quantity, l_item_cost) VALUES ('"
                                + i + "','" + newOrderID + "','" + products[i] + "','" + linePrice[i] + "','"
                                + amounts[i] + "','" + sTotal + "');");
            }

        } catch (Exception e) {
            System.err.println("[ Push Order ERROR: " + e.getMessage() + " ]\n");
        }

    }

    private String[] getPrices(String[] products, int lines, Connection connection) {
        String[] linePrice = new String[lines];
        try {
            Statement statement = null;
            statement = connection.createStatement();
            ResultSet result = statement.executeQuery("select p.* from saleco_capdeville.products p");
            int x = 0;
            while (result.next()) {
                String checker = result.getString("p_id");
                String price = result.getString("p_price");
                for (int j = 0; j < lines; j++) {
                    if (products[j].contentEquals(checker)) {
                        linePrice[x] = price;
                        x++;
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("[Connected: Exception:" + e.getMessage() + "]\n");
        }
        return linePrice;
    }

    private int newOrderId(Connection connection) {
        String latestID = "";
        int newID = -1;
        try {
            Statement statement = null;
            statement = connection.createStatement();
            ResultSet result = statement.executeQuery("SELECT orders.o_id FROM orders ORDER BY orders.o_id desc limit 1");
            while (result.next()) {
                latestID = result.getString("o_id");
            }
            try {
                newID = Integer.parseInt(latestID);
                return ++newID;
            } catch (NumberFormatException nfe) {
                System.err.println("[ Not a valid Number: " + nfe.getMessage() + " ]\n");
                return newID;
            }
        } catch (Exception e) {
            System.err.println("[ Gen new order ID: Exception:" + e.getMessage() + " ]\n");
            return newID;
        }
        
    }

    private int askQuantity(Scanner s) {
        System.out.println("\n=   How Many Products Would You Like To Order   =");
        String passed;
        int amount = -1;
        while (true) {
            System.out.print("= Quantity: ");
            passed = s.next();
            try {
                amount = Integer.parseInt(passed);
            } catch (NumberFormatException nfe) {
                System.err.println("[ Not a valid Number: " + nfe.getMessage()+" ]\n");
            }
            if (amount > 0 && amount < 10)
                break;
            System.err.println("[ Amount has to between 0 & 10 ]\n");
        }
        return amount;
    }

    private String askProductID(Connection connection, Scanner scanner) {
        System.out.println("\n=   What Would You Like To Order?               =");
        // System.out.println("= = = = = = = = = = = = = = = = = = = = = = =");
        String product = "";
        while (true) {
            System.out.print("= Product ID: ");
            product = scanner.next();
            if (productExists(product, connection))
                break;
            System.err.println("[ Invalid Product ID, Try Again... ]\n");
        }
        return product;
    }

    private int askAmount(Scanner s){
        System.out.println("\n=   How Many Would You Like To Order?           =");

        String passed = "";
        int amount = -1;
        
        while (true){
            System.out.print("= Amount: ");
            passed = s.next();

            try { amount = Integer.parseInt(passed); }
            catch (NumberFormatException nfe){ 
                System.err.println("[ Number Format ERROR : " + nfe.getMessage() +" ]\n" ); 
            }
            if (amount < 1000000000 && amount > 0){
                break;
            }
            System.err.println("[ Can't order 0 OR more than 10*10^8 ]\n");

        }
        return amount;
    }

    private boolean productExists(String product, Connection con) {
        try {
            Statement statement = null;
            statement = con.createStatement();
            ResultSet q = statement.executeQuery("select p.* from saleco_capdeville.products p");
            while (q.next()) {
                String checker = q.getString("p_id");
                if (product.contentEquals(checker))
                    return true;
            }
        } catch (Exception e) {
            System.err.println("[ Connected: Exception:" + e.getMessage() + " ]\n");
        }
        return false;
    }
}