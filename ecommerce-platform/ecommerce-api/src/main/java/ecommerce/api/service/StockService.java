package ecommerce.api.service;

import ecommerce.api.model.OrderItem;
import ecommerce.api.model.Product;
import ecommerce.api.model.Stock;

import java.util.List;

import static java.lang.String.format;

public class StockService {

    private static final String MESSAGE = "PRODUCT CHECKOUTED from STOCK : "
                                    + "{\"productId\":%d,"
                                    + " \"countCheckouted\":%d,"
                                    + " \"countAfterCheckout\":%d}";

    private final Stock stock = new Stock();

    public void add(Product product, Integer count) {
        stock.add(product, count);
    }

    public Integer getCount(Integer productId) {
        return stock.getCount(productId);
    }

    public void checkout(OrderItem item) {

        Product product = item.getProduct();
        Integer countToCheckout = item.getCount();
        stock.checkout(product, countToCheckout);

        System.out.println(
                format(MESSAGE,
                       product.getId(),
                       countToCheckout,
                       stock.getCount(product.getId())));
    }

    public void checkout(List<OrderItem> items) {
        for (OrderItem item : items) {
            checkout(item);
        }
    }
}
