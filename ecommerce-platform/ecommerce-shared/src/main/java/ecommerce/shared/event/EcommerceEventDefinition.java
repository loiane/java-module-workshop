package ecommerce.shared.event;

import jeventbus.shared.EventType;
import jeventbus.streaming.EventContext;
import jeventbus.streaming.EventDefinition;

import java.util.Arrays;
import java.util.Optional;

public enum EcommerceEventDefinition implements EventDefinition {

    PRODUCT_ADDED(1, ECommerceEventContext.CATALOG, true, ECommerceEventType.PRODUCT_ADDED),
    PRODUCT_DELETED(2, ECommerceEventContext.CATALOG, true, ECommerceEventType.PRODUCT_DELETED),
    STOCK_ADDED(3, ECommerceEventContext.STOCK, true, ECommerceEventType.STOCK_ADDED),
    STOCK_CHECKOUTED(4, ECommerceEventContext.STOCK, true, ECommerceEventType.STOCK_CHECKOUTED),
    BASKET_CREATED(5, ECommerceEventContext.BASKET, true, ECommerceEventType.BASKET_CREATED),
    BASKET_CLEARED(6, ECommerceEventContext.BASKET, true, ECommerceEventType.BASKET_CLEARED),
    BASKET_ITEM_ADDED(7, ECommerceEventContext.BASKET, true, ECommerceEventType.BASKET_ITEM_ADDED),
    BASKET_ITEM_COUNT_DECREASED(8, ECommerceEventContext.BASKET, true, ECommerceEventType.BASKET_ITEM_COUNT_DECREASED),
    BASKET_ITEM_REMOVED(9, ECommerceEventContext.BASKET, true, ECommerceEventType.BASKET_ITEM_REMOVED),
    ORDER(10, ECommerceEventContext.ORDER, true, ECommerceEventType.ORDER);

    private final int id;

    private final ECommerceEventContext context;

    private final boolean reportable;

    private final ECommerceEventType eventType;

    EcommerceEventDefinition(int id, ECommerceEventContext context, boolean reportable, ECommerceEventType eventType) {

        this.id = id;
        this.context = context;
        this.reportable = reportable;
        this.eventType = eventType;
    }

    public static Optional<? extends EventDefinition> of(ECommerceEventType eventType) {
        return Arrays.stream(values()).filter(d -> eventType == d.getEventType()).findAny();
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public EventContext getContext() {
        return context;
    }

    @Override
    public Boolean getReportable() {
        return reportable;
    }

    @Override
    public EventType getEventType() {
        return eventType;
    }
}