package ecommerce.shared.event;

import jeventbus.streaming.EventContext;

public enum ECommerceEventContext implements EventContext {
    BASKET,
    CATALOG,
    ORDER,
    STOCK;

    @Override
    public EventContext fromName(String name) {
        return valueOf(name);
    }
}