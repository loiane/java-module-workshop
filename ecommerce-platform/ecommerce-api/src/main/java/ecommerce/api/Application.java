package ecommerce.api;

import ecommerce.api.model.Basket;
import ecommerce.api.service.BasketService;
import ecommerce.api.service.CatalogService;
import ecommerce.api.model.Order;
import ecommerce.api.service.OrderService;
import ecommerce.api.model.Product;
import ecommerce.api.service.StockService;

import java.util.Optional;

public class Application {

    public static void main(String[] args) {
        BasketService basketService = new BasketService();
        CatalogService catalogService = new CatalogService();
        StockService stockService = new StockService();
        OrderService orderService = new OrderService(stockService, basketService);

        Product bossKulaklik = catalogService.add("Boss Kulaklık", 500D);
        Product appleKlavye = catalogService.add("Apple Klavye", 200D);
        Product dellMonitor = catalogService.add("Dell Monitor", 200D);

        stockService.add(bossKulaklik, 100);
        stockService.add(appleKlavye, 100);
        stockService.add(dellMonitor, 100);

        Integer buyerId = 1001;

        basketService.addItem(buyerId, bossKulaklik,  5);
        basketService.addItem(buyerId, appleKlavye,  3);
        basketService.addItem(buyerId, dellMonitor, 1);

        orderProductsInBasket(basketService, orderService, buyerId);
    }

    private static void orderProductsInBasket(BasketService basketService, OrderService orderService, Integer buyerId) {
        Optional<Basket> basket = basketService.get(buyerId);
        Optional<Order> order = BasketOrderConverter.convert(basket);
        System.out.println("********* Order Execution Started *********");
        order.ifPresentOrElse(o->orderService.order(o), System.out::println);
    }
}
