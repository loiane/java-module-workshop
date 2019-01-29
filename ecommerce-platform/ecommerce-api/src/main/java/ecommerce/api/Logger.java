package ecommerce.api;

import ecommerce.shared.model.ItemWithCount;
import jeventbus.shared.EventListener;
import jeventbus.shared.EventSource;
import logger.core.LogMessage;
import logger.service.LoggerService;

import java.util.List;
import java.util.stream.Collectors;

public class Logger implements EventListener {

    private LoggerService loggerService;

    public Logger(LoggerService loggerService) {

        this.loggerService = loggerService;
    }

    private void log(String str) {
        loggerService.log(new LogMessage(str));
    }

    public void onProductAdded(EventSource eventSource) {
        log("PRODUCT ADDED to CATALOG : "+eventSource.get("product"));
    }

    public void onStockCheckouted(EventSource eventSource) {
        log(String.format("PRODUCT CHECKOUTED from STOCK : {\"productId\":%d, \"countCheckouted\":%d, \"countAfterCheckout\":%d}",
                          eventSource.get("productId"),
                          eventSource.get("countToCheckout"),
                          eventSource.get("countAfterCheckout")));
    }


    public void onBasketCleared(EventSource eventSource) {
        log(String.format("BASKET CLEARED : {\"buyerId\":%d, \"basketId\":%d, \"totalPrice\":%.2f}",
                          eventSource.get("buyerId"),
                          eventSource.get("basketId"),
                          eventSource.get("totalPrice")));
    }

    public void onOrder(EventSource eventSource){
        String itemsWithCount = ((List<ItemWithCount>)eventSource.get("items")).stream().map(i->i.toString()).collect(Collectors.joining(","));

        log(String.format("ORDER EXECUTED : {\"buyerId\":%d, \"basketId\":%d, \"items\":[%s]}",
                          eventSource.get("buyerId"),
                          eventSource.get("basketId"),
                          itemsWithCount));
    }
}
