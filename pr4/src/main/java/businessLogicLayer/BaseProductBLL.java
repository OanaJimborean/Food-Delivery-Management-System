package businessLogicLayer;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import java.util.List;

public class BaseProductBLL extends MenuItemBLL implements Serializable {

    //public static int orderedTimes;

    public BaseProductBLL(String title, float rating, int calories, int proteins, int fats, int sodium, int price) {
        super(title, rating, calories, proteins, fats, sodium, price);
        orderedTimes = 0;
    }

    public BaseProductBLL() {
        super();
    }

    public int computePrice() {
        return getPrice();
    }

    public List<BaseProductBLL> loadItemsFromCsvFile() {
        Pattern pattern = Pattern.compile(",");
        List<BaseProductBLL> BaseProducts = new ArrayList<>();

        try (Stream<String> lines = Files.lines(Path.of("D:\\AN2_SEMESTRUL2\\PT_Lab\\PT2022_30422_Jimborean_Oana_Assignment_4\\pr4\\products.csv"))) {
            List<BaseProductBLL> products = lines.skip(1).map(line -> {
                String[] arr = pattern.split(line);
                BaseProducts.add(new BaseProductBLL(arr[0], Float.parseFloat(arr[1]), Integer.parseInt(arr[2]),
                        Integer.parseInt(arr[3]), Integer.parseInt(arr[4]), Integer.parseInt(arr[5]),
                        Integer.parseInt(arr[6])));
                return new BaseProductBLL(arr[0], Float.parseFloat(arr[1]), Integer.parseInt(arr[2]),
                        Integer.parseInt(arr[3]), Integer.parseInt(arr[4]), Integer.parseInt(arr[5]),
                        Integer.parseInt(arr[6]));
            }).collect(Collectors.toList());

        } catch (IOException e) {
            System.out.println("The file does not exist.");
        }

        // remove duplicates
        List<BaseProductBLL> products = BaseProducts.stream().filter(distinctByKey(p -> p.getTitle()))
                .collect(Collectors.toList());
        return products;
    }

    public static <T> Predicate<T> distinctByKey(Function<? super T, Object> keyExtractor) {
        Map<Object, Boolean> map = new ConcurrentHashMap<>();
        return t -> map.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }

}