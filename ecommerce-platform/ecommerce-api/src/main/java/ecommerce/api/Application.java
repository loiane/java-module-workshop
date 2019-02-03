package ecommerce.api;

import ecommerce.basket.core.Basket;
import ecommerce.basket.service.BasketService;
import ecommerce.catalog.service.CatalogService;
import ecommerce.order.core.Order;
import ecommerce.order.service.OrderService;
import ecommerce.shared.model.Product;
import ecommerce.stock.service.StockService;

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

        stockService.add(bossKulaklik.getId(), 100);
        stockService.add(appleKlavye.getId(), 100);
        stockService.add(dellMonitor.getId(), 100);

        Integer buyerId = 1001;

        basketService.addItem(buyerId, bossKulaklik.getId(), bossKulaklik.getUnitPrice(), 5);
        basketService.addItem(buyerId, appleKlavye.getId(), appleKlavye.getUnitPrice(), 3);
        basketService.addItem(buyerId, dellMonitor.getId(), dellMonitor.getUnitPrice(),1);

        orderProductsInBasket(basketService, orderService, buyerId);
    }

    private static void orderProductsInBasket(BasketService basketService, OrderService orderService, Integer buyerId) {
        Optional<Basket> basket = basketService.get(buyerId);
        Optional<Order> order = BasketOrderConverter.convert(basket);
        System.out.println("********* Order Execution Started *********");
        order.ifPresentOrElse(o->orderService.order(o), System.out::println);
    }
}
