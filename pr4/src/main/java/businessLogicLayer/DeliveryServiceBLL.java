package businessLogicLayer;

import dataAccessLayer.FileWriter;

import javax.swing.*;
import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

import static businessLogicLayer.BaseProductBLL.distinctByKey;

public class DeliveryServiceBLL extends Observable implements IDeliveryService, Serializable {

    public HashMap<OrderBLL, ArrayList<MenuItemBLL>> Orders = new HashMap<>();
    public List<MenuItemBLL> products = new ArrayList<>();
    public ArrayList<MenuItemBLL> items = new ArrayList<>();
    public ArrayList<OrderBLL> OrderBLLList = new ArrayList<>();
    public int OrderBLLCounter = 1;
    public int UserBLLCounter = 1;
    public List<UserBLL> Users = new ArrayList<>();

    public DeliveryServiceBLL() {
        BaseProductBLL b = new BaseProductBLL();
        List<BaseProductBLL> prod = b.loadItemsFromCsvFile();
        for (BaseProductBLL i : prod) {
            products.add(i);
        }
        assert isWellFormed();
    }

    public boolean isWellFormed(){
        assert !products.contains(null);
        return !products.contains(null);
    }

    @Override
    public List<BaseProductBLL> importProducts() {
        List<BaseProductBLL> menu = new ArrayList<>();
        BaseProductBLL b = new BaseProductBLL();
        menu = b.loadItemsFromCsvFile();

        return menu;
    }

    /**
     * Method used to create a new BaseProductBLL
     *
     * @param MenuItemBLL
     * @throws AssertionError
     */
    @Override
    public void addProduct(BaseProductBLL MenuItemBLL) {
        assert MenuItemBLL != null;
        int size = products.size();

        products.add(MenuItemBLL);

        assert size + 1 == products.size();
    }

    /**
     * Method used to delete a given product
     *
     * @param MenuItemBLLName
     * @return
     */
    @Override
    public boolean deleteProduct(String MenuItemBLLName) {
        assert MenuItemBLLName != null;
        int size = products.size();

        for (MenuItemBLL i : products) {
            if (MenuItemBLLName.equals(i.getTitle())) {
                products.remove(i);
                assert size - 1 == products.size();
                return true;
            }
        }
        return false;
    }

    /**
     * Method used to modify the price of a product
     *
     * @param MenuItemBLLName
     * @param price
     * @return
     */
    @Override
    public boolean updateProduct(String MenuItemBLLName, int price) {
        assert MenuItemBLLName != null;
        int p = 0;

        for (MenuItemBLL i : products) {
            if (i instanceof BaseProductBLL) {
                if (MenuItemBLLName.equals(i.getTitle())) {
                    i.setPrice(price);
                    p = i.getPrice();
                    assert p == i.getPrice();
                    return true;
                }
            } else {
                JOptionPane.showMessageDialog(null, "Composite products cannot be edited.", null,
                        JOptionPane.INFORMATION_MESSAGE);
            }
        }
        return false;
    }

    /**
     * Method used to create a CompositeProductBLL
     *
     * @param title
     * @param prod1
     * @param prod2
     * @param prod3
     * @throws AssertionError
     */
    @Override
    public void createCompositeProduct(String title, String prod1, String prod2, String prod3) {
        assert title != null;
        int size = products.size();

        BaseProductBLL p1 = new BaseProductBLL();
        BaseProductBLL p2 = new BaseProductBLL();
        BaseProductBLL p3 = new BaseProductBLL();
        for (MenuItemBLL i : products) {
            if (prod1.equals(i.getTitle())) {
                p1 = (BaseProductBLL) i;
            }
            if (prod2.equals(i.getTitle())) {
                p2 = (BaseProductBLL) i;
            }
            if (prod3.equals(i.getTitle())) {
                p3 = (BaseProductBLL) i;
            }
        }
        CompositeProductBLL c = new CompositeProductBLL();
        List<MenuItemBLL> items = new ArrayList<>();
//        items.add(p1);
//        items.add(p2);
//        items.add(p3);
        c.setItems(items);
        c.setTitle(title);
        c.computePrice();
        c.compute();
        products.add(c);

        assert size + 1 == products.size();
    }

    /**
     * Method used to register a new client into the system
     *
     * @param UserBLLname
     * @param password
     */
    @Override
    public void registerUser(String UserBLLname, String password) {
        Users.add(new UserBLL(UserBLLCounter++, UserBLLname, password));
    }

    public boolean login(UserBLL UserBLL) {
        for (UserBLL i : Users) {
            if (i.equals(UserBLL)) {
                return true;
            }
        }
        return false;
    }

    public ArrayList<MenuItemBLL> addItem(ArrayList<MenuItemBLL> MenuItemBLLs) {
        ArrayList<MenuItemBLL> ord = new ArrayList<>();
        for (MenuItemBLL i : MenuItemBLLs) {
            ord.add(i);
        }
        return ord;
    }

    public void addOrderItem(String item, int UserId) {
        for (MenuItemBLL i : products) {
            if (i.getTitle().equals(item)) {
                items.add(i);
                i.orderedTimes++;
                break;
            }
        }
        for (UserBLL u : Users) {
            if (u.getId() == UserId) {
                u.nrOrders++;
            }
        }
    }

    public void clearItems() {
        items.clear();
    }

    /**
     * Method used by client to create an OrderBLL
     *
     * @param UserBLLID
     * @param items
     * @throws FileNotFoundException
     */
    @Override
    public void createOrder(int UserBLLID, ArrayList<MenuItemBLL> items) throws FileNotFoundException{
        assert UserBLLID != 0;
        assert items != null;

        OrderBLL OrderBLL = new OrderBLL(OrderBLLCounter++, UserBLLID);
        Orders.put(OrderBLL, addItem(items));

        setChanged();
        notifyObservers(OrderBLL);

        for (UserBLL u : Users) {
            if (u.getId() == UserBLLID)
                u.nrOrders++;
        }
        generateBill(OrderBLL.getOrderID());

        assert OrderBLL.equals(OrderBLL);
    }

    /**
     * Method used to compute the total price of the OrderBLL
     *
     * @return
     */
    @Override
    public int computeOrderPrice() {
        int price = 0;
        for (MenuItemBLL m : items) {
            price += m.computePrice();
        }

        assert price >= 0;
        return price;
    }

    /**
     * Method used to create a bill with an OrderBLL
     *
     * @param OrderBLLID
     */
    @Override
    public void generateBill(int OrderBLLID) {
        assert OrderBLLID != 0;

        new FileWriter(OrderBLLID, Orders, computeOrderPrice());
    }

    /**
     * @param title
     * @return
     */
    @Override
    public List<MenuItemBLL> searchByTitle(String title) {
        List<MenuItemBLL> prod = new ArrayList<>();
        prod = products.stream().filter(p -> p.getTitle().equals(title)).collect(Collectors.toList());
        return prod;
    }

    @Override
    public List<MenuItemBLL> searchByRating(float rating) {
        List<MenuItemBLL> prod = new ArrayList<>();
        prod = products.stream().filter(p -> p.getRating() == rating).collect(Collectors.toList());
        return prod;
    }

    @Override
    public List<MenuItemBLL> searchByPrice(int price) {
        List<MenuItemBLL> prod = new ArrayList<>();
        prod = products.stream().filter(p -> p.getPrice() == price).collect(Collectors.toList());
        return prod;
    }

    @Override
    public List<MenuItemBLL> searchByTitleAndPrice(String title, int price) {
        List<MenuItemBLL> prod = new ArrayList<>();
        prod = products.stream().filter(p -> p.getTitle().equals(title) && p.getPrice() == price)
                .collect(Collectors.toList());
        return prod;
    }

    @Override
    public List<MenuItemBLL> searchByTitleAndRaiting(String title, float rating) {
        List<MenuItemBLL> prod = new ArrayList<>();
        prod = products.stream().filter(p -> p.getTitle().equals(title) && p.getRating() == rating)
                .collect(Collectors.toList());
        return prod;
    }

    @Override
    public List<MenuItemBLL> searchByTitleRatingPrice(String title, float rating, int price) {
        List<MenuItemBLL> prod = new ArrayList<>();
        prod = products.stream()
                .filter(p -> p.getTitle().equals(title) && p.getPrice() == price && p.getRating() == rating)
                .collect(Collectors.toList());
        return prod;
    }

    @Override
    public List<MenuItemBLL> searchByRatingPrice(float rating, int price) {
        List<MenuItemBLL> prod = new ArrayList<>();
        prod = products.stream().filter(p -> p.getPrice() == price && p.getRating() == rating)
                .collect(Collectors.toList());
        return prod;
    }

    @Override
    public void generateTimeReport(LocalDateTime start, LocalDateTime finish) throws FileNotFoundException {
        File f = new File("Report1.txt");
        PrintWriter printWriter = new PrintWriter(f);
        List<OrderBLL> ord = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        printWriter.println("Orders performed between " + start.format(formatter) + "  " + finish.format(formatter));

        for (Map.Entry<OrderBLL, ArrayList<MenuItemBLL>> i : Orders.entrySet()) {
            ord.add(i.getKey());
        }
        ord = ord.stream().filter(o -> o.getOrderDate().isAfter(start)).filter(o -> o.getOrderDate().isBefore(finish))
                .collect(Collectors.toList());
        for (OrderBLL i : ord) {
            printWriter.print("ID: " + i.getOrderID() + "  ");
            printWriter.print("Date: " + i.getOrderDate().format(formatter) + "  ");
            printWriter.println("Client: " + i.getClientID() + "  ");
        }
        printWriter.close();
    }

    @Override
    public void generateProductsReport(int nr) throws FileNotFoundException {
        File f = new File("Report2.txt");
        PrintWriter printWriter = new PrintWriter(f);
        printWriter.println("The products Ordered more than " + nr + " number of times");

        products = products.stream().filter(p -> p.orderedTimes > nr).collect(Collectors.toList());
        for (MenuItemBLL i : products) {
            printWriter.println("Product: " + i.getTitle() + ", Ordered " + i.orderedTimes + " times");
        }
        printWriter.close();
    }

    @Override
    public void generateClientsReport(int nr, int value) throws FileNotFoundException {
        File f = new File("Report3.txt");
        PrintWriter printWriter = new PrintWriter(f);
        printWriter.println("The clients that have Ordered more than " + nr
                + " times and the value of the OrderBLL was higher than " + value);

        List<OrderBLL> ord = new ArrayList<>();
        for (Map.Entry<OrderBLL, ArrayList<MenuItemBLL>> i : Orders.entrySet()) {
            ord.add(i.getKey());
        }
        List<UserBLL> u = new ArrayList<>();
        u = u.stream().filter(n -> n.nrOrders > nr).collect(Collectors.toList());
        for (UserBLL i : u) {
            ord = ord.stream().filter(n -> n.clientID == i.getId()).collect(Collectors.toList());
        }
        ord = ord.stream().filter(distinctByKey(n -> n.clientID)).collect(Collectors.toList());
        ord.forEach(o -> printWriter.println("Client: " + o.clientID));
        printWriter.close();
    }

    @Override
    public void generateProductsDayReport(LocalDate date) throws FileNotFoundException {
        File f = new File("Report4.txt");
        PrintWriter printWriter = new PrintWriter(f);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        printWriter.println("The products Ordered within " + date.format(formatter)
                + " and the number of times they have been OrderBLLed");
        List<OrderBLL> ord = new ArrayList<>();

        for (Map.Entry<OrderBLL, ArrayList<MenuItemBLL>> i : Orders.entrySet()) {
            ord.add(i.getKey());
        }
        ord = ord.stream().filter(o -> o.getOrderDate().toLocalDate().equals(date)).collect(Collectors.toList());

        List<MenuItemBLL> prod = new ArrayList<>();

        for (OrderBLL i : ord) {
            for (Map.Entry<OrderBLL, ArrayList<MenuItemBLL>> j : Orders.entrySet()) {
                if (j.getKey().equals(i)) {
                    for (MenuItemBLL m : j.getValue()) {
                        prod.add(m);
                    }
                }
            }
        }

        prod = prod.stream().filter(distinctByKey(p -> p.getTitle())).collect(Collectors.toList());
        for (MenuItemBLL item : prod) {
            printWriter.print("Product: " + item.getTitle() + "  ");
            printWriter.println("Nr. of times Ordered: " + item.orderedTimes + "  ");
        }
        printWriter.close();
    }
}