package microservicios.api.service.product;

import microservicios.api.dto.product.ProductDTO;
import microservicios.api.dto.product.ProductMapper;
import microservicios.api.dto.product.ProductToSaveDTO;
import microservicios.api.entities.Product;
import microservicios.api.repository.ProductoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.BDDMockito.given;
import static org.mockito.ArgumentMatchers.any;
import static org.assertj.core.api.Assertions.assertThat;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {
    @Mock
    private ProductMapper productMapper;
    @Mock
    private ProductoRepository productoRepository;
    @InjectMocks
    private ProductServiceImpl productService;
    Product product;
    Product product2;
    @BeforeEach
    void setUp(){
        product = Product.builder()
                .id(UUID.randomUUID())
                .nombre("Product test")
                .price((float) 1000)
                .stock(10)
                .build();
        product2 = Product.builder()
                .id(UUID.randomUUID())
                .nombre("Product2 test")
                .price((float) 1500)
                .stock(5)
                .build();
    }
    @Test
    void givenProduct_whenGuardarProduct_thenReturnProductGuardado(){
        ProductDTO productDTO = new ProductDTO(
                product.getId(),
                product.getNombre(),
                product.getPrice(),
                product.getStock());
        given(productMapper.ToSaveDtoToProduct(any())).willReturn(product);
        given(productMapper.ProductToProductDto(any())).willReturn(productDTO);
        given(productoRepository.save(any())).willReturn(product);
        ProductToSaveDTO productToSave = new ProductToSaveDTO(
                product.getNombre(),
                product.getPrice(),
                product.getStock());
        ProductDTO productSaved = productService.guardarProducto(productToSave);
        assertThat(productSaved).isNotNull();
        assertThat(productSaved.id()).isEqualTo(product.getId());
    }
    @Test
    void givenProductAndId_whenActualizarProduct_thenReturnsProductActualizado(){
        Product productUpdated = Product.builder()
                .id(product.getId())
                .nombre(product.getNombre())
                .price((float) 800)
                .stock(product.getStock())
                .build();
        ProductDTO productUpdatedDTO = new ProductDTO(
                productUpdated.getId(),
                productUpdated.getNombre(),
                productUpdated.getPrice(),
                productUpdated.getStock());
        given(productoRepository.findById(product.getId())).willReturn(Optional.of(product));
        given(productoRepository.save(any())).willReturn(productUpdated);
        given(productMapper.ProductToProductDto(any())).willReturn(productUpdatedDTO);
        ProductToSaveDTO productToUpdate = new ProductToSaveDTO(
                null,
                productUpdated.getPrice(),
                null);
        ProductDTO productActualizado = productService.actualizarProducto(product.getId(),productToUpdate);
        assertThat(productActualizado).isNotNull();
        assertThat(productActualizado.id()).isEqualTo(product.getId());
        assertThat(productActualizado.price()).isEqualTo(productToUpdate.price());
    }
    @Test
    void givenAnId_whenBuscarById_thenReturnsProduct(){
        ProductDTO productFinded = new ProductDTO(
                product.getId(),
                product.getNombre(),
                product.getPrice(),
                product.getStock());
        given(productoRepository.findById(product.getId())).willReturn(Optional.of(product));
        given(productMapper.ProductToProductDto(any())).willReturn(productFinded);
        ProductDTO productEncontrado = productService.buscarProductoById(product.getId());
        assertThat(productEncontrado).isNotNull();
        assertThat(productEncontrado.id()).isEqualTo(product.getId());
    }
    @Test
    void givenProducts_WhenBuscarAllProducts_thenReturnsAllProducts(){
        ProductDTO productDTO = new ProductDTO(
                product.getId(),
                product.getNombre(),
                product.getPrice(),
                product.getStock());
        ProductDTO productDTO2 = new ProductDTO(
                product2.getId(),
                product2.getNombre(),
                product2.getPrice(),
                product2.getStock());
        given(productoRepository.findAll()).willReturn(List.of(product,product2));
        given(productMapper.ProductToProductDto(any())).willReturn(productDTO,productDTO2);
        List<ProductDTO> products = productService.obtenerTodosLosProductos();
        assertThat(products).isNotEmpty();
        assertThat(products.get(0)).isEqualTo(productDTO);
        assertThat(products.get(1)).isEqualTo(productDTO2);
    }
    @Test
    void givenCliente_whenEliminarCliente_thenNothing(){
        given(productoRepository.findById(product.getId())).willReturn(Optional.of(product));
        willDoNothing().given(productoRepository).delete(any());
        productService.removerProducto(product.getId());
        verify(productoRepository,times(1)).delete(any());
    }
    @Test
    void givenProduct_whenBuscarPorNombre_thenReturnsProduct(){
        ProductDTO productFinded = new ProductDTO(
                product.getId(),
                product.getNombre(),
                product.getPrice(),
                product.getStock());
        given(productoRepository.findByNombre(product.getNombre())).willReturn(List.of(product));
        given(productMapper.ProductToProductDto(any())).willReturn(productFinded);
        List<ProductDTO> productsEncontrados = productService.buscarProductoByNombre(product.getNombre());
        assertThat(productsEncontrados).isNotEmpty();
        assertThat(productsEncontrados).size().isEqualTo(1);
        assertThat(productsEncontrados.get(0).id()).isEqualTo(product.getId());
    }
    @Test
    void givenProduct_whenBuscarPorPrice_thenReturnsProduct(){
        ProductDTO productFinded = new ProductDTO(
                product.getId(),
                product.getNombre(),
                product.getPrice(),
                product.getStock());
        given(productoRepository.findByPrice(product.getPrice())).willReturn(List.of(product));
        given(productMapper.ProductToProductDto(any())).willReturn(productFinded);
        List<ProductDTO> productsEncontrados = productService.buscarProductoByPrice(product.getPrice());
        assertThat(productsEncontrados).isNotEmpty();
        assertThat(productsEncontrados).size().isEqualTo(1);
        assertThat(productsEncontrados.get(0).id()).isEqualTo(product.getId());
    }
    @Test
    void givenProduct_whenBuscarPorInStock_thenReturnsProduct(){
        ProductDTO productFinded = new ProductDTO(
                product.getId(),
                product.getNombre(),
                product.getPrice(),
                product.getStock());
        ProductDTO productFinded2 = new ProductDTO(
                product2.getId(),
                product2.getNombre(),
                product2.getPrice(),
                product2.getStock());
        given(productoRepository.findByInStock()).willReturn(List.of(product,product2));
        given(productMapper.ProductToProductDto(any())).willReturn(productFinded,productFinded2);
        List<ProductDTO> productsEncontrados = productService.buscarProductosByInStock();
        assertThat(productsEncontrados).isNotEmpty();
        assertThat(productsEncontrados).size().isEqualTo(2);
        assertThat(productsEncontrados.get(0).id()).isEqualTo(product.getId());
        assertThat(productsEncontrados.get(1).id()).isEqualTo(product2.getId());
    }
}
