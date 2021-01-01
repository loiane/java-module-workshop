package ecommerce.logger;

import ecommerce.shared.model.ItemWithCount;
import jeventbus.shared.EventListener;
import jeventbus.shared.EventSource;
import logger.shared.LogMessage;
import logger.service.LoggerService;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.String.format;

@ApplicationScoped
public class Logger implements EventListener {

    LoggerService loggerService;

    public Logger(LoggerService loggerService) {

        this.loggerService = loggerService;
    }

    private void log(String str) {
        loggerService.log(new LogMessage(str));
    }

    public void onProductAdded(EventSource eventSource) {
        log(format("PRODUCT{%d:%s} ADDED to CATALOG : ",
                        eventSource.get("productId"),
                        eventSource.get("productName")));
    }

    public void onStockCheckouted(EventSource eventSource) {
        log(format("PRODUCT CHECKOUTED from STOCK : {\"productId\":%d, \"countCheckouted\":%d, \"countAfterCheckout\":%d}",
                        eventSource.get("productId"),
                        eventSource.get("countToCheckout"),
                        eventSource.get("countAfterCheckout")));
    }

    public void onBasketCreated(EventSource eventSource) {
        log(format("BASKET CREATED : {\"buyerId\":%d, \"basketId\":%d}",
                eventSource.get("buyerId"),
                eventSource.get("basketId")));
    }

    public void onBasketCleared(EventSource eventSource) {
        log(format("BASKET CLEARED : {\"buyerId\":%d, \"basketId\":%d, \"totalPrice\":%.2f}",
                          eventSource.get("buyerId"),
                          eventSource.get("basketId"),
                          eventSource.get("totalPrice")));
    }

    public void onOrder(EventSource eventSource){
        String itemsWithCount = ((List<ItemWithCount>)eventSource.get("items")).stream().map(i->i.toString()).collect(Collectors.joining(","));

        log(format("ORDER EXECUTED : {\"buyerId\":%d, \"basketId\":%d, \"items\":[%s]}",
                          eventSource.get("buyerId"),
                          eventSource.get("basketId"),
                          itemsWithCount));
    }

    public void onBasketItemAdded(EventSource eventSource) {
        log(format("BASKET ADD ITEM : {\"buyerId\":%d, \"productId\":%d, \"unitPrice\":%.2f, \"count\":%d }",
                           eventSource.get("buyerId"),
                           eventSource.get("productId"),
                           eventSource.get("unitPrice"),
                           eventSource.get("count")));
    }

    public void onBasketItemCountDecreased(EventSource eventSource) {
        log(format("BASKET DEC ITEM COUNT : {\"buyerId\":%d, \"productId\":%d, \"countToDec\":%d, \"latestCount\":%d }",
                           eventSource.get("buyerId"),
                           eventSource.get("productId"),
                           eventSource.get("count"),
                           eventSource.get("latestCount")));
    }

    public void onBaskedItemRemoved(EventSource eventSource) {
        log(format("BASKET REMOVE ITEM : {\"buyerId\":%d, \"productId\":%d }",
                           eventSource.get("buyerId"),
                           eventSource.get("productId")
                           ));
    }

    public void onProductDeleted(EventSource eventSource) {
        log(format("CATALOG ITEM DELETED : {\"productId\":%d}",
                                             eventSource.get("productId")));
    }

    public void onStockAdded(EventSource eventSource) {
        log(format("STOCK ADDED : {\"productId\":%d, \"count\":%d}",
                          eventSource.get("productId"), eventSource.get("count")));
    }
}
