package app;

import java.util.Scanner;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;

public class CreateOrder{

    public CreateOrder(){
    }

    public void createOrder(Connection con, Scanner s, GUI display){

        int lines = howManyProducts(s);
        String[] products = new String[lines];
        int[] amounts = new int[lines];
        System.out.println("= = = = = = = = = = = = = = = = = PRODUCTS: = = = = = = = = = = = = = = = = =");
        display.displayProducts(con);

        for (int i=0; i<lines; i++){
            System.out.println("\n= = = = = = = = = = = = = = = = = PRODUCT ["+i+"]: = = = = = = = = = = = = = = = = =");
            System.out.println("\n=   What Would You Like To Order?     =");
            products[i] = display.newOrder(con, s);
            System.out.println("\n=  How Many Would You Like To Order?  =");
            amounts[i] = display.howMany(s);
        }

        int newOrderID = newOrderId(con);
        if( newOrderID == -1){
            System.out.print("ERROR, new order id can not be created\n");
        }else{
        String[] linePrice = new String[lines];
        setPrices(linePrice, products, lines, con);
        
        DateHandler dateHandler = new DateHandler();

        String orderDate = dateHandler.newDate();
        String orderEta = "null";
        try{
            orderEta = dateHandler.etaDate(orderDate);
            pushOrder(con, newOrderID, products, amounts, lines, linePrice, orderDate, orderEta);
            display.invoice(con, newOrderID);
        }catch(Exception e){
            System.out.println(" [ DateHandler: etaDate: Exception: "+e+" ]\n");
        }

        System.out.println("Enter any character and then press:'Enter/Return' to return to Main Menu\n");
        s.next();
        }
    }

    private void pushOrder(Connection con, int newOrderID, String[] products, int[] amounts, int lines, String[] linePrice, String orderDate, String orderEta){
        try{
            Statement statement = null;
            statement = con.createStatement();

            // construct and insert new order with: new order_id, order_date, order_eta
            statement.execute("INSERT INTO saleco_capdeville.orders (o_id, o_date, o_est) VALUES ('"+newOrderID+"', '"+orderDate+"', '"+orderEta+"');"); 
            
            // construct insert sql for lines
            for(int i = 0; i < lines; i++){
                int a = amounts[i];
                double p = Double.parseDouble(linePrice[i]);
                double total = a * p;
                String sTotal= ""+total;
                statement.execute("INSERT INTO saleco_capdeville.line (l_id, o_id, p_id, l_price, l_quantity, l_item_cost) VALUES ('"+i+"','"+newOrderID+"','"+products[i]+"','"+linePrice[i]+"','"+amounts[i]+"','"+sTotal+"');");
            }

        }catch(Exception e){
            System.err.println("[Push Order ERROR: " + e.getMessage()+"]");
        }

    }   

    private void setPrices(String[] linePrice, String[] products, int lines, Connection con){
        try{
            Statement statement = null;
            statement = con.createStatement();
            ResultSet q = statement.executeQuery("select p.* from saleco_capdeville.products p");
            int x = 0;
            while (q.next()) {
                String checker = q.getString("p_id");
                String price = q.getString("p_price");
                for(int j = 0; j<lines ; j++){
                    if(products[j].contentEquals(checker)){
                        linePrice[x]= price;
                        x++;
                    }
                }
            }
        }catch(Exception e){
            System.err.println("[Connected: Exception:" + e.getMessage()+"]");
        }
    }

    private int newOrderId(Connection con){
        try{

            Statement statement = null;
            statement = con.createStatement();
            ResultSet q = statement.executeQuery("SELECT orders.o_id FROM orders ORDER BY orders.o_id desc limit 1");
            String id="";

            while(q.next()){
            id = q.getString("o_id");
            }

            int n = -1;
            try { n = Integer.parseInt(id); }
            catch (NumberFormatException nfe){ 
                System.out.println("Not a valid Number: " + nfe.getMessage()); 
                System.out.println("Exiting Create Order..."); 
                return -1;
            }
            n++;

            return ( n );

        }catch(Exception e){
            System.err.println("[Gen new order ID: Exception:" + e.getMessage()+"]");
            return -1;
        }
    }

    private int howManyProducts(Scanner s){
        System.out.println("\n= = = = = = = = = = = = = = = = = NEW ORDER: = = = = = = = = = = = = = = = = =");
        System.out.println("\n= How Many Products Would You Like To Order =");
        String passed;
        int amount = -1;
        while(true){
            passed = s.next();
            try { amount = Integer.parseInt(passed); }
            catch (NumberFormatException nfe){ 
                System.out.println("Not a valid Number: " + nfe.getMessage()); 
                System.out.println("Exiting Create Order..."); 
            }
            if( amount > 0 && amount < 10)
                break;
            System.out.println("\n= Amount has to between 0 & 10 =");
        }
        return amount;
    }

}