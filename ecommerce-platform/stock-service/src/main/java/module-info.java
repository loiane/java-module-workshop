module stock.service {
    exports ecommerce.stock.service;
    requires ecommerce.shared;
    requires jeventbus.core;
    opens ecommerce.stock.service to jeventbus.core;
}