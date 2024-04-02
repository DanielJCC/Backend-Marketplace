package microservicios.api.service.detalleEnvio;

import microservicios.api.dto.cliente.ClienteDTO;
import microservicios.api.dto.detalleEnvio.DetalleEnvioDTO;
import microservicios.api.dto.detalleEnvio.DetalleEnvioMapper;
import microservicios.api.dto.detalleEnvio.DetalleEnvioToSaveDTO;
import microservicios.api.dto.pedido.PedidoDTO;
import microservicios.api.entities.Cliente;
import microservicios.api.entities.DetalleEnvio;
import microservicios.api.entities.Pedido;
import microservicios.api.repository.DetalleEnvioRepository;
import microservicios.api.repository.PedidoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.assertj.core.api.InstanceOfAssertFactories.LIST;
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
class DetalleEnvioServiceImplTest {
    @Mock
    private DetalleEnvioMapper detalleEnvioMapper;
    @Mock
    private DetalleEnvioRepository detalleEnvioRepository;
    @Mock
    private PedidoRepository pedidoRepository;
    @InjectMocks
    private DetalleEnvioServiceImpl detalleEnvioService;
    Cliente cliente;
    ClienteDTO clienteDTO;
    Pedido pedido;
    Pedido pedido2;
    PedidoDTO pedidoDTO;
    PedidoDTO pedidoDTO2;
    DetalleEnvio detalleEnvio;
    DetalleEnvio detalleEnvio2;
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
        detalleEnvio = DetalleEnvio.builder()
                .id(UUID.randomUUID())
                .pedido(pedido)
                .direccion("Cra Test #Test-01")
                .transportadora("COORDINADORA")
                .numeroGuia(593324)
                .build();
        detalleEnvio2 = DetalleEnvio.builder()
                .id(UUID.randomUUID())
                .pedido(pedido2)
                .direccion("Cra Test #Test-02")
                .transportadora("SERVIENTREGA")
                .numeroGuia(986321)
                .build();
    }
    @Test
    void givenDetalleEnvio_whenGuardar_thenReturnsDetalleEnvioGuardado(){
        DetalleEnvioDTO detalleEnvioDTO = new DetalleEnvioDTO(
                detalleEnvio.getId(),
                pedidoDTO,
                detalleEnvio.getDireccion(),
                detalleEnvio.getTransportadora(),
                detalleEnvio.getNumeroGuia());
        given(pedidoRepository.findById(pedido.getId())).willReturn(Optional.of(pedido));
        given(detalleEnvioMapper.ToSaveDtoToEntity(any())).willReturn(detalleEnvio);
        given(detalleEnvioRepository.save(any())).willReturn(detalleEnvio);
        given(detalleEnvioMapper.DetalleEnvioToDto(any())).willReturn(detalleEnvioDTO);
        DetalleEnvioToSaveDTO detalleEnvioToSave = new DetalleEnvioToSaveDTO(
                pedidoDTO,
                detalleEnvio.getDireccion(),
                detalleEnvio.getTransportadora(),
                detalleEnvio.getNumeroGuia());
        DetalleEnvioDTO detalleEnvioSave = detalleEnvioService.guardarDetalleEnvio(detalleEnvioToSave);
        assertThat(detalleEnvioSave).isNotNull();
        assertThat(detalleEnvioSave.id()).isEqualTo(detalleEnvio.getId());
    }
    @Test
    void givenDetalleEnvioAndId_whenActualizar_thenReturnsDetalleEnvioActualizado(){
        DetalleEnvio detalleEnvioUpdated = DetalleEnvio.builder()
                .id(detalleEnvio.getId())
                .pedido(detalleEnvio.getPedido())
                .direccion(detalleEnvio.getDireccion())
                .transportadora(detalleEnvio.getTransportadora())
                .numeroGuia(89452)
                .build();
        DetalleEnvioDTO detalleEnvioUpdatedDTO = new DetalleEnvioDTO(
                detalleEnvioUpdated.getId(),
                pedidoDTO,
                detalleEnvioUpdated.getDireccion(),
                detalleEnvioUpdated.getTransportadora(),
                detalleEnvioUpdated.getNumeroGuia());
        given(detalleEnvioRepository.findById(detalleEnvio.getId())).willReturn(Optional.of(detalleEnvio));
        given(detalleEnvioRepository.save(any())).willReturn(detalleEnvioUpdated);
        given(detalleEnvioMapper.DetalleEnvioToDto(any())).willReturn(detalleEnvioUpdatedDTO);
        DetalleEnvioToSaveDTO detalleEnvioToUpdate = new DetalleEnvioToSaveDTO(
                new PedidoDTO(pedido.getId(),null,null,null),
                null,
                null,
                detalleEnvioUpdated.getNumeroGuia());
        DetalleEnvioDTO detalleEnvioActualizado = detalleEnvioService.actualizarDetalleEnvio(detalleEnvio.getId(),detalleEnvioToUpdate);
        assertThat(detalleEnvioActualizado).isNotNull();
        assertThat(detalleEnvioActualizado.id()).isEqualTo(detalleEnvio.getId());
        assertThat(detalleEnvioActualizado.numeroGuia()).isEqualTo(detalleEnvioUpdated.getNumeroGuia());
    }
    @Test
    void givenAnId_whenBuscarById_thenReturnsDetalleEnvio(){
        DetalleEnvioDTO detalleEnvioDTO = new DetalleEnvioDTO(
                detalleEnvio.getId(),
                pedidoDTO,
                detalleEnvio.getDireccion(),
                detalleEnvio.getTransportadora(),
                detalleEnvio.getNumeroGuia());
        given(detalleEnvioRepository.findById(detalleEnvio.getId())).willReturn(Optional.of(detalleEnvio));
        given(detalleEnvioMapper.DetalleEnvioToDto(any())).willReturn(detalleEnvioDTO);
        DetalleEnvioDTO detalleEnvioFinded = detalleEnvioService.buscarDetalleEnvioById(detalleEnvio.getId());
        assertThat(detalleEnvioFinded).isNotNull();
        assertThat(detalleEnvioFinded.id()).isEqualTo(detalleEnvio.getId());
    }
    @Test
    void givenPagos_WhenBuscarAllPagos_thenReturnsAllPagos(){
        DetalleEnvioDTO detalleEnvioDTO = new DetalleEnvioDTO(
                detalleEnvio.getId(),
                pedidoDTO,
                detalleEnvio.getDireccion(),
                detalleEnvio.getTransportadora(),
                detalleEnvio.getNumeroGuia());
        DetalleEnvioDTO detalleEnvioDTO2 = new DetalleEnvioDTO(
                detalleEnvio2.getId(),
                pedidoDTO2,
                detalleEnvio2.getDireccion(),
                detalleEnvio2.getTransportadora(),
                detalleEnvio2.getNumeroGuia());
        given(detalleEnvioRepository.findAll()).willReturn(List.of(detalleEnvio,detalleEnvio2));
        given(detalleEnvioMapper.DetalleEnvioToDto(any())).willReturn(detalleEnvioDTO,detalleEnvioDTO2);
        List<DetalleEnvioDTO> detalleEnvios = detalleEnvioService.obtenerTodosLosDetalleEnvio();
        assertThat(detalleEnvios).isNotEmpty();
        assertThat(detalleEnvios.get(0)).isEqualTo(detalleEnvioDTO);
        assertThat(detalleEnvios.get(1)).isEqualTo(detalleEnvioDTO2);
    }
    @Test
    void givenDetalleEnvio_whenEliminar_thenNothing(){
        given(detalleEnvioRepository.findById(detalleEnvio.getId())).willReturn(Optional.of(detalleEnvio));
        willDoNothing().given(detalleEnvioRepository).delete(any());
        detalleEnvioService.removerDetalleEnvio(detalleEnvio.getId());
        verify(detalleEnvioRepository,times(1)).delete(any());
    }
    @Test
    void givenDetalleEnvio_whenBuscarPorPedidoId_thenReturnsDetalleEnvio(){
        DetalleEnvioDTO detalleEnvioDTO = new DetalleEnvioDTO(
                detalleEnvio.getId(),
                pedidoDTO,
                detalleEnvio.getDireccion(),
                detalleEnvio.getTransportadora(),
                detalleEnvio.getNumeroGuia());
        given(detalleEnvioRepository.findByPedidoId(pedido.getId())).willReturn(detalleEnvio);
        given(detalleEnvioMapper.DetalleEnvioToDto(any())).willReturn(detalleEnvioDTO);
        DetalleEnvioDTO detalleEnvioFinded = detalleEnvioService.buscarDetalleEnvioByPedidoId(pedido.getId());
        assertThat(detalleEnvioFinded).isNotNull();
        assertThat(detalleEnvioFinded.id()).isEqualTo(detalleEnvio.getId());
    }
    @Test
    void givenDetallesEnvios_whenBuscarPorTransportadora_thenReturnsDetalleEnvio(){
        DetalleEnvioDTO detalleEnvioDTO = new DetalleEnvioDTO(
                detalleEnvio.getId(),
                pedidoDTO,
                detalleEnvio.getDireccion(),
                detalleEnvio.getTransportadora(),
                detalleEnvio.getNumeroGuia());
        DetalleEnvioDTO detalleEnvioDTO2 = new DetalleEnvioDTO(
                detalleEnvio2.getId(),
                pedidoDTO2,
                detalleEnvio2.getDireccion(),
                detalleEnvio2.getTransportadora(),
                detalleEnvio2.getNumeroGuia());
        given(detalleEnvioRepository.findByTransportadora(detalleEnvio.getTransportadora())).willReturn(List.of(detalleEnvio));
        given(detalleEnvioMapper.DetalleEnvioToDto(any())).willReturn(detalleEnvioDTO);
        List<DetalleEnvioDTO> detalleEnvioFinded = detalleEnvioService.buscarDetalleEnvioByTransportadora(detalleEnvio.getTransportadora());
        assertThat(detalleEnvioFinded).isNotEmpty();
        assertThat(detalleEnvioFinded).size().isEqualTo(1);
        assertThat(detalleEnvioFinded.get(0).id()).isEqualTo(detalleEnvio.getId());
    }
    @Test
    void givenDetallesEnvios_whenBuscarPorPedidoStatus_thenReturnsDetalleEnvios(){
        DetalleEnvioDTO detalleEnvioDTO = new DetalleEnvioDTO(
                detalleEnvio.getId(),
                pedidoDTO,
                detalleEnvio.getDireccion(),
                detalleEnvio.getTransportadora(),
                detalleEnvio.getNumeroGuia());
        DetalleEnvioDTO detalleEnvioDTO2 = new DetalleEnvioDTO(
                detalleEnvio2.getId(),
                pedidoDTO2,
                detalleEnvio2.getDireccion(),
                detalleEnvio2.getTransportadora(),
                detalleEnvio2.getNumeroGuia());
        given(detalleEnvioRepository.findByPedidoStatus(pedido.getStatus())).willReturn(List.of(detalleEnvio,detalleEnvio2));
        given(detalleEnvioMapper.DetalleEnvioToDto(any())).willReturn(detalleEnvioDTO,detalleEnvioDTO2);
        List<DetalleEnvioDTO> detalleEnvioFinded = detalleEnvioService.buscarDetalleEnvioByStatus(pedido.getStatus());
        assertThat(detalleEnvioFinded).isNotEmpty();
        assertThat(detalleEnvioFinded).size().isEqualTo(2);
        assertThat(detalleEnvioFinded.get(0).id()).isEqualTo(detalleEnvio.getId());
        assertThat(detalleEnvioFinded.get(1).id()).isEqualTo(detalleEnvio2.getId());
    }
}