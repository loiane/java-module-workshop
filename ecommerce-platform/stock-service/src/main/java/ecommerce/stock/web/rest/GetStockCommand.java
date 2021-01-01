package ecommerce.stock.web.rest;

import ecommerce.stock.service.StockService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Path("/v1/stock/product/{productId}")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class GetStockCommand {

    @Inject
    StockService stockService;

    @GET
    public GetStockResponse add(@PathParam("productId") Long productId) {

        var count = stockService.getCount(productId);
        return new GetStockResponse(productId, count);
    }

    public static class GetStockResponse {
        public Long productId;
        public int count;

        public GetStockResponse() {
        }

        public GetStockResponse(Long productId, int count) {
            this.productId = productId;
            this.count = count;
        }
    }

}
