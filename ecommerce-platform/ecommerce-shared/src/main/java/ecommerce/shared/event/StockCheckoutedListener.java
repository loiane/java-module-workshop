package ecommerce.shared.event;

import jeventbus.shared.EventSource;
import jeventbus.shared.EventListener;

public interface StockCheckoutedListener extends EventListener {

    void onStockCheckouted(EventSource eventSource);
}
