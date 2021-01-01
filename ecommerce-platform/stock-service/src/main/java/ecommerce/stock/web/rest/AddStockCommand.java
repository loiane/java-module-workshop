package ecommerce.stock.web.rest;

import ecommerce.stock.service.StockService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Path("/v1/stock/add")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AddStockCommand {

    @Inject
    StockService stockService;

    @PUT
    public Response add(AddStockInput input) {

        input.items.forEach(i->stockService.add(i.productId, i.count));

        return Response.ok().build();
    }

    public static class AddStockItem {
        public Long productId;
        public Integer count;
    }

    public static class AddStockInput {
        public List<AddStockItem> items = new ArrayList<>();
    }
}
