package microservicios.api.api;

import microservicios.api.dto.product.ProductDTO;
import microservicios.api.dto.product.ProductToSaveDTO;
import microservicios.api.service.product.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {
    private final ProductService productService;
    public ProductController(ProductService productService){
        this.productService = productService;
    }
    @GetMapping()
    public ResponseEntity<List<ProductDTO>> getAllProducts(){
        List<ProductDTO> products = productService.obtenerTodosLosProductos();
        return ResponseEntity.ok().body(products);
    }
    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable("id") UUID id){
        try{
            ProductDTO productFinded = productService.buscarProductoById(id);
            return ResponseEntity.ok().body(productFinded);
        }catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/search")
    public ResponseEntity<List<ProductDTO>> getProductBySearchTerm(@RequestParam String searchTerm){
        List<ProductDTO> productsFinded = productService.buscarProductoByNombre(searchTerm);
        if(productsFinded.isEmpty()){
            try{
                Float price = Float.parseFloat(searchTerm);
                productsFinded = productService.buscarProductoByPrice(price);
            }catch (RuntimeException e){
                return ResponseEntity.ok().body(productsFinded);
            }
        }
        return ResponseEntity.ok().body(productsFinded);
    }
    @GetMapping("/instock")
    public ResponseEntity<List<ProductDTO>> getProductsInStock(){
        List<ProductDTO> productsFinded = productService.buscarProductosByInStock();
        return ResponseEntity.ok().body(productsFinded);
    }
    @PostMapping()
    public ResponseEntity<ProductDTO> createProduct(@RequestBody ProductToSaveDTO productToSave){
        ProductDTO productSaved = productService.guardarProducto(productToSave);
        return ResponseEntity.ok().body(productSaved);
    }
    @PutMapping("/{id}")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable("id") UUID id, @RequestBody ProductToSaveDTO productToUpdate){
        try{
            ProductDTO productUpdated = productService.actualizarProducto(id,productToUpdate);
            return ResponseEntity.ok().body(productUpdated);
        }catch (RuntimeException e){
            return ResponseEntity.notFound().build();
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable("id") UUID id){
        try{
            productService.removerProducto(id);
            return ResponseEntity.ok().body("Producto eliminado con id: "+ id);
        }catch (RuntimeException e){
            return ResponseEntity.notFound().build();
        }
    }
}
