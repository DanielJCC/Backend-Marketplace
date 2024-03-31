package microservicios.api.api;

import microservicios.api.dto.detalleEnvio.DetalleEnvioDTO;
import microservicios.api.dto.detalleEnvio.DetalleEnvioToSaveDTO;
import microservicios.api.service.detalleEnvio.DetalleEnvioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/shipping")
public class DetalleEnvioController {
    private final DetalleEnvioService detalleEnvioService;
    public DetalleEnvioController(DetalleEnvioService detalleEnvioService){
        this.detalleEnvioService = detalleEnvioService;
    }
    @GetMapping("/{id}")
    public ResponseEntity<DetalleEnvioDTO> getDetalleEnvioById(@PathVariable("id")UUID id){
        try{
            DetalleEnvioDTO detalleEnvioFinded = detalleEnvioService.buscarDetalleEnvioById(id);
            return ResponseEntity.ok().body(detalleEnvioFinded);
        }catch (RuntimeException e){
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping()
    public ResponseEntity<List<DetalleEnvioDTO>> getDetalleEnvios(){
        List<DetalleEnvioDTO> detalleEnvios = detalleEnvioService.obtenerTodosLosDetalleEnvio();
        return ResponseEntity.ok().body(detalleEnvios);
    }
    @GetMapping("/order/{orderId}")
    public ResponseEntity<DetalleEnvioDTO> getDetalleEnvioByOrderId(@PathVariable("orderId")UUID orderId){
        DetalleEnvioDTO detalleEnvioFinded = detalleEnvioService.buscarDetalleEnvioByPedidoId(orderId);
        return ResponseEntity.ok().body(detalleEnvioFinded);
    }
    @GetMapping("/carrier")
    public ResponseEntity<List<DetalleEnvioDTO>> getDetalleEnviosByTransportadora(@RequestParam String name){
        List<DetalleEnvioDTO> detalleEnviosFinded = detalleEnvioService.buscarDetalleEnvioByTransportadora(name);
        return ResponseEntity.ok().body(detalleEnviosFinded);
    }
    @GetMapping("/order")
    ResponseEntity<List<DetalleEnvioDTO>> getDetalleEnvioByPedidoStatus(@RequestParam String status){
        List<DetalleEnvioDTO> detalleEnviosFinded = detalleEnvioService.buscarDetalleEnvioByStatus(status);
        return ResponseEntity.ok().body(detalleEnviosFinded);
    }
    @PostMapping()
    public ResponseEntity<DetalleEnvioDTO> createDetalleEnvio(@RequestBody DetalleEnvioToSaveDTO detalleEnvioToSaveDTO){
        try {
            DetalleEnvioDTO detalleEnvioCreated = detalleEnvioService.guardarDetalleEnvio(detalleEnvioToSaveDTO);
            return ResponseEntity.ok().body(detalleEnvioCreated);
        }catch (RuntimeException e){
            return ResponseEntity.notFound().build();
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<DetalleEnvioDTO> updateDetalleEnvio(@PathVariable("id")UUID id, @RequestBody DetalleEnvioToSaveDTO detalleEnvioToUpdate){
        try {
            DetalleEnvioDTO detalleEnvioUpdated = detalleEnvioService.actualizarDetalleEnvio(id,detalleEnvioToUpdate);
            return ResponseEntity.ok().body(detalleEnvioUpdated);
        }catch (RuntimeException e){
            return ResponseEntity.notFound().build();
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDetalleEnvio(@PathVariable("id")UUID id){
        try {
            detalleEnvioService.removerDetalleEnvio(id);
            return ResponseEntity.ok().body("Detalle de envio eliminado con id: "+id);
        }catch (RuntimeException e){
            return ResponseEntity.notFound().build();
        }
    }
}
