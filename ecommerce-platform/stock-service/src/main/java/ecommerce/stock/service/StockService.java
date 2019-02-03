package ecommerce.stock.service;

import ecommerce.shared.model.ItemWithCount;
import ecommerce.stock.core.Stock;

import java.util.List;

public class StockService {

    private final Stock stock = new Stock();

    public void add(Integer productId, Integer count) {
        stock.add(productId, count);
    }

    public Integer getCount(Integer productId) {
        return stock.getCount(productId);
    }

    public void checkout(ItemWithCount item) {
        Integer productId = item.getProductId();
        Integer countToCheckout = item.getCount();
        stock.checkout(productId, countToCheckout);

        System.out.println(
                String.format("PRODUCT CHECKOUTED from STOCK : {\"productId\":%d, \"countCheckouted\":%d, \"countAfterCheckout\":%d}",
                              productId, countToCheckout, stock.getCount(productId)));
    }

    public void checkout(List<ItemWithCount> items) {
        for (ItemWithCount item : items) {
            checkout(item);
        }
    }
}
