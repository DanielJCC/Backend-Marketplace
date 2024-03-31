package microservicios.api.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import microservicios.api.entities.DetalleEnvio;
import java.util.List;

public interface DetalleEnvioRepository extends JpaRepository<DetalleEnvio,UUID>{
    DetalleEnvio findByPedidoId(UUID idPedido);
    List<DetalleEnvio> findByTransportadora(String transportadora);
    List<DetalleEnvio> findByPedidoStatus(String status);
}
