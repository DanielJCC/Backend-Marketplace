package microservicios.api.service.pago;

import microservicios.api.dto.pago.PagoDTO;
import microservicios.api.dto.pago.PagoToSaveDTO;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface PagoService {
    PagoDTO guardarPago(PagoToSaveDTO pagoToSaveDTO);
    PagoDTO actualizarPago(UUID id, PagoToSaveDTO pagoToUpdate);
    List<PagoDTO> obtenerTodosLosPagos();
    PagoDTO buscarPagoById(UUID id);
    void removerPago(UUID id);
    List<PagoDTO> buscarPagosEntreFechas(LocalDate startDate, LocalDate endDate);
    PagoDTO buscarPagoPorIdPedidoYMetodoPago(UUID idPedido, String metodoPago);
}
