package ecommerce.shared.event;

import jeventbus.shared.EventType;

public enum ECommerceEventType implements EventType {
    PRODUCT_ADDED("onProductAdded"),
    PRODUCT_DELETED("onProductDeleted"),
    STOCK_ADDED("onStockAdded"),
    STOCK_CHECKOUTED("onStockCheckouted"),
    BASKET_CREATED("onBasketCreated"),
    BASKET_CLEARED("onBasketCleared"),
    BASKET_ITEM_ADDED("onBasketItemAdded"),
    BASKET_ITEM_COUNT_DECREASED("onBasketItemCountDecreased"),
    BASKET_ITEM_REMOVED("onBaskedItemRemoved"),
    ORDER("onOrder");

    private String methodName;

    ECommerceEventType(String methodName) {
        this.methodName = methodName;
    }

    @Override
    public String getMethodName() {
        return methodName;
    }
}
