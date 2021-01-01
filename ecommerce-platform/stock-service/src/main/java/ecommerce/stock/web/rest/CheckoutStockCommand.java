package ecommerce.stock.web.rest;

import ecommerce.shared.model.ItemWithCount;
import ecommerce.stock.service.StockService;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Path("/v1/stock/checkout")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CheckoutStockCommand {

    @Inject
    StockService stockService;

    @PUT
    public Response checkout(CheckoutStockInput input) {

        input.items.forEach(i->stockService.checkout(i.productId, i.count));

        return Response.ok().build();
    }

    public static class CheckoutStockItem {
        public Long productId;
        public Integer count;
    }

    public static class CheckoutStockInput {
        public List<CheckoutStockItem> items = new ArrayList<>();
    }
}
