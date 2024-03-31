package microservicios.api.service.product;

import microservicios.api.dto.product.ProductDTO;
import microservicios.api.dto.product.ProductToSaveDTO;

import java.util.List;
import java.util.UUID;

public interface ProductService {
    ProductDTO guardarProducto(ProductToSaveDTO producto);
    ProductDTO actualizarProducto(UUID id, ProductToSaveDTO producto);
    List<ProductDTO> obtenerTodosLosProductos();
    ProductDTO buscarProductoById(UUID id);
    void removerProducto(UUID id);
    List<ProductDTO> buscarProductoByNombre(String nombre);
    List<ProductDTO> buscarProductoByPrice(Float price);
    List<ProductDTO> buscarProductosByInStock();
}
