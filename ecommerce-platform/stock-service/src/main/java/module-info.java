module stock.service {
    exports ecommerce.stock.service;
    requires ecommerce.shared;
    requires jeventbus;
    opens ecommerce.stock.service to jeventbus;
}