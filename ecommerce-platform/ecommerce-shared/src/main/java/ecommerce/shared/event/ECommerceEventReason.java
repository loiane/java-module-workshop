package ecommerce.shared.event;

import jeventbus.streaming.EventReason;

public enum ECommerceEventReason implements EventReason<ECommerceEventReason> {
    BUY_SOMETHING, FINDING_CHEAPER_OTHER_PLATFORM, OUT_OF_STOCK, I_LIKED_IT_TOO_MUCH, SOLD_OUT, ORDER;

    @Override
    public ECommerceEventReason fromName(String name) {
        return null;
    }
}









