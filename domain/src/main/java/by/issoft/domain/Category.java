package by.issoft.domain;

import java.util.ArrayList;
import java.util.List;

public abstract class Category {
    public String name;
    public List<Product> productList;

    public void addProduct(Product product) {
        if (productList == null) {
            productList = new ArrayList<>();
        }

        productList.add(product);
    }

    public void printAllProduct() {

        System.out.println(String.format("%s", "----------------------------------------------------------------------------------------------------------------"));
        System.out.println("Category " + name + " : ");
        System.out.println(String.format("%s", "----------------------------------------------------------------------------------------------------------------"));

        for (Product product : productList) {

            System.out.println(product.toString());
        }
    }
}
