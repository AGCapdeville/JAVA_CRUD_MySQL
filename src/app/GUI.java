package app;


import java.sql.Connection;
import java.sql.Statement;
import java.util.Scanner;
import java.sql.ResultSet;


public class GUI {

    public GUI() {
    }

    // - - - - - - - - - - GUI MENUS - - - - - - - - - - //
    public void popMenu(){
        System.out.println("\n= = = = = = MAIN MENU:= = = = = = = = =");
        System.out.println("=   Enter: c, Create Order              =");
        System.out.println("=   Enter: v, View Order                =");
        System.out.println("=   Enter: u, Update Order              =");
        System.out.println("=   Enter: d, Delete Order              =");
        System.out.println("=   Enter: q, Exit                      =");
        System.out.println("= = = = = = = = = = = = = = = = = = = = =\n");
    }

    public String newOrder(Connection con, Scanner s){
        System.out.println("\n= = = = = = = = = = = = = = = = = = = =");
        System.out.println("=     Enter a valid Product ID:       =");

        String product = s.next();
        while(true){
            if(productCheck(product, con)){
                break;
            }else{
                System.out.println("\n=   Invalid Product ID, Try Again...  =");
                product = s.next();
            }
        }
        return product;
    }

    public boolean productCheck(String product, Connection con){
        try{
            Statement statement = null;
            statement = con.createStatement();
            ResultSet q = statement.executeQuery("select p.* from saleco_capdeville.products p");
            while (q.next()) {
                String checker = q.getString("p_id");
                if(product.contentEquals(checker))
                    return true;
            }
        }catch(Exception e){
            System.err.println("[Connected: Exception:" + e.getMessage()+"]");
        }
        return false;
    }

    public boolean check(Connection connection, String field, String coloumn, String table){
        try{
            Statement statement = null;
            statement = connection.createStatement();
            ResultSet q = statement.executeQuery("select p.* from saleco_capdeville."+table+" p");
            while (q.next()) {
                String checker = q.getString(coloumn);
                if(field.contentEquals(checker))
                    return true;
            }
        }catch(Exception e){
            System.err.println("[Connected: Exception:" + e.getMessage()+"]");
        }
        return false;
    }

    public int checkInvoice(Connection connection, String table, String field, String product){
        int amount = -1;
        try{
            Statement statement = null;
            statement = connection.createStatement();
            ResultSet q = statement.executeQuery("SELECT * FROM line WHERE "+table+".o_id='"+field+"';");
            while (q.next()) {
                String checker = q.getString(field);
                String numberStr = q.getString("l_quantity");
                if(product.contentEquals(checker)){
                    amount = Integer.parseInt(numberStr);
                    break;
                }
            }
        }catch(Exception e){
            System.err.println("[Connected: Exception:" + e.getMessage()+"]");
        }
        return amount;
    }

    public boolean checkLine(Connection connection, String orderID, String line){
        try{
            Statement statement = null;
            statement = connection.createStatement();
            ResultSet q = statement.executeQuery("SELECT * FROM line WHERE line.o_id='"+orderID+"' AND line.l_id='"+line+"' ;");
            while(q.next()){
                String check = q.getString("l_id");
                if(check.contentEquals(line)){
                    return true;
                }
            }

        }catch(Exception e){
            System.err.println("[ IN: checkLine, SQL: Exception:" + e.getMessage()+"]");
        }
        return false;
    }

    public int howMany(Scanner s){
        String passed = s.next();
        int amount = -1;

        try { amount = Integer.parseInt(passed); }
        catch (NumberFormatException nfe){ 
            System.out.println("Not a valid Number: " + nfe.getMessage()); 
            System.out.println("Exiting Create Order..."); 
        }
        
        while (true){
            if (amount < 1000000000 && amount > 0){
                break;
            }
            System.out.println("\n=  Can't order 0 OR more than 10*10^8 =");
            passed = s.next();

            try { amount = Integer.parseInt(passed); }
            catch (NumberFormatException nfe){ 
                System.out.println("Not a valid number: " + nfe.getMessage()); 
                System.out.println("Exiting Create Order...");
            }   
        }
        return amount;
    }

    public void displayProducts(Connection con){
        try{
            Statement statement = null;
            statement = con.createStatement();
            ResultSet q = statement.executeQuery("SELECT products.p_id, vendors.v_name, products.p_name, products.p_price FROM products JOIN vendors ON products.v_id = vendors.v_id");
            
            String pID=""; String vName=""; String pName="";String pPrice="";
            System.out.format("| %10s | %20s | %30s | %5s |\n", "Product ID", "Product", "Vendor", "Price");
            while (q.next()) {
                pID = q.getString("p_id");
                vName = q.getString("v_name");
                pName = q.getString("p_name");
                pPrice = q.getString("p_price");
                System.out.format("| %10s | %20s | %30s | %5s |\n", pID, pName, vName, pPrice);
            }

        }catch(Exception e){
            System.err.println("[Connected: Exception:" + e.getMessage()+"]");
        }
    }

    public void displayOrders(Connection con){
        try{
            Statement statement = null;
            statement = con.createStatement();
            ResultSet orders = statement.executeQuery("SELECT orders.o_id, line.l_id, products.p_name, line.l_price, line.l_quantity, line.l_item_cost, orders.o_date, orders.o_est FROM orders JOIN line ON orders.o_id = line.o_id JOIN products ON line.p_id = products.p_id ORDER BY orders.o_id, line.l_id ");
            String orderId=""; String orderDate=""; String orderEst="";
            String line=""; String prodName=""; String linePrice=""; String lineQ=""; String lineItemCost="";
            System.out.format("|%8s |%-4s|%-32s", "Order ID", "Line", "Product");
            System.out.format("|%-7s|%-10s|%-9s", "Price", "Quantity","Cost");
            System.out.format("| %10s | %10s |\n","Order Date","Order Est.");
            while (orders.next()) {
                orderId=orders.getString("o_id");
                line=orders.getString("l_id");
                prodName=orders.getString("p_name");
                linePrice=orders.getString("l_price");
                lineQ=orders.getString("l_quantity");
                lineItemCost=orders.getString("l_item_cost");
                orderDate=orders.getString("o_date");
                orderEst=orders.getString("o_est");
            

                System.out.format("|  %-7s|  %-2s|%-32s", orderId, line, prodName);
                System.out.format("|%-7s|%-10s|%-9s", linePrice, lineQ, lineItemCost);
                System.out.format("| %10s | %10s |\n", orderDate, orderEst);

            }

        }catch(Exception e){
            System.err.println("[Connected: Exception:" + e.getMessage()+"]");
        }
    }

    public void invoice(Connection con, int orderID){
        System.out.println("\n= = = = = = = = = = = = = = = = = = = = = = = ORDER: = = = = = = = = = = = = = = = = = = = = = = = = =");

        try{
            Statement statement = null;
            statement = con.createStatement();
            ResultSet orders = statement.executeQuery("SELECT orders.o_id, line.l_id, products.p_name, line.l_price, line.l_quantity, line.l_item_cost, orders.o_date, orders.o_est FROM orders JOIN line ON orders.o_id = line.o_id JOIN products ON line.p_id = products.p_id WHERE orders.o_id = '"+orderID+"' ORDER BY orders.o_id, line.l_id;");
            String orderId=""; String orderDate=""; String orderEst="";
            String line=""; String prodName=""; String linePrice=""; String lineQ=""; String lineItemCost="";
            System.out.format("|%8s |%-4s|%-32s", "Order ID", "Line", "Product");
            System.out.format("|%-7s|%-10s|%-9s", "Price", "Quantity","Cost");
            System.out.format("| %10s | %10s |\n","Order Date","Order Est.");
            while (orders.next()) {
                orderId=orders.getString("o_id");
                line=orders.getString("l_id");
                prodName=orders.getString("p_name");
                linePrice=orders.getString("l_price");
                lineQ=orders.getString("l_quantity");
                lineItemCost=orders.getString("l_item_cost");
                orderDate=orders.getString("o_date");
                orderEst=orders.getString("o_est");

                System.out.format("|  %-7s|  %-2s|%-32s", orderId, line, prodName);
                System.out.format("|%-7s|%-10s|%-9s", linePrice, lineQ, lineItemCost);
                System.out.format("| %10s | %10s |\n", orderDate, orderEst);

            }

        }catch(Exception e){
            System.err.println("[Connected: Exception:" + e.getMessage()+"]");
        }

        System.out.println("= = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =\n");

    }

    public void orderQuantity(){
        System.out.println("\n=   What Would You Like To Order?     =");
        System.out.println("\n=   Quantity?                         =");

    }

}