package microservicios.api.dto.cliente;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import microservicios.api.dto.pedido.PedidoDTO;

public record ClienteDTO(
    UUID id,
    String nombre,
    String email,
    String direccion,
    List<PedidoDTO> pedidos
) {
    public List<PedidoDTO> pedidos(){
        return Collections.unmodifiableList(pedidos);
    }
}
