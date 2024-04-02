package microservicios.api.service.itemPedido;

import microservicios.api.dto.cliente.ClienteDTO;
import microservicios.api.dto.itemPedido.ItemPedidoDTO;
import microservicios.api.dto.itemPedido.ItemPedidoMapper;
import microservicios.api.dto.itemPedido.ItemPedidoToSaveDTO;
import microservicios.api.dto.pedido.PedidoDTO;
import microservicios.api.dto.product.ProductDTO;
import microservicios.api.entities.*;
import microservicios.api.repository.ItemPedidoRepository;
import microservicios.api.repository.PedidoRepository;
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
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


@ExtendWith(MockitoExtension.class)
class ItemPedidoServiceImplTest {
    @Mock
    private ItemPedidoMapper itemPedidoMapper;
    @Mock
    private ItemPedidoRepository itemPedidoRepository;
    @Mock
    private ProductoRepository productoRepository;
    @Mock
    private PedidoRepository pedidoRepository;
    @InjectMocks
    private ItemPedidoServiceImpl itemPedidoService;
    Cliente cliente;
    ClienteDTO clienteDTO;
    Pedido pedido,pedido2;
    PedidoDTO pedidoDTO,pedidoDTO2;
    Product product, product2;
    ProductDTO productDTO, productDTO2;
    ItemPedido itemPedido,itemPedido2;
    @BeforeEach
    void setUp(){
        cliente = Cliente.builder()
                .id(UUID.randomUUID())
                .nombre("Test")
                .email("test@test.com")
                .direccion("Cra Test #Test-01")
                .build();
        clienteDTO = new ClienteDTO(
                cliente.getId(),
                cliente.getNombre(),
                cliente.getEmail(),
                cliente.getDireccion());
        pedido = Pedido.builder()
                .id(UUID.randomUUID())
                .cliente(cliente)
                .fechaPedido(LocalDateTime.now())
                .status("PENDIENTE")
                .build();
        pedidoDTO = new PedidoDTO(
                pedido.getId(),
                clienteDTO,
                pedido.getFechaPedido(),
                pedido.getStatus());
        pedido2 = Pedido.builder()
                .id(UUID.randomUUID())
                .cliente(cliente)
                .fechaPedido(LocalDateTime.now())
                .status("PENDIENTE")
                .build();
        pedidoDTO2 = new PedidoDTO(
                pedido2.getId(),
                clienteDTO,
                pedido2.getFechaPedido(),
                pedido2.getStatus());
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
        productDTO = new ProductDTO(
                product.getId(),
                product.getNombre(),
                product.getPrice(),
                product.getStock());
        productDTO2 = new ProductDTO(
                product2.getId(),
                product2.getNombre(),
                product2.getPrice(),
                product2.getStock());
        itemPedido = ItemPedido.builder()
                .id(UUID.randomUUID())
                .pedido(pedido)
                .producto(product)
                .cantidad(5)
                .precioUnitario((float) 1000)
                .build();
        itemPedido2 = ItemPedido.builder()
                .id(UUID.randomUUID())
                .pedido(pedido)
                .producto(product2)
                .cantidad(4)
                .precioUnitario((float) 1500)
                .build();
    }
    @Test
    void givenItemPedido_whenGuardar_thenReturnsItemPedidoGuardado(){
        ItemPedidoDTO itemPedidoDTO = new ItemPedidoDTO(
                itemPedido.getId(),
                pedidoDTO,
                productDTO,
                itemPedido.getCantidad(),
                itemPedido.getPrecioUnitario());
        given(productoRepository.findById(product.getId())).willReturn(Optional.of(product));
        given(pedidoRepository.findById(pedido.getId())).willReturn(Optional.of(pedido));
        given(itemPedidoMapper.DtoToSaveToEntity(any())).willReturn(itemPedido);
        given(itemPedidoRepository.save(any())).willReturn(itemPedido);
        given(itemPedidoMapper.EntityToDto(any())).willReturn(itemPedidoDTO);
        ItemPedidoToSaveDTO itemPedidoToSave = new ItemPedidoToSaveDTO(
                pedidoDTO,
                productDTO,
                itemPedido.getCantidad(),
                itemPedido.getPrecioUnitario());
        ItemPedidoDTO itemPedidoSaved = itemPedidoService.guardarItemPedido(itemPedidoToSave);
        assertThat(itemPedidoSaved).isNotNull();
        assertThat(itemPedidoSaved.id()).isEqualTo(itemPedido.getId());
    }
    @Test
    void givenItemPedidoAndId_whenActualizar_thenReturnsItemPedidoActualizado(){
        ItemPedido itemPedidoUpdated = ItemPedido.builder()
                .id(itemPedido.getId())
                .pedido(itemPedido.getPedido())
                .producto(itemPedido.getProducto())
                .cantidad(8)
                .precioUnitario(itemPedido.getPrecioUnitario())
                .build();
        ItemPedidoDTO itemPedidoUpdatedDTO = new ItemPedidoDTO(
                itemPedidoUpdated.getId(),
                pedidoDTO,
                productDTO,
                itemPedidoUpdated.getCantidad(),
                itemPedidoUpdated.getPrecioUnitario());
        given(itemPedidoRepository.findById(itemPedido.getId())).willReturn(Optional.of(itemPedido));
        given(itemPedidoRepository.save(any())).willReturn(itemPedidoUpdated);
        given(itemPedidoMapper.EntityToDto(any())).willReturn(itemPedidoUpdatedDTO);
        ItemPedidoToSaveDTO itemPedidoToUpdate = new ItemPedidoToSaveDTO(
                new PedidoDTO(pedido.getId(),null,null,null),
                new ProductDTO(product.getId(),null,null,null),
                itemPedidoUpdated.getCantidad(),
                null);
        ItemPedidoDTO itemPedidoActualizado = itemPedidoService.actualizarItemPedido(itemPedido.getId(),itemPedidoToUpdate);
        assertThat(itemPedidoActualizado).isNotNull();
        assertThat(itemPedidoActualizado.id()).isEqualTo(itemPedido.getId());
        assertThat(itemPedidoActualizado.cantidad()).isEqualTo(itemPedidoUpdated.getCantidad());
    }
    @Test
    void givenAnId_whenBuscarById_thenReturnsItemPedido(){
        ItemPedidoDTO itemPedidoDTO = new ItemPedidoDTO(
                itemPedido.getId(),
                pedidoDTO,
                productDTO,
                itemPedido.getCantidad(),
                itemPedido.getPrecioUnitario());
        given(itemPedidoRepository.findById(itemPedido.getId())).willReturn(Optional.of(itemPedido));
        given(itemPedidoMapper.EntityToDto(any())).willReturn(itemPedidoDTO);
        ItemPedidoDTO itemPedidoFinded = itemPedidoService.buscarItemPedidoById(itemPedido.getId());
        assertThat(itemPedidoFinded).isNotNull();
        assertThat(itemPedidoFinded.id()).isEqualTo(itemPedido.getId());
    }
    //AQUI FALTAN EL DE LEER TODOS
    @Test
    void givenItemPedido_whenEliminar_thenNothing(){
        given(itemPedidoRepository.findById(itemPedido.getId())).willReturn(Optional.of(itemPedido));
        willDoNothing().given(itemPedidoRepository).delete(any());
        itemPedidoService.removerItemPedido(itemPedido.getId());
        verify(itemPedidoRepository,times(1)).delete(any());
    }
}