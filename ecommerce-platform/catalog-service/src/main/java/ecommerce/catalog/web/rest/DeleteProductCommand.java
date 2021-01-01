package ecommerce.catalog.web.rest;

import ecommerce.catalog.service.CatalogService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/v1/catalog/delete-item")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class DeleteProductCommand {

    @Inject
    CatalogService catalogService;

    @DELETE
    public Response delete(DeleteProductInput input) {

        catalogService.delete(input.productId);

        return Response.ok().build();
    }

    public static class DeleteProductInput {
        public Long productId;
    }
}
