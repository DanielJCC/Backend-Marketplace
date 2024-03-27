package microservicios.api.dto.product;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import microservicios.api.dto.itemPedido.ItemPedidoDTO;

public record ProductDTO(
    UUID id,
    String nombre,
    Float price,
    Integer stock,
    List<ItemPedidoDTO> itemsPedidos
) {
    public List<ItemPedidoDTO> itemPedidos(){
        return Collections.unmodifiableList(itemsPedidos);
    }
}
