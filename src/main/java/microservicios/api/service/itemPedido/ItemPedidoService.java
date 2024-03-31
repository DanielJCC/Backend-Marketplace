package microservicios.api.service.itemPedido;

import microservicios.api.dto.itemPedido.ItemPedidoDTO;
import microservicios.api.dto.itemPedido.ItemPedidoToSaveDTO;

import java.util.List;
import java.util.UUID;

public interface ItemPedidoService {
    ItemPedidoDTO guardarItemPedido(ItemPedidoToSaveDTO itemPedidoToSave);
    ItemPedidoDTO actualizarItemPedido(UUID id, ItemPedidoToSaveDTO itemPedidoToUpdate);
    ItemPedidoDTO buscarItemPedidoById(UUID id);
    List<ItemPedidoDTO> obtenerTodosLosItemPedidos();
    void removerItemPedido(UUID id);
    List<ItemPedidoDTO> buscarItemPedidosByPedidoId(UUID idPedido);
    List<ItemPedidoDTO> buscarItemPedidosByProductoId(UUID idProducto);
    ItemPedidoDTO buscarItemPedidoAndTotalVentasByProductoId(UUID idProducto);
}
