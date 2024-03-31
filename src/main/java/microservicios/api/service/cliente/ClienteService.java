package microservicios.api.service.cliente;

import java.util.List;
import java.util.UUID;

import microservicios.api.dto.cliente.ClienteDTO;
import microservicios.api.dto.cliente.ClienteToSaveDTO;

public interface ClienteService {
    ClienteDTO guardarCliente(ClienteToSaveDTO cliente);
    ClienteDTO actualizarCliente(UUID id, ClienteToSaveDTO cliente);
    List<ClienteDTO> obtenerTodosLosClientes();
    ClienteDTO buscarClienteById(UUID id);
    void removerCliente(UUID id);
    ClienteDTO buscarClienteByEmail(String email);
    ClienteDTO buscarClienteByDireccion(String direccion);
    List<ClienteDTO> buscarClientesByPrefijoDeNombre(String prefijo);
}
