package microservicios.api.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import microservicios.api.entities.ItemPedido;

public interface ItemPedidoRepository extends JpaRepository<ItemPedido,UUID>{
    List<ItemPedido> findByPedidoId(UUID idPedido);
    List<ItemPedido> findByProductoId(UUID idProducto);
    @Query("select sum(ip.cantidad*ip.precioUnitario) from ItemPedido ip where ip.producto.id = ?1 group by ip.producto")
    Float findTotalVentasByProducto(UUID idProducto);
}
