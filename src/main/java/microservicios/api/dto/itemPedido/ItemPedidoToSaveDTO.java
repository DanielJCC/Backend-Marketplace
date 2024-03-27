package microservicios.api.dto.itemPedido;

import microservicios.api.dto.pedido.PedidoDTO;
import microservicios.api.dto.product.ProductDTO;

public record ItemPedidoToSaveDTO(
    PedidoDTO pedido,
    ProductDTO producto,
    Integer cantidad,
    Float precioUnitario
) {

}
