package ecommerce.basket.web.rest;

import ecommerce.basket.service.BasketService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/v1/buyer/{buyerId}/basket/remove-item")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RemoveItemCommand {

    @Inject
    BasketService basketService;

    @DELETE
    public Response remove(@PathParam("buyerId") Long buyerId,
                       RemoveItemRequest request) {

        basketService.removeItem(
                buyerId,
                request.productId);

        return Response.ok().build();
    }

    public static class RemoveItemRequest {
        public Long productId;
    }

}
