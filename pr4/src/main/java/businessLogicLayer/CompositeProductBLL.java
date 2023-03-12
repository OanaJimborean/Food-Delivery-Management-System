package businessLogicLayer;


import java.io.Serializable;
import java.util.List;

public class CompositeProductBLL extends MenuItemBLL implements Serializable {

    private List<MenuItemBLL> items;

    public CompositeProductBLL(String title, float rating, int calories, int proteins, int fats, int sodium, int price) {
        super(title, rating, calories, proteins, fats, sodium, price);
        orderedTimes = 0;
    }

    public CompositeProductBLL() {
        super();
    }

    @Override
    public int computePrice() {
        int price = 0;
        for (MenuItemBLL i : items) {
            price = price + i.getPrice();
        }
        this.setPrice(price);

        return price;
    }

    public void compute() {
        int calories = 0;
        float rating = 0;
        int proteins = 0;
        int fats = 0;
        int sodium = 0;
        for (MenuItemBLL i : items) {
            calories += i.getCalories();
            rating += i.getRating();
            proteins += i.getProteins();
            fats += i.getFats();
            sodium += i.getSodium();
        }
        this.setCalories(calories);
        this.setProteins(proteins);
        this.setFats(fats);
        this.setRating(rating);
        this.setSodium(sodium);
    }

    public List<MenuItemBLL> getItems() {
        return items;
    }

    public void setItems(List<MenuItemBLL> items) {
        this.items = items;
    }
}
