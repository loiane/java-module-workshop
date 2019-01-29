module ecommerce.order {
    exports ecommerce.order.service;
    exports ecommerce.order.core to ecommerce.api;

    requires jeventbus;
    requires ecommerce.shared;
}