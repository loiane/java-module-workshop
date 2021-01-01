package ecommerce.basket.web.rest;

import ecommerce.basket.service.BasketService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/v1/buyer/{buyerId}/basket/create")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CreateBasketCommand {

    @Inject
    BasketService basketService;

    @POST
    public Response clear(@PathParam("buyerId") Long buyerId) {

        basketService.create(buyerId);

        return Response.ok().build();
    }

}
