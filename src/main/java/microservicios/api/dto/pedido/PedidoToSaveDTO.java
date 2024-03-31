package microservicios.api.dto.pedido;

import java.time.LocalDateTime;
import java.util.List;

import microservicios.api.dto.cliente.ClienteDTO;
import microservicios.api.dto.detalleEnvio.DetalleEnvioDTO;
import microservicios.api.dto.itemPedido.ItemPedidoDTO;
import microservicios.api.dto.pago.PagoDTO;

public record PedidoToSaveDTO(
    ClienteDTO cliente,
    LocalDateTime fechaPedido,
    String status
) {
    
}
