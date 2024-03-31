package microservicios.api.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import microservicios.api.entities.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente,UUID>{
    Cliente findByEmail(String email);
    Cliente findByDireccion(String direccion);
    List<Cliente> findByNombreStartingWith(String prefix);
}
