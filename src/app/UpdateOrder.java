package app;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class UpdateOrder {

    public UpdateOrder(){
    }

    public void updateOrder(Connection connection, Scanner scanner, GUI display){

        display.displayOrders(connection);

        System.out.println("Which Order Would You Like To Update? ( Enter In Order ID )\n");

        String orderID="";
        while(true){
            orderID = scanner.next();
            if(display.check(connection, orderID, "o_id", "orders")){
                display.invoice(connection, Integer.parseInt(orderID));
                update(connection, scanner, orderID, display);
                break;
            }else{
                System.out.println(" = INVALID Order ID, ( Enter A Valid Order ID )\n");
            }
        }

        System.out.println("\n= = = = = = = = = = = = = = = = = = = = = = = UPDATED: = = = = = = = = = = = = = = = = = = = = = = = =");
        display.invoice(connection, Integer.parseInt(orderID));
        System.out.println("Enter any character and then press:'Enter/Return' to return to Main Menu\n");
        scanner.next();
        
    }

    private void update(Connection connection, Scanner scanner, String orderID, GUI display){
        System.out.println("Which Line Would You Like To Modify?");
        String modLine = "";
        while(true){
            System.out.println("= Enter A Valid Line Number\n");
            modLine = scanner.next();
            if( display.checkLine(connection, orderID, modLine) ){
                break;
            }
        }

        System.out.println("= Enter New Quantity (Between 1 & 100) \n");
        int newQuantity = -1;
        while(true){
            String input = scanner.next();
            try{ newQuantity = Integer.parseInt(input); }
            catch(Exception e){
                System.err.println("[INVALID INT SYNTAX: " + e.getMessage()+"]");
            }
            if( newQuantity > 0 && newQuantity < 100 ){
                break;
            }
            System.out.println("[ INVALID QUANTITY (QUANTITY MUST BE BETWEEN 1 & 100) ]\n");
        }


        try{
            Statement statement = null;
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM line WHERE line.o_id = '"+orderID+"' AND line.l_id = '"+modLine+"';");
            String amount ="";
            String checkLine ="";
            while(resultSet.next()){
                checkLine = resultSet.getString("l_id");
                if(checkLine.contentEquals(modLine)){
                    amount = resultSet.getString("l_quantity");
                }
            }

            // System.out.println("Line to be altered:"+line+" Current quantity:"+amount+" To: "+quantity+"\n");

            String oldEst = getOrderEta(connection, orderID);
            if(oldEst.contentEquals("null")){
                System.out.println("INVALID ORDER ETA");
            }else{
                
                DateHandler dh = new DateHandler();
                String newEst = dh.etaDate(oldEst);
                int newTotal = (newQuantity * Integer.parseInt(amount));

                System.out.println("Line to be altered:"+modLine+" Current quantity:"+amount+" To: "+newQuantity+"\n");
                System.out.println("Old Order EST: "+oldEst+" New Order EST:"+newEst+"\n");

                statement.execute("UPDATE orders SET  o_est = '"+newEst+"' WHERE o_id = '"+orderID+"';");
                statement.execute("UPDATE line SET l_quantity = '"+newQuantity+"', l_item_cost= '"+newTotal+"' WHERE o_id = '"+orderID+"' AND l_id = '"+modLine+"';");
            }

        }catch(Exception e){
            System.err.println("[Conn: Update: Exception:" + e.getMessage()+"]");
        }

    }

    private String getOrderEta(Connection connection, String orderID){
        try{
            Statement statement = null;
            statement = connection.createStatement();
            ResultSet q = statement.executeQuery(" SELECT * FROM saleco_capdeville.orders WHERE o_id='"+orderID+"'; ");
            String orderEta = "";
            while (q.next()) {
                orderEta = q.getString("o_est");
            }
            return orderEta;
        }catch(Exception e){
            System.err.println("[Connected: Exception:" + e.getMessage()+"]");
        }
        return "null";
    }


}