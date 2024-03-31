package microservicios.api.api;

import microservicios.api.dto.pago.PagoDTO;
import microservicios.api.dto.pago.PagoToSaveDTO;
import microservicios.api.service.pago.PagoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/payments")
public class PagoController {
    private final PagoService pagoService;
    public PagoController(PagoService pagoService){
        this.pagoService = pagoService;
    }

    @GetMapping()
    public ResponseEntity<List<PagoDTO>> getPagos(){
        List<PagoDTO> pagos = pagoService.obtenerTodosLosPagos();
        return ResponseEntity.ok().body(pagos);
    }
    @GetMapping("/{id}")
    public ResponseEntity<PagoDTO> getPagoById(@PathVariable("id")UUID id){
        try {
            PagoDTO pagoFinded = pagoService.buscarPagoById(id);
            return ResponseEntity.ok().body(pagoFinded);
        }catch (RuntimeException e){
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/order/{orderId}")
    public ResponseEntity<PagoDTO> getPagoByOrderId(@PathVariable("orderId")UUID orderId,@RequestParam String metodoPago){
        PagoDTO pagoFinded = pagoService.buscarPagoPorIdPedidoYMetodoPago(orderId, metodoPago);
        return ResponseEntity.ok().body(pagoFinded);
    }
    @GetMapping("/date-range")
    public ResponseEntity<List<PagoDTO>> getPagosByDateRange(@RequestParam LocalDate startDate, @RequestParam LocalDate endDate){
        List<PagoDTO> pagosFinded = pagoService.buscarPagosEntreFechas(startDate, endDate);
        return ResponseEntity.ok().body(pagosFinded);
    }
    @PostMapping()
    public ResponseEntity<PagoDTO> createPago(@RequestBody PagoToSaveDTO pagoToSave){
        PagoDTO pagoSaved = pagoService.guardarPago(pagoToSave);
        return ResponseEntity.ok().body(pagoSaved);
    }
    @PutMapping("/{id}")
    public ResponseEntity<PagoDTO> updatePago(@PathVariable("id")UUID id, @RequestBody PagoToSaveDTO pagoToUpdate){
        try{
            PagoDTO pagoUpdated = pagoService.actualizarPago(id, pagoToUpdate);
            return ResponseEntity.ok().body(pagoUpdated);
        }catch (RuntimeException e){
            return ResponseEntity.notFound().build();
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePago(@PathVariable("id")UUID id){
        try{
            pagoService.removerPago(id);
            return ResponseEntity.ok().body("Pago eliminado con id: "+id);
        }catch (RuntimeException e){
            return ResponseEntity.notFound().build();
        }
    }
}
