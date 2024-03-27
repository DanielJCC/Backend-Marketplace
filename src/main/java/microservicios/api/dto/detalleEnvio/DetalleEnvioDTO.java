package microservicios.api.dto.detalleEnvio;

import java.util.UUID;

import microservicios.api.dto.pedido.PedidoDTO;

public record DetalleEnvioDTO(
    UUID id,
    PedidoDTO pedido,
    String direccion,
    String transportadora,
    Integer numeroGuia
) {

}
