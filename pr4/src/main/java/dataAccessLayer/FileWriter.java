package dataAccessLayer;

import businessLogicLayer.MenuItemBLL;
import businessLogicLayer.OrderBLL;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FileWriter {

    public FileWriter(int OrderID,  HashMap<OrderBLL, ArrayList<MenuItemBLL>> orders, int price){
        File f = new File("bill" + OrderID + ".txt");

        try{
            PrintWriter printWriter = new PrintWriter(f);

            printWriter.println("Order: " + OrderID);

            for (Map.Entry<OrderBLL, ArrayList<MenuItemBLL>> i : orders.entrySet()) {
                if (OrderID == i.getKey().getOrderID()) {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                    printWriter.println("Date: " + i.getKey().getOrderDate().format(formatter));
                    printWriter.println("Client: " + i.getKey().getClientID());
                    printWriter.println("Price: " + price);
                    printWriter.println("\nProducts: ");

                    for (MenuItemBLL m : i.getValue()) {
                        printWriter.print("Name: " + m.getTitle());
                        printWriter.print(" Price: " + m.getPrice());
                        printWriter.println(" RON");
                    }
                    printWriter.close();
                }
            }
        } catch (FileNotFoundException e){
            e.printStackTrace();
        }
    }
}