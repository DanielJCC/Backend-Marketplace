package microservicios.api.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import microservicios.api.entities.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente,UUID>{
    List<Cliente> findByEmail(String email);
    List<Cliente> findByDireccionLike(String direccion);
    List<Cliente> findByNombreStartingWith(String prefix);
}
