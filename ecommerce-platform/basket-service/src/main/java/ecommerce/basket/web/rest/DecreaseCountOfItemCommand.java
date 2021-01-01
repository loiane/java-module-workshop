package ecommerce.basket.web.rest;

import ecommerce.basket.service.BasketService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/v1/buyer/{buyerId}/basket/dec-item")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class DecreaseCountOfItemCommand {

    @Inject
    BasketService basketService;

    @POST
    public Response decrease(@PathParam("buyerId") Long buyerId,
                         DecreaseCountOfItemInput input) {

        basketService.decCountOfItem(
                buyerId,
                input.productId,
                input.count);

        return Response.ok().build();
    }

    public static class DecreaseCountOfItemInput {
        public Long productId;
        public Integer count;
    }
}
