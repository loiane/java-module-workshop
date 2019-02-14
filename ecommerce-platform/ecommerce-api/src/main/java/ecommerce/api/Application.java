package ecommerce.api;

import ecommerce.basket.core.Basket;
import ecommerce.basket.service.BasketService;
import ecommerce.catalog.service.CatalogService;
import ecommerce.stock.service.StockService;
import ecommerce.order.core.Order;
import ecommerce.order.service.OrderService;
import ecommerce.shared.event.ECommerceEventType;
import ecommerce.shared.model.Product;
import jeventbus.service.EventBuilder;
import jeventbus.service.EventService;

import java.util.Optional;

public class Application {

    public static void main(String[] args) {
        EventService eventService = new EventService();
        BasketService basketService = new BasketService(eventService);
        CatalogService catalogService = new CatalogService(eventService);
        StockService stockService = new StockService(eventService);
        OrderService orderService = new OrderService(eventService);
        Logger logger = new Logger(LoggerFactory.getInstance().get("console"));

        //        ConsoleLogger consoleLogger = new ConsoleLogger();

        eventService.register(EventBuilder.aNew(ECommerceEventType.BASKET_CLEARED).add(logger));
        eventService.register(EventBuilder.aNew(ECommerceEventType.BASKET_CREATED).add(logger));
        eventService.register(EventBuilder.aNew(ECommerceEventType.BASKET_ITEM_ADDED).add(logger));
        eventService.register(EventBuilder.aNew(ECommerceEventType.BASKET_ITEM_REMOVED).add(logger));
        eventService.register(EventBuilder.aNew(ECommerceEventType.BASKET_ITEM_COUNT_DECREASED).add(logger));
        eventService.register(EventBuilder.aNew(ECommerceEventType.PRODUCT_ADDED).add(logger));
        eventService.register(EventBuilder.aNew(ECommerceEventType.PRODUCT_DELETED).add(logger));
        eventService.register(EventBuilder.aNew(ECommerceEventType.STOCK_ADDED).add(logger));
        eventService.register(EventBuilder.aNew(ECommerceEventType.STOCK_CHECKOUTED).add(logger));
        eventService.register(EventBuilder.aNew(ECommerceEventType.ORDER).add(basketService).add(stockService).add(logger));


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
