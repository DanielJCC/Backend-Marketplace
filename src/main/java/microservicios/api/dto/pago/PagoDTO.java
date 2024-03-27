package microservicios.api.dto.pago;

import java.time.LocalDate;
import java.util.UUID;

import microservicios.api.dto.pedido.PedidoDTO;

public record PagoDTO(
    UUID id,
    PedidoDTO pedido,
    Float totalPago,
    LocalDate fechaPago,
    String metodoPago
) {

}
