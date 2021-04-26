package by.issoft.domain;

public class Product {
    public String name;
    public Double price;
    public Double rate;

    public Product(String name, double price, double rate) {
        this.name = name;
        this.price = price;
        this.rate = rate;
    }

    @Override
    public String toString() {

        String productInfo = String.format("Name: '%s', Price: %s, Rate: %s", name, price, rate);

        return productInfo;
    }
}