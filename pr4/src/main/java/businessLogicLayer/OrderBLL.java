package businessLogicLayer;

import java.io.Serializable;
import java.time.LocalDateTime;

public class OrderBLL implements Serializable {

    int orderID = 1;
    int clientID;
    LocalDateTime orderDate;

    public OrderBLL(){
        orderDate = LocalDateTime.now();
    }

    public OrderBLL(int orderID, int clientID) {
        this.orderID = orderID;
        this.clientID = clientID;
        orderDate = LocalDateTime.now();
    }

    @Override
    public int hashCode() {
        return orderID;
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public int getClientID() {
        return clientID;
    }

    public void setClientID(int clientID) {
        this.clientID = clientID;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }
}
