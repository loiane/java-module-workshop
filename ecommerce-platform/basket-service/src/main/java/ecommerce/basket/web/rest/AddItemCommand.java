package ecommerce.basket.web.rest;

import ecommerce.basket.service.BasketService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/v1/buyer/{buyerId}/basket/add-item")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AddItemCommand {

    @Inject
    BasketService basketService;

    @PUT
    public Response add(@PathParam("buyerId") Long buyerId, AddItemInput input) {

        basketService.addItem(
                buyerId,
                input.productId,
                input.unitPrice,
                input.count);

        return Response.ok().build();
    }

    public static class AddItemInput {
        public Long productId;
        public Double unitPrice;
        public Integer count;
    }
}
