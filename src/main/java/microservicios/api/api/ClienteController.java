package microservicios.api.api;

import microservicios.api.dto.cliente.ClienteDTO;
import microservicios.api.dto.cliente.ClienteToSaveDTO;
import microservicios.api.dto.exception.ClienteNotFoundException;
import microservicios.api.service.cliente.ClienteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/costumers")
public class ClienteController {
    private final ClienteService clienteService;
    public ClienteController(ClienteService clienteService){
        this.clienteService = clienteService;
    }
    @GetMapping()
    public ResponseEntity<List<ClienteDTO>> getClientes(){
        List<ClienteDTO> clientesDTO = clienteService.obtenerTodosLosClientes();
        return ResponseEntity.ok().body(clientesDTO);
    }
    @GetMapping("/{id}")
    public ResponseEntity<ClienteDTO> getClienteById(@PathVariable("id") UUID id){
        try{
            ClienteDTO clienteFinded = clienteService.buscarClienteById(id);
            return ResponseEntity.ok().body(clienteFinded);
        }catch (ClienteNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/email/{email}")
    public ResponseEntity<ClienteDTO> getClienteByEmail(@PathVariable("email") String email){
        ClienteDTO clienteFinded = clienteService.buscarClienteByEmail(email);
        return ResponseEntity.ok().body(clienteFinded);
    }
    @GetMapping("/direccion")
    public ResponseEntity<ClienteDTO> getClienteByDireccion(@RequestParam String direccion){
        ClienteDTO clienteFinded = clienteService.buscarClienteByDireccion(direccion);
        return ResponseEntity.ok().body(clienteFinded);
    }
    @GetMapping("/nombre")
    public ResponseEntity<List<ClienteDTO>> getClientesByNombreStartingWith(@RequestParam String prefijo){
        List<ClienteDTO> clientesFinded = clienteService.buscarClientesByPrefijoDeNombre(prefijo);
        return ResponseEntity.ok().body(clientesFinded);
    }
    @PostMapping()
    public ResponseEntity<ClienteDTO> createCliente(@RequestBody ClienteToSaveDTO clienteToSave){
        ClienteDTO clienteSaved = clienteService.guardarCliente(clienteToSave);
        return ResponseEntity.ok().body(clienteSaved);
    }
    @PutMapping("/{id}")
    public ResponseEntity<ClienteDTO> updateCliente(@PathVariable("id") UUID id, @RequestBody ClienteToSaveDTO clienteToUpdate) {
        try {
            ClienteDTO clienteUpdated = clienteService.actualizarCliente(id, clienteToUpdate);
            return ResponseEntity.ok().body(clienteUpdated);
        } catch (ClienteNotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCliente(@PathVariable("id") UUID id){
        try {
            clienteService.removerCliente(id);
            return ResponseEntity.ok().body("Cliente eliminado: " + id);
        } catch (ClienteNotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }
}
