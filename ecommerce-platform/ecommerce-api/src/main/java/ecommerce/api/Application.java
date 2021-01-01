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
        //eventService.register(EventBuilder.aNew(ECommerceEventType.ORDER).add(basketService).add(stockService).add(logger));
    }
}
