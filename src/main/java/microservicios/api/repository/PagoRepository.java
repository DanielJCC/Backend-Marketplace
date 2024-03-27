package microservicios.api.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import microservicios.api.entities.Pago;

public interface PagoRepository extends JpaRepository<Pago,UUID>{
    List<Pago> findByFechaPagoBetween(LocalDate startDate, LocalDate endDate);
    List<Pago> findByPedidoIdAndMetodoPago(UUID idPedido, String metodoPago);
}
