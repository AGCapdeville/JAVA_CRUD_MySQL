package app;

import java.util.Scanner;
import java.sql.Connection;


public class Menu {

    public Menu(){
    }

    public void menu(Connection connection, Scanner scanner){
        String buff = "";
        GUI display = new GUI();
        ExistsQuery existsQuery = new ExistsQuery();
        display.menu();

        while (true) {
            System.out.print("        SELECT: ");
            buff = scanner.next();
            if (buff.contentEquals("q")) break;
            if(menuSelection(connection, scanner, buff, display, existsQuery)){
                display.clearTerminal();
                display.menu();
            }
        }
    }

    private boolean menuSelection(Connection connection, Scanner scanner, String instruction, GUI display, ExistsQuery existsQuery){
        //return true if crud, false if invalid OR Menu
        switch (instruction){
            case "c":
                // create order();
                CreateOrder createOrder = new CreateOrder();
                createOrder.createOrder(connection, scanner, display);
                return true;
            case "v":
                display.orders(connection);
                System.out.println("Enter any character and then press:'Enter/Return' to return to Main Menu\n");
                scanner.next();
                return true;
            case "u":
                UpdateOrder updateOrder = new UpdateOrder();
                updateOrder.updateOrder(connection, scanner, display, existsQuery);
                return true;
            case "d":
                DeleteOrder deleteOrder = new DeleteOrder();
                deleteOrder.deleteOrder(connection, scanner, display, existsQuery);
                return true;
            default :
                System.out.print("invalid input\n");
                return false;
        }
    }

}