package microservicios.api.service.product;

import microservicios.api.dto.product.ProductDTO;
import microservicios.api.dto.product.ProductMapper;
import microservicios.api.dto.product.ProductToSaveDTO;
import microservicios.api.entities.Product;
import microservicios.api.repository.ProductoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ProductServiceImpl implements ProductService{
    private final ProductMapper productMapper;
    private final ProductoRepository productoRepository;

    public ProductServiceImpl(ProductMapper productMapper, ProductoRepository productoRepository){
        this.productMapper = productMapper;
        this.productoRepository = productoRepository;
    }

    @Override
    public ProductDTO guardarProducto(ProductToSaveDTO producto) {
        Product producto1 = productMapper.ToSaveDtoToProduct(producto);
        Product productSaved = productoRepository.save(producto1);
        return productMapper.ProductToProductDto(productSaved);
    }

    @Override
    public ProductDTO actualizarProducto(UUID id, ProductToSaveDTO producto) {
        Product productoFinded = productoRepository.findById(id)
                .orElseThrow(()->new RuntimeException("Product not found"));
        if (producto.nombre()!=null){ productoFinded.setNombre(producto.nombre()); }
        if (producto.price()!=null){ productoFinded.setPrice(producto.price()); }
        if (producto.stock()!=null){ productoFinded.setStock(producto.stock()); }

        Product productSaved = productoRepository.save(productoFinded);
        return productMapper.ProductToProductDto(productSaved);
    }

    @Override
    public List<ProductDTO> obtenerTodosLosProductos() {
        List<Product> products = productoRepository.findAll();
        return products.stream().map(productMapper::ProductToProductDto).toList();
    }

    @Override
    public ProductDTO buscarProductoById(UUID id) {
        Product productoFinded = productoRepository.findById(id)
                .orElseThrow(()->new RuntimeException("Product not found"));
        return productMapper.ProductToProductDto(productoFinded);
    }

    @Override
    public void removerProducto(UUID id) {
        Product productToDelete = productoRepository.findById(id)
                .orElseThrow(()->new RuntimeException("Product not found"));
        productoRepository.delete(productToDelete);
    }

    @Override
    public List<ProductDTO> buscarProductoByNombre(String nombre) {
        List<Product> productosFinded = productoRepository.findByNombre(nombre);
        return productosFinded.stream().map(productMapper::ProductToProductDto).toList();
    }

    @Override
    public List<ProductDTO> buscarProductoByPrice(Float price) {
        List<Product> productosFinded = productoRepository.findByPrice(price);
        return productosFinded.stream().map(productMapper::ProductToProductDto).toList();
    }

    @Override
    public List<ProductDTO> buscarProductosByInStock() {
        List<Product> productosFinded = productoRepository.findByInStock();
        return productosFinded.stream().map(productMapper::ProductToProductDto).toList();
    }
}
