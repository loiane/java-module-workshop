module order.service {
    exports ecommerce.order.service;
    exports ecommerce.order.core to ecommerce.api;
    requires ecommerce.shared;
    requires jeventbus;
}