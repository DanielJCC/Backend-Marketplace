package microservicios.api.service.detalleEnvio;

import microservicios.api.dto.detalleEnvio.DetalleEnvioDTO;
import microservicios.api.dto.detalleEnvio.DetalleEnvioToSaveDTO;

import java.util.List;
import java.util.UUID;

public interface DetalleEnvioService {
    DetalleEnvioDTO guardarDetalleEnvio(DetalleEnvioToSaveDTO detalleEnvioToSaveDTO);
    DetalleEnvioDTO actualizarDetalleEnvio(UUID id, DetalleEnvioToSaveDTO detalleEnvioToUpdate);
    List<DetalleEnvioDTO> obtenerTodosLosDetalleEnvio();
    DetalleEnvioDTO buscarDetalleEnvioById(UUID id);
    void removerDetalleEnvio(UUID id);
    DetalleEnvioDTO buscarDetalleEnvioByPedidoId(UUID idPedido);
    List<DetalleEnvioDTO> buscarDetalleEnvioByTransportadora(String transportadora);
    List<DetalleEnvioDTO> buscarDetalleEnvioByStatus(String status);
}
