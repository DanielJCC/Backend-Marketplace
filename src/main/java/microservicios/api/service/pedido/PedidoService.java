package microservicios.api.service.pedido;

import microservicios.api.dto.pedido.PedidoDTO;
import microservicios.api.dto.pedido.PedidoToSaveDTO;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface PedidoService {
    PedidoDTO guardarPedido(PedidoToSaveDTO pedido);
    PedidoDTO actualizarPedido(UUID id, PedidoToSaveDTO pedidoToUpdate);
    List<PedidoDTO> obtenerTodosLosPedidos();
    PedidoDTO buscarPedidoById(UUID id);
    void eliminarPedido(UUID id);
    List<PedidoDTO> buscarPedidosEntreFechas(LocalDateTime fechaComienzo, LocalDateTime fechaFinal);
    List<PedidoDTO> buscarPedidosPorClienteYEstado(UUID idCliente, String status);
    List<PedidoDTO> buscarPedidosPorArticulosPorCliente(UUID idCliente);
}
