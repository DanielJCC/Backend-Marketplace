package microservicios.api.dto.pedido;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import microservicios.api.dto.cliente.ClienteDTO;
import microservicios.api.dto.detalleEnvio.DetalleEnvioDTO;
import microservicios.api.dto.itemPedido.ItemPedidoDTO;
import microservicios.api.dto.pago.PagoDTO;

public record PedidoDTO(
    UUID id,
    ClienteDTO cliente,
    LocalDateTime fechaPedido,
    String status,
    List<ItemPedidoDTO> itemsPedidos,
    PagoDTO pago,
    DetalleEnvioDTO detalleEnvio
) {
    public List<ItemPedidoDTO> itemsPedidos(){
        return Collections.unmodifiableList(itemsPedidos);
    }
}
