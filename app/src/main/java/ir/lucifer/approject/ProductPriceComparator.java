package ir.lucifer.approject;

import java.util.Comparator;

public class ProductPriceComparator implements Comparator<Product> {

    @Override
    public int compare(Product o1, Product o2) {

        return Integer.parseInt(o1.price) - Integer.parseInt(o2.price);
    }
}
