package comparator;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ProductMain {
    public static void main(String[] args) {
        List<Product> products = new ArrayList<>();
        products.add(new Product(1, "Laptop", 75000));
        products.add(new Product(2, "Mouse", 500));
        products.add(new Product(3, "Keyboard", 1500));

        Comparator<Product> comparator = (p1,p2) ->
                Double.compare(p1.price,p2.price);
        products.sort(comparator);
        System.out.println(products);
    }
}
