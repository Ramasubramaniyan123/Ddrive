package Medium.Col2;
import java.util.*;
public class ShoppingCartBilling {
    public static int findLowestPrice(List<List<String>> products, List<List<String>> discounts) {
        Map<String, List<int[]>> map = new HashMap<>();
        for (List<String> d : discounts) {
            String tag = d.get(0);
            int type = Integer.parseInt(d.get(1));
            int amount = Integer.parseInt(d.get(2));
            map.putIfAbsent(tag, new ArrayList<>());
            map.get(tag).add(new int[]{type, amount});
        }
        int total = 0;
        for (List<String> product : products) {
            int originalPrice = Integer.parseInt(product.get(0));
            int minPrice = originalPrice;
            for (int i = 1; i < product.size(); i++) {
                String tag = product.get(i);
                if (tag.equals("EMPTY") || !map.containsKey(tag)) continue;
                for (int[] dis : map.get(tag)) {
                    int type = dis[0];
                    int amount = dis[1];
                    int price = originalPrice;
                    if (type == 0) {
                        price = amount;
                    }
                    else if (type == 1) {
                        price = (int)(price - (price * amount / 100.0));
                    }
                    else if (type == 2) {
                        price = price - amount;
                    }

                    price = Math.max(0, price);
                    minPrice = Math.min(minPrice, price);
                }
            }

            total += minPrice;
        }

        return total;
    }
}
