package ecommerce.catalog.web.rest;

import ecommerce.catalog.service.CatalogService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import static java.lang.String.format;

@Path("/v1/catalog/item/{productId}")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class GetProductCommand {

    @Inject
    CatalogService catalogService;

    @GET
    public GetProductOutput get(@PathParam("productId") Long productId) {

        var product = catalogService.getProduct(productId);

        return product
                .map(p->{
                            var output = new GetProductOutput();
                            output.id = p.getId();
                            output.productName = p.getTitle();
                            output.unitPrice = p.getUnitPrice();
                            return output;
                        })
                .orElseThrow(()->new WebApplicationException(format("Product{%d} not found!", productId)));

    }

    public static class GetProductOutput {
        public Long id;
        public String productName;
        public Double unitPrice;
    }
}
