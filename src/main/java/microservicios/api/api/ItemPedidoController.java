package microservicios.api.api;

import microservicios.api.dto.itemPedido.ItemPedidoDTO;
import microservicios.api.dto.itemPedido.ItemPedidoToSaveDTO;
import microservicios.api.service.itemPedido.ItemPedidoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/order-items")
public class ItemPedidoController {
    private final ItemPedidoService itemPedidoService;
    public ItemPedidoController(ItemPedidoService itemPedidoService){
        this.itemPedidoService = itemPedidoService;
    }
    @GetMapping("/{id}")
    public ResponseEntity<ItemPedidoDTO> getItemPedidoById(@PathVariable("id")UUID id){
        try{
            ItemPedidoDTO itemPedidoFinded = itemPedidoService.buscarItemPedidoById(id);
            return ResponseEntity.ok().body(itemPedidoFinded);
        }catch (RuntimeException e){
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping()
    public ResponseEntity<List<ItemPedidoDTO>> getAllItemPedidos(){
        List<ItemPedidoDTO> itemsPedidos = itemPedidoService.obtenerTodosLosItemPedidos();
        return ResponseEntity.ok().body(itemsPedidos);
    }
    @GetMapping("/order/{orderId}")
    public ResponseEntity<List<ItemPedidoDTO>> getItemsPedidosByPedidoId(@PathVariable("orderId")UUID orderId){
        List<ItemPedidoDTO> itemsPedidosFinded = itemPedidoService.buscarItemPedidosByPedidoId(orderId);
        return ResponseEntity.ok().body(itemsPedidosFinded);
    }
    @GetMapping("/product/{productId}")
    public ResponseEntity<List<ItemPedidoDTO>> getItemsPedidosByProductId(@PathVariable("productId")UUID productId){
        List<ItemPedidoDTO> itemsPedidosFinded = itemPedidoService.buscarItemPedidosByProductoId(productId);
        return ResponseEntity.ok().body(itemsPedidosFinded);
    }
    @GetMapping("/total-sales/{productId}")
    public ResponseEntity<Float> getTotalVentasByProductId(@PathVariable("productId")UUID productId){
        Float totalVentasFinded = itemPedidoService.buscarTotalVentasByProductoId(productId);
        return ResponseEntity.ok().body(totalVentasFinded);
    }
    @PostMapping()
    public ResponseEntity<ItemPedidoDTO> createItemPedido(@RequestBody ItemPedidoToSaveDTO itemPedidoToSave){
        ItemPedidoDTO itemPedidoSaved = itemPedidoService.guardarItemPedido(itemPedidoToSave);
        return ResponseEntity.ok().body(itemPedidoSaved);
    }
    @PutMapping("/{id}")
    public ResponseEntity<ItemPedidoDTO> updateItemPedido(@PathVariable("id") UUID id, @RequestBody ItemPedidoToSaveDTO itemPedidoToUpdate){
        try{
            ItemPedidoDTO itemPedidoUpdated = itemPedidoService.actualizarItemPedido(id, itemPedidoToUpdate);
            return ResponseEntity.ok().body(itemPedidoUpdated);
        }catch (RuntimeException e){
            return ResponseEntity.notFound().build();
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteItemPedido(@PathVariable("id")UUID id){
        try{
            itemPedidoService.removerItemPedido(id);
            return ResponseEntity.ok().body("Item pedido eliminado con id: "+id);
        }catch (RuntimeException e){
            return ResponseEntity.notFound().build();
        }
    }
}
