import businessLogicLayer.DeliveryServiceBLL;
import presentationLayer.LogIn;

import java.io.FileInputStream;
import java.io.ObjectInputStream;

public class Main {

    public static void main(String[] args) {

        try {
            FileInputStream f = new FileInputStream("output.txt");
            ObjectInputStream o = new ObjectInputStream(f);
            DeliveryServiceBLL deliveryService = (DeliveryServiceBLL) o.readObject();
            if (deliveryService.products.size() == 0)
                deliveryService.products.addAll(deliveryService.importProducts());
            LogIn log = new LogIn(deliveryService);
        } catch (Exception e) {
            DeliveryServiceBLL deliveryService = new DeliveryServiceBLL();
            LogIn log = new LogIn(deliveryService);
        }


    }

    // Trb sa pui cu spatiu la final produsele cand le adaugi
    // Sa schimbi pathu de la products.csv in importProducts
}
