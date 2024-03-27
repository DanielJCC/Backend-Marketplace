package microservicios.api.dto.pago;

import java.time.LocalDate;

import microservicios.api.dto.pedido.PedidoDTO;

public record PagoToSaveDTO(
    PedidoDTO pedido,
    Float totalPago,
    LocalDate fechaPago,
    String metodoPago
) {

}
