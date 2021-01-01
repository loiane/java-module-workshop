package ecommerce.catalog.web.rest;

import ecommerce.catalog.service.CatalogService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/v1/catalog/add-item")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AddProductCommand {

    @Inject
    CatalogService catalogService;

    @PUT
    public AddProductOutput add(AddProductInput input) {

        var product = catalogService.add(
                input.productName,
                input.unitPrice);

        var output = new AddProductOutput();
        output.id = product.getId();
        output.productName = product.getTitle();
        output.unitPrice = product.getUnitPrice();
        return output;
    }

    public static class AddProductInput {
        public String productName;
        public Double unitPrice;
    }

    public static class AddProductOutput {
        public Long id;
        public String productName;
        public Double unitPrice;
    }
}
