package ecommerce.shared.event;

import jeventbus.shared.EventSource;
import jeventbus.shared.EventListener;

public interface StockAddedListener extends EventListener {

    void onStockAdded(EventSource source);
}
