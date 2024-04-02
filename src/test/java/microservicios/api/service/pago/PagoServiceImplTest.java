package microservicios.api.service.pago;

import microservicios.api.dto.cliente.ClienteDTO;
import microservicios.api.dto.pago.PagoDTO;
import microservicios.api.dto.pago.PagoMapper;
import microservicios.api.dto.pago.PagoToSaveDTO;
import microservicios.api.dto.pedido.PedidoDTO;
import microservicios.api.entities.Cliente;
import microservicios.api.entities.Pago;
import microservicios.api.entities.Pedido;
import microservicios.api.repository.PagoRepository;
import microservicios.api.repository.PedidoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.BDDMockito.given;
import static org.mockito.ArgumentMatchers.any;
import static org.assertj.core.api.Assertions.assertThat;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class PagoServiceImplTest {
    @Mock
    private PagoMapper pagoMapper;
    @Mock
    private PagoRepository pagoRepository;
    @Mock
    private PedidoRepository pedidoRepository;
    @InjectMocks
    private PagoServiceImpl pagoService;
    Pago pago;
    Pago pago2;
    Pedido pedido;
    PedidoDTO pedidoDTO;
    Pedido pedido2;
    PedidoDTO pedidoDTO2;
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
        pago = Pago.builder()
                .id(UUID.randomUUID())
                .pedido(pedido)
                .totalPago((float) 1000)
                .fechaPago(LocalDate.now())
                .metodoPago("NEQUI")
                .build();
        pago2 = Pago.builder()
                .id(UUID.randomUUID())
                .pedido(pedido2)
                .totalPago((float) 1500)
                .fechaPago(LocalDate.now())
                .metodoPago("DAVIPLATA")
                .build();

    }
    @Test
    void givenPago_whenGuardarPago_thenReturnsPagoGuardado(){
        PagoDTO pagoDTO = new PagoDTO(
                pago.getId(),
                pedidoDTO,
                pago.getTotalPago(),
                pago.getFechaPago(),
                pago.getMetodoPago());
        given(pedidoRepository.findById(pedido.getId())).willReturn(Optional.of(pedido));
        given(pagoMapper.toSaveDtoToEntity(any())).willReturn(pago);
        given(pagoRepository.save(any())).willReturn(pago);
        given(pagoMapper.PagoToPagoDto(any())).willReturn(pagoDTO);
        PagoToSaveDTO pagoToSave = new PagoToSaveDTO(
                pedidoDTO,
                pago.getTotalPago(),
                pago.getFechaPago(),
                pago.getMetodoPago());
        PagoDTO pagoSaved = pagoService.guardarPago(pagoToSave);
        assertThat(pagoSaved).isNotNull();
        assertThat(pagoSaved.id()).isEqualTo(pago.getId());
    }
    @Test
    void givenPagoAndId_whenActualizarPago_thenReturnsPagoActualizado(){
        Pago pagoUpdated = Pago.builder()
                .id(pago.getId())
                .pedido(pago.getPedido())
                .totalPago((float) 1250)
                .fechaPago(pago.getFechaPago())
                .metodoPago(pago.getMetodoPago())
                .build();
        PagoDTO pagoUpdatedDTO = new PagoDTO(
                pagoUpdated.getId(),
                pedidoDTO,
                pagoUpdated.getTotalPago(),
                pagoUpdated.getFechaPago(),
                pagoUpdated.getMetodoPago());
        given(pagoRepository.findById(pago.getId())).willReturn(Optional.of(pago));
        given(pagoRepository.save(any())).willReturn(pagoUpdated);
        given(pagoMapper.PagoToPagoDto(any())).willReturn(pagoUpdatedDTO);
        PagoToSaveDTO pagoToUpdate = new PagoToSaveDTO(
                new PedidoDTO(pedido.getId(),null,null,null),
                pagoUpdated.getTotalPago(),
                null,
                null);
        PagoDTO pagoActualizado = pagoService.actualizarPago(pago.getId(),pagoToUpdate);
        assertThat(pagoActualizado).isNotNull();
        assertThat(pagoActualizado.id()).isEqualTo(pago.getId());
        assertThat(pagoActualizado.totalPago()).isEqualTo(pagoToUpdate.totalPago());
    }
    @Test
    void givenAnId_whenBuscarById_thenReturnsPago(){
        PagoDTO pagoDTO = new PagoDTO(
                pago.getId(),
                pedidoDTO,
                pago.getTotalPago(),
                pago.getFechaPago(),
                pago.getMetodoPago());
        given(pagoRepository.findById(pago.getId())).willReturn(Optional.of(pago));
        given(pagoMapper.PagoToPagoDto(any())).willReturn(pagoDTO);
        PagoDTO pagoFinded = pagoService.buscarPagoById(pago.getId());
        assertThat(pagoFinded).isNotNull();
        assertThat(pagoFinded.id()).isEqualTo(pago.getId());
    }
    @Test
    void givenPagos_WhenBuscarAllPagos_thenReturnsAllPagos(){
        PagoDTO pagoDTO = new PagoDTO(
                pago.getId(),
                pedidoDTO,
                pago.getTotalPago(),
                pago.getFechaPago(),
                pago.getMetodoPago());
        PagoDTO pagoDTO2 = new PagoDTO(
                pago2.getId(),
                pedidoDTO2,
                pago2.getTotalPago(),
                pago2.getFechaPago(),
                pago2.getMetodoPago());
        given(pagoRepository.findAll()).willReturn(List.of(pago,pago2));
        given(pagoMapper.PagoToPagoDto(any())).willReturn(pagoDTO,pagoDTO2);
        List<PagoDTO> pagos = pagoService.obtenerTodosLosPagos();
        assertThat(pagos).isNotEmpty();
        assertThat(pagos.get(0)).isEqualTo(pagoDTO);
        assertThat(pagos.get(1)).isEqualTo(pagoDTO2);
    }
    @Test
    void givenPago_whenEliminarPago_thenNothing(){
        given(pagoRepository.findById(pago.getId())).willReturn(Optional.of(pago));
        willDoNothing().given(pagoRepository).delete(any());
        pagoService.removerPago(pago.getId());
        verify(pagoRepository,times(1)).delete(any());
    }
    @Test
    void givenPagos_whenBuscarEntreFechas_thenReturnsPagos(){
        PagoDTO pagoDTO = new PagoDTO(
                pago.getId(),
                pedidoDTO,
                pago.getTotalPago(),
                pago.getFechaPago(),
                pago.getMetodoPago());
        PagoDTO pagoDTO2 = new PagoDTO(
                pago2.getId(),
                pedidoDTO2,
                pago2.getTotalPago(),
                pago2.getFechaPago(),
                pago2.getMetodoPago());
        LocalDate startDate = LocalDate.of(2024,3,30);
        LocalDate endDate = LocalDate.of(2024,4,5);
        given(pagoRepository.findByFechaPagoBetween(startDate,endDate)).willReturn(List.of(pago,pago2));
        given(pagoMapper.PagoToPagoDto(any())).willReturn(pagoDTO,pagoDTO2);
        List<PagoDTO> pagosFinded = pagoService.buscarPagosEntreFechas(startDate,endDate);
        assertThat(pagosFinded).isNotEmpty();
        assertThat(pagosFinded).size().isEqualTo(2);
        assertThat(pagosFinded.get(0).id()).isEqualTo(pago.getId());
        assertThat(pagosFinded.get(1).id()).isEqualTo(pago2.getId());
    }
    @Test
    void givenPagos_whenBuscarPorIdYMetodoPago_thenReturnsPago(){
        PagoDTO pagoDTO = new PagoDTO(
                pago.getId(),
                pedidoDTO,
                pago.getTotalPago(),
                pago.getFechaPago(),
                pago.getMetodoPago());
        given(pagoRepository.findByPedidoIdAndMetodoPago(pedido.getId(),pago.getMetodoPago())).willReturn(pago);
        given(pagoMapper.PagoToPagoDto(any())).willReturn(pagoDTO);
        PagoDTO pagoFinded = pagoService.buscarPagoPorIdPedidoYMetodoPago(pedido.getId(),pago.getMetodoPago());
        assertThat(pagoFinded).isNotNull();
        assertThat(pagoFinded.id()).isEqualTo(pago.getId());
    }
}