package microservicios.api.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import microservicios.api.entities.Pedido;
import java.time.LocalDateTime;



public interface PedidoRepository extends JpaRepository<Pedido,UUID> {
    List<Pedido> findByFechaPedidoBetween(LocalDateTime fechaComienzo, LocalDateTime fechaFinal);
    List<Pedido> findByClienteIdAndStatus(UUID idCliente, String status);
    @Query("select p from Pedido p join fetch p.itemsPedidos where p.cliente.id = ?1")
    List<Pedido> findArticulosPedidosByClient(UUID idCliente);
}
