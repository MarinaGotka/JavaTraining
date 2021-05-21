package by.issoft.store.helpers;

import by.issoft.domain.Category;
import by.issoft.domain.Product;
import by.issoft.domain.categoryFactory.CategoryCreator;
import by.issoft.store.Store;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

import by.issoft.store.helpers.comparators.ProductComparator;
import by.issoft.store.helpers.comparators.SortOrder;
import by.issoft.store.helpers.comparators.XmlReader;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;

import javax.xml.parsers.ParserConfigurationException;

public class StoreHelper {

    Store store;

    public StoreHelper(Store store) {
        this.store = store;
    }

    public void fillStoreRandomly() {

        RandomStorePopulator populator = new RandomStorePopulator();
        Map<Category, Integer> categoryProductsMapToAdd = createProductListToAdd();

        for (Map.Entry<Category, Integer> entry : categoryProductsMapToAdd.entrySet()) {
            for (int i = 0; i < entry.getValue(); i++) {

                Product product = new Product(
                        populator.getProductName(entry.getKey().name),
                        populator.getPrice(),
                        populator.getRate());
                entry.getKey().addProduct(product);
            }

            this.store.categoryList.add(entry.getKey());
        }
    }

    private static Map<Category, Integer> createProductListToAdd() {
        Map<Category, Integer> productsToAdd = new HashMap<>();

        Reflections reflections = new Reflections("by.issoft.domain.categoryFactory", new SubTypesScanner());
        //Get all existed Category Creators
        Set<Class<? extends CategoryCreator>> subTypes = reflections.getSubTypesOf(CategoryCreator.class);

        //Create a random number of random products for each category using CategoryCreators
        for (Class<? extends CategoryCreator> type : subTypes) {
            try {
                Random random = new Random();

                CategoryCreator creator = type.newInstance();
                Method method = creator.getClass().getMethod("createCategory");

                productsToAdd.put((Category) method.invoke(creator), random.nextInt(10));

            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            }
        }

        return productsToAdd;
    }

    public List<Product> sortAllProducts() throws Exception {
        Map<String, String> sortBy;
        try {
            XmlReader xml = new XmlReader();
             sortBy = xml.getAllPropertiesToSort();
        }
        catch (ParserConfigurationException e){
            throw new Exception("Error: Config file exception.");
        }
        return sortAllProducts(sortBy);
    }

    public List<Product> sortAllProducts(Map<String, String> sortBy) {

        List<Product> allProducts = this.store.getListOfAllProducts();
        allProducts.sort(new ProductComparator(sortBy));

        return allProducts;
    }

    public List<Product> getTop5() {

        Map<String, String> sortBy = new HashMap<>();
        sortBy.put("price", SortOrder.DESC.toString());

        List<Product> sortedList = sortAllProducts(sortBy);
        List<Product> top5 = new ArrayList<>(sortedList.subList(0, 5));

        return top5;
    }
}
