package ecommerce.order.web.rest;

import ecommerce.order.core.Order;
import ecommerce.order.core.OrderItem;
import ecommerce.order.service.OrderService;
import ecommerce.shared.model.ItemWithCount;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Path("/v1/buyer/{buyerId}/order")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class OrderCommand {

    @Inject
    OrderService orderService;

    @POST
    public Response order(@PathParam("buyerId") Long buyerId, OrderInput input) {

        var order = new Order();
        order.setBuyerId(buyerId);
        order.setBasketId(input.basketId);
        order.setItems(
                input.items
                    .stream()
                    .map(oii->new OrderItem(
                                new ItemWithCount(
                                    oii.productId,
                                    oii.unitPrice,
                                    oii.count)))
                    .collect(toList()));

        orderService.order(order);

        return Response.ok().build();
    }


    public static class OrderItemInput {
        public Long productId;
        public Double unitPrice;
        public Integer count;
    }
    public static class OrderInput {
        public Long basketId;
        public List<OrderItemInput> items;
    }
}
