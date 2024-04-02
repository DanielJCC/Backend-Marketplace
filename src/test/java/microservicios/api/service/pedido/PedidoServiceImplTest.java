package microservicios.api.service.pedido;

import microservicios.api.dto.cliente.ClienteDTO;
import microservicios.api.dto.pedido.PedidoDTO;
import microservicios.api.dto.pedido.PedidoMapper;
import microservicios.api.dto.pedido.PedidoToSaveDTO;
import microservicios.api.entities.Cliente;
import microservicios.api.entities.Pedido;
import microservicios.api.repository.ClienteRepository;
import microservicios.api.repository.PedidoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.BDDMockito.given;
import static org.mockito.ArgumentMatchers.any;
import static org.assertj.core.api.Assertions.assertThat;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class PedidoServiceImplTest {
    @Mock
    private PedidoMapper pedidoMapper;
    @Mock
    private PedidoRepository pedidoRepository;
    @Mock
    private ClienteRepository clienteRepository;
    @InjectMocks
    private PedidoServiceImpl pedidoService;
    Pedido pedido;
    Pedido pedido2;
    Cliente cliente;
    ClienteDTO clienteDTO;
    @BeforeEach
    void setUp(){
        cliente = Cliente.builder()
                .id(UUID.randomUUID())
                .nombre("Test")
                .email("test@test.com")
                .direccion("Cra Test #Test-01")
                .build();
        pedido = Pedido.builder()
                .id(UUID.randomUUID())
                .cliente(cliente)
                .fechaPedido(LocalDateTime.now())
                .status("PENDIENTE")
                .build();
        pedido2 = Pedido.builder()
                .id(UUID.randomUUID())
                .cliente(cliente)
                .fechaPedido(LocalDateTime.of(2024,3,20,15,0))
                .status("PENDIENTE")
                .build();
        clienteDTO = new ClienteDTO(
                cliente.getId(),
                cliente.getNombre(),
                cliente.getEmail(),
                cliente.getDireccion());
    }
    @Test
    void givenPedido_whenGuardarPedido_thenReturnsPedidoGuardado(){
        PedidoDTO pedidoDTO = new PedidoDTO(
                pedido.getId(),
                clienteDTO,
                pedido.getFechaPedido(),
                pedido.getStatus());
        given(clienteRepository.findById(cliente.getId())).willReturn(Optional.of(cliente));
        given(pedidoMapper.ToSaveDtoToPedido(any())).willReturn(pedido);
        given(pedidoRepository.save(any())).willReturn(pedido);
        given(pedidoMapper.PedidoToPedidoDto(any())).willReturn(pedidoDTO);
        PedidoToSaveDTO pedidoToSave = new PedidoToSaveDTO(
                clienteDTO,
                pedido.getFechaPedido(),
                pedido.getStatus());
        PedidoDTO pedidoSaved = pedidoService.guardarPedido(pedidoToSave);
        assertThat(pedidoSaved).isNotNull();
        assertThat(pedidoSaved.id()).isEqualTo(pedido.getId());
        assertThat(pedidoSaved.cliente().id()).isEqualTo(cliente.getId());
    }
    @Test
    void givenPedidoAndId_whenActualizarPedido_thenReturnsPedidoActualizado(){
        Pedido pedidoUpdated = Pedido.builder()
                .id(pedido.getId())
                .cliente(pedido.getCliente())
                .fechaPedido(pedido.getFechaPedido())
                .status("ENVIADO")
                .build();
        PedidoDTO pedidoUpdatedDTO = new PedidoDTO(
                pedido.getId(),
                clienteDTO,
                pedidoUpdated.getFechaPedido(),
                pedidoUpdated.getStatus());
        given(pedidoRepository.findById(pedido.getId())).willReturn(Optional.of(pedido));
        given(pedidoRepository.save(any())).willReturn(pedidoUpdated);
        given(pedidoMapper.PedidoToPedidoDto(any())).willReturn(pedidoUpdatedDTO);
        PedidoToSaveDTO pedidoToUpdate = new PedidoToSaveDTO(
                new ClienteDTO(cliente.getId(),null,null,null),
                pedido.getFechaPedido(),
                "ENVIADO");
        PedidoDTO pedidoActualizado = pedidoService.actualizarPedido(pedido.getId(),pedidoToUpdate);
        assertThat(pedidoActualizado).isNotNull();
        assertThat(pedidoActualizado.id()).isEqualTo(pedido.getId());
        assertThat(pedidoActualizado.status()).isEqualTo(pedidoToUpdate.status());
    }
    @Test
    void givenAnId_whenBuscarById_thenReturnsPedido(){
        PedidoDTO pedidoDTO = new PedidoDTO(
                pedido.getId(),
                clienteDTO,
                pedido.getFechaPedido(),
                pedido.getStatus());
        given(pedidoRepository.findById(pedido.getId())).willReturn(Optional.of(pedido));
        given(pedidoMapper.PedidoToPedidoDto(any())).willReturn(pedidoDTO);
        PedidoDTO pedidoFinded = pedidoService.buscarPedidoById(pedido.getId());
        assertThat(pedidoFinded).isNotNull();
        assertThat(pedidoFinded.id()).isEqualTo(pedido.getId());
    }
    @Test
    void givenPedidos_WhenBuscarAllPedidos_thenReturnsAllPedidos(){
        PedidoDTO pedidoDTO = new PedidoDTO(
                pedido.getId(),
                clienteDTO,
                pedido.getFechaPedido(),
                pedido.getStatus());
        PedidoDTO pedidoDTO2 = new PedidoDTO(
                pedido2.getId(),
                clienteDTO,
                pedido2.getFechaPedido(),
                pedido2.getStatus());
        given(pedidoRepository.findAll()).willReturn(List.of(pedido,pedido2));
        given(pedidoMapper.PedidoToPedidoDto(any())).willReturn(pedidoDTO,pedidoDTO2);
        List<PedidoDTO> pedidos = pedidoService.obtenerTodosLosPedidos();
        assertThat(pedidos).isNotEmpty();
        assertThat(pedidos.get(0)).isEqualTo(pedidoDTO);
        assertThat(pedidos.get(1)).isEqualTo(pedidoDTO2);
    }
    @Test
    void givenPedido_whenEliminarPedido_thenNothing(){
        given(pedidoRepository.findById(pedido.getId())).willReturn(Optional.of(pedido));
        willDoNothing().given(pedidoRepository).delete(any());
        pedidoService.eliminarPedido(pedido.getId());
        verify(pedidoRepository,times(1)).delete(any());
    }
    @Test
    void givenPedidos_whenBuscarEntreFechas_thenReturnsPedidos(){
        PedidoDTO pedidoDTO = new PedidoDTO(
                pedido.getId(),
                clienteDTO,
                pedido.getFechaPedido(),
                pedido.getStatus());
        PedidoDTO pedidoDTO2 = new PedidoDTO(
                pedido2.getId(),
                clienteDTO,
                pedido2.getFechaPedido(),
                pedido2.getStatus());
        LocalDateTime startDate = LocalDateTime.of(2024,3,15,8,30);
        LocalDateTime endDate = LocalDateTime.of(2024,3,25,20,0);
        given(pedidoRepository.findByFechaPedidoBetween(startDate,endDate)).willReturn(List.of(pedido2));
        given(pedidoMapper.PedidoToPedidoDto(any())).willReturn(pedidoDTO2);
        List<PedidoDTO> pedidosFinded = pedidoService.buscarPedidosEntreFechas(startDate,endDate);
        assertThat(pedidosFinded).isNotEmpty();
        assertThat(pedidosFinded).size().isEqualTo(1);
        assertThat(pedidosFinded.get(0).id()).isEqualTo(pedido2.getId());
    }
    @Test
    void givenPedido_whenBuscarByClienteAndStatus_thenReturnsPedido(){
        PedidoDTO pedidoDTO = new PedidoDTO(
                pedido.getId(),
                clienteDTO,
                pedido.getFechaPedido(),
                pedido.getStatus());
        PedidoDTO pedidoDTO2 = new PedidoDTO(
                pedido2.getId(),
                clienteDTO,
                pedido2.getFechaPedido(),
                "ENVIADO");
        given(pedidoRepository.findByClienteIdAndStatus(pedido.getCliente().getId(),pedido.getStatus())).willReturn(List.of(pedido));
        given(pedidoMapper.PedidoToPedidoDto(any())).willReturn(pedidoDTO);
        List<PedidoDTO> pedidosFinded = pedidoService.buscarPedidosPorClienteYEstado(pedido.getCliente().getId(),pedido.getStatus());
        assertThat(pedidosFinded).isNotEmpty();
        assertThat(pedidosFinded).size().isEqualTo(1);
        assertThat(pedidosFinded.get(0).id()).isEqualTo(pedido.getId());
    }
    @Test
    void givenPedidos_whenBuscarByCliente_thenReturnsPedidos(){
        PedidoDTO pedidoDTO = new PedidoDTO(
                pedido.getId(),
                clienteDTO,
                pedido.getFechaPedido(),
                pedido.getStatus());
        PedidoDTO pedidoDTO2 = new PedidoDTO(
                pedido2.getId(),
                clienteDTO,
                pedido2.getFechaPedido(),
                pedido2.getStatus());
        given(pedidoRepository.findArticulosPedidosByClient(pedido.getCliente().getId())).willReturn(List.of(pedido,pedido2));
        given(pedidoMapper.PedidoToPedidoDto(any())).willReturn(pedidoDTO,pedidoDTO2);
        List<PedidoDTO> pedidosFinded = pedidoService.buscarPedidosPorArticulosPorCliente(pedido.getCliente().getId());
        assertThat(pedidosFinded).isNotEmpty();
        assertThat(pedidosFinded).size().isEqualTo(2);
        assertThat(pedidosFinded.get(0).id()).isEqualTo(pedido.getId());
        assertThat(pedidosFinded.get(1).id()).isEqualTo(pedido2.getId());
    }
}