package presentationLayer;


import businessLogicLayer.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class OrderBox {

    private JScrollPane scrollPane;
    private OrderBLL order;
    private ArrayList<MenuItemBLL> content;
    public Box box;

    public OrderBox(OrderBLL order, ArrayList<MenuItemBLL> content) {
        this.order = order;
        this.content = content;
        box = Box.createVerticalBox();
        JLabel orderNumber = new JLabel("Order number: " + order.getOrderID());
        orderNumber.setFont(new Font("Tahoma", Font.BOLD, 15));
        orderNumber.setForeground(Color.BLUE);
        box.add(orderNumber);
        JLabel prod = new JLabel("Products: ");
        prod.setFont(new Font("Tahoma", Font.BOLD, 14));
        box.add(prod);
        int s = 0;
        for (MenuItemBLL m : content) {
            s = s+m.getPrice();
            JLabel temp = new JLabel("   " + m.getTitle());
            temp.setFont(new Font("Tahoma", Font.PLAIN, 13));
            box.add(temp);
            if (m instanceof CompositeProductBLL) {
                for (MenuItemBLL item : ((CompositeProductBLL) m).getItems()) {
                    JLabel subTemp = new JLabel("   " + item.getTitle());
                    subTemp.setFont(new Font("Tahoma", Font.PLAIN, 13));
                    box.add(subTemp);
                }
            }
        }
        JLabel price = new JLabel("\nTotal price: " + s);
        price.setFont(new Font("Tahoma", Font.BOLD, 14));
        box.add(price);
        scrollPane = new JScrollPane(box);
        scrollPane.setPreferredSize(new Dimension(250, 250));
    }

    public JScrollPane getScrollablePane() {
        return scrollPane;
    }

    public OrderBLL getOrder() {
        return order;
    }
}
