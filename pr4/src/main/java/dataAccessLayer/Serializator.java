package dataAccessLayer;

import businessLogicLayer.DeliveryServiceBLL;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class Serializator {

    public static void serialization(DeliveryServiceBLL deliveryService){
        FileOutputStream f;
        try {
            f = new FileOutputStream("output.txt");
            ObjectOutputStream o = new ObjectOutputStream(f);
            o.writeObject(deliveryService);
            o.flush();
            o.close();
        } catch (IOException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        }
    }

}