package microservicios.api.dto.itemPedido;

import java.util.UUID;

import microservicios.api.dto.pedido.PedidoDTO;
import microservicios.api.dto.product.ProductDTO;

public record ItemPedidoDTO(
    UUID id,
    PedidoDTO pedido,
    ProductDTO producto,
    Integer cantidad,
    Float precioUnitario
) {

}
