package microservicios.api.dto.detalleEnvio;

import microservicios.api.dto.pedido.PedidoDTO;

public record DetalleEnvioToSaveDTO(
    PedidoDTO pedido,
    String direccion,
    String transportadora,
    Integer numeroGuia
) {

}
