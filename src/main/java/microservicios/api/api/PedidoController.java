package microservicios.api.api;

import microservicios.api.dto.pedido.PedidoDTO;
import microservicios.api.dto.pedido.PedidoToSaveDTO;
import microservicios.api.service.pedido.PedidoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/orders")
public class PedidoController {
    private final PedidoService pedidoService;
    public PedidoController(PedidoService pedidoService){ this.pedidoService = pedidoService; }

    @GetMapping("/{id}")
    public ResponseEntity<PedidoDTO> getPedidoById(@PathVariable("id") UUID id){
        try{
            PedidoDTO pedidoFinded = pedidoService.buscarPedidoById(id);
            return ResponseEntity.ok().body(pedidoFinded);
        }catch (RuntimeException e){
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping()
    public ResponseEntity<List<PedidoDTO>> getAllPedidos(){
        List<PedidoDTO> pedidos = pedidoService.obtenerTodosLosPedidos();
        return ResponseEntity.ok().body(pedidos);
    }
    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<PedidoDTO>> getPedidosByClienteId(@PathVariable("customerId") UUID costumerId){
        List<PedidoDTO> pedidos = pedidoService.buscarPedidosPorArticulosPorCliente(costumerId);
        return ResponseEntity.ok().body(pedidos);
    }
    @GetMapping("/date-range")
    public ResponseEntity<List<PedidoDTO>> getPedidosByDateRange(@RequestParam LocalDateTime startDate, @RequestParam LocalDateTime endDate){
        List<PedidoDTO> pedidos = pedidoService.buscarPedidosEntreFechas(startDate,endDate);
        return ResponseEntity.ok().body(pedidos);
    }
    @GetMapping("/status")
    public ResponseEntity<List<PedidoDTO>> getPedidosByClienteIdAndStatus(@RequestParam UUID customerId, @RequestParam String status){
        List<PedidoDTO> pedidos = pedidoService.buscarPedidosPorClienteYEstado(customerId, status);
        return ResponseEntity.ok().body(pedidos);
    }
    @PostMapping()
    public ResponseEntity<PedidoDTO> createPedido(@RequestBody PedidoToSaveDTO pedidoToSave){
        PedidoDTO pedidoSaved = pedidoService.guardarPedido(pedidoToSave);
        return ResponseEntity.ok().body(pedidoSaved);
    }
    @PutMapping("/{id}")
    public ResponseEntity<PedidoDTO> updatePedido(@PathVariable("id") UUID id, @RequestBody PedidoToSaveDTO pedidoToUpdate){
        try{
            PedidoDTO pedidoUpdated = pedidoService.actualizarPedido(id, pedidoToUpdate);
            return ResponseEntity.ok().body(pedidoUpdated);
        }catch (RuntimeException e){
            return ResponseEntity.notFound().build();
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePedido(@PathVariable("id") UUID id){
        try{
            pedidoService.eliminarPedido(id);
            return ResponseEntity.ok().body("Pedido eliminado con id: "+id);
        }catch (RuntimeException e){
            return ResponseEntity.notFound().build();
        }

}
}
