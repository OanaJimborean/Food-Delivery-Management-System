package businessLogicLayer;

import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * The DeliveryService interface which is implemented by the DeliveryService
 * It contains the main operations that can be executed by the administrator and the client
 */

public interface IDeliveryService {

    //administrator
    public List<BaseProductBLL> importProducts();

    /**
     * Method used to create a new BaseProductBLLBLL
     * @pre the given item is not null
     * @inv the menu item itself
     * @post the difference between the old size and the new size is exactly 1
     * @param MenuItemBLLBLL
     */
    public void addProduct(BaseProductBLL MenuItemBLLBLL);

    /**
     * Method used to delete a given product
     * @pre the given item is not null
     * @inv the menu item itself
     * @post the difference between the old size and the new size is exactly -1
     * @param MenuItemBLLName
     * @return
     */
    public boolean deleteProduct(String MenuItemBLLName);

    /**
     * Method used to modify the price of a product
     * @pre the menu item is not null
     * @inv the update method, itself
     * @post the new price and the given item price are equal
     * @param MenuItemBLLName
     * @param price
     * @return
     */
    public boolean updateProduct(String MenuItemBLLName, int price);

    /**
     * Method used to create a new CompositeProduct
     * @pre the given item is not null
     * @inv the menu item itself
     * @post the difference between the old size and the new size is exactly 1
     * @param title
     * @param prod1
     * @param prod2
     * @param prod3
     */
    public void createCompositeProduct(String title, String prod1, String prod2, String prod3);

    public void generateTimeReport(LocalDateTime start, LocalDateTime finish) throws FileNotFoundException;

    public void generateProductsReport(int nr) throws FileNotFoundException;

    public void generateProductsDayReport(LocalDate date) throws FileNotFoundException;

    public void generateClientsReport(int nr, int value) throws FileNotFoundException;

    //client
    public void registerUser(String username, String password);

    /**
     * Method used to create a new order, required by the client, notifying the employee
     * @ pre the given userID is not 0
     * @inv the list of products from the order is not null
     * @post the given order and the created order are equals
     * @param items
     * @throws FileNotFoundException
     */
    public void createOrder(int userID, ArrayList<MenuItemBLL> items) throws FileNotFoundException;

    /**
     * Method used to compute the total price of the order
     * @pre
     * @inv the menu item itself
     * @post the total price of the order is bigger than 0
     * @return
     */
    public int computeOrderPrice();

    /**
     * Method used to generate the bill in .txt file
     * @pre the orderID of the order to be printed is not 0
     * @inv creating the bill.txt file
     * @param orderID
     */
    public void generateBill(int orderID);

    public List<MenuItemBLL> searchByTitle(String title);

    public List<MenuItemBLL> searchByRating(float rating);

    public List<MenuItemBLL> searchByPrice(int price);

    public List<MenuItemBLL> searchByTitleAndPrice(String title, int price);

    public List<MenuItemBLL> searchByTitleAndRaiting(String title, float rating);

    public List<MenuItemBLL> searchByTitleRatingPrice(String title, float rating, int price);

    public List<MenuItemBLL> searchByRatingPrice(float rating, int price);

}