package app;

import java.util.Scanner;
import java.sql.Connection;
import java.sql.Statement;



public class DeleteOrder {

    public DeleteOrder(){
    }

    public void deleteOrder(Connection con, Scanner scanner, GUI display){
        System.out.println("\n= = = = = = = = = = = = = = = = = = = = = Current Orders: = = = = = = = = = = = = = = = = = = = = = = = ");
        display.displayOrders(con);
        System.out.println("= = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = = =\n");

        System.out.println(" = What Order Would You Like To Delete? ( Enter In Order ID )\n");


        String orderID="";
        int id=-1;
        while(true){
            orderID = scanner.next();
            if(display.check(con, orderID, "o_id", "orders")){
                try { id = Integer.parseInt(orderID); }
                catch (NumberFormatException nfe){ 
                    System.out.println("Not a valid Order ID: " + nfe.getMessage()); 
                    System.out.println("Exiting Delete Order..."); 
                }
                
                try{
                    Statement statement = null;
                    statement = con.createStatement();
                    statement.execute("DELETE FROM line WHERE o_id='"+id+"';");
                    statement.execute("DELETE FROM orders WHERE o_id='"+id+"';");
                }catch(Exception e){
                    System.err.println("[DELETE ERROR:Exception:" + e.getMessage()+"]");
                }
                break;
            }else{
                System.out.println(" = INVALID Order ID, ( Enter A Valid Order ID )\n");
            }
        }

        
    }

}