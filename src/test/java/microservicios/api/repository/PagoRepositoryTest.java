package microservicios.api.repository;

import microservicios.api.AbstractIntegrationDBTest;
import microservicios.api.entities.Pago;
import microservicios.api.entities.Pedido;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class PagoRepositoryTest extends AbstractIntegrationDBTest {
    PagoRepository pagoRepository;
    PedidoRepository pedidoRepository;

    @Autowired
    public PagoRepositoryTest( PagoRepository pagoRepository, PedidoRepository pedidoRepository ){
        this.pagoRepository = pagoRepository;
        this.pedidoRepository = pedidoRepository;
    }

    Pago pagoCliente1;
    Pago pagoCliente2;
    Pedido pedidoGenerico1;
    Pedido pedidoGenerico2;
    @BeforeEach
    void setUp() {

        pedidoRepository.deleteAll();
        pagoRepository.deleteAll();

        pedidoGenerico1 = Pedido.builder().build();
        pedidoGenerico2 = Pedido.builder().build();
        pedidoRepository.save(pedidoGenerico1);
        pedidoRepository.save(pedidoGenerico2);

        pagoCliente1 = Pago.builder()
                .fechaPago(LocalDate.of(2002, 12, 7))
                .metodoPago("EFECTIVO")
                .totalPago((float) 1500)
                .pedido(pedidoGenerico1)
                .build();

        pagoCliente2 = Pago.builder()
                .fechaPago(LocalDate.of(2022, 3, 12))
                .metodoPago("NEQUI")
                .totalPago((float)67000)
                .pedido(pedidoGenerico2)
                .build();

        pagoRepository.save(pagoCliente1);
        pagoRepository.save(pagoCliente2);

    }

    @Test
    @DisplayName("[test_findByFechaPagoBetween] buscar los pagos que se encuentre dentro de un rango de fecha")
    void test_findByFechaPagoBetween() {

        LocalDate fechaInicial = LocalDate.of(2010, 10, 10);
        LocalDate fechaFinal = LocalDate.of(2024, 3, 27);

        List<Pago> pagosMatchConRangofecha = pagoRepository.findByFechaPagoBetween(fechaInicial, fechaFinal);

        assertThat(pagosMatchConRangofecha.size()).isEqualTo(1);
        assertThat(pagosMatchConRangofecha.get(0).getId()).isEqualTo(pagoCliente2.getId());

    }

    @Test
    @DisplayName("[test_findByPedidoIdAndMetodoPago] dado el id de un pedido y un metodo de pago debe retornar los pagos que hagan match")
    void test_findByPedidoIdAndMetodoPago() {

        String metodopago = "EFECTIVO";
        UUID id_pedido1 = pedidoGenerico1.getId();

        Pago pagosMatch = pagoRepository.findByPedidoIdAndMetodoPago(id_pedido1, metodopago);

        assertThat(pagosMatch).isNotNull();
        assertThat(pagosMatch.getPedido().getId()).isEqualTo(id_pedido1);
        assertThat(pagosMatch.getMetodoPago()).isEqualTo(metodopago);

    }

    @Test
    @DisplayName("[read] se debe poder obtener todos los pagos registrados")
    void test_read(){
        List<Pago> todosLosPagos = pagoRepository.findAll();
        assertThat(todosLosPagos.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("[update] se debe poder cambiar valores de un pago")
    void test_update(){

        UUID pago_id = pagoCliente1.getId();
        String metodoPagoEfectivo ="EFECTIVO";
        String metodoPagoPse = "PSE";

        Optional<Pago> pagoEncontrado = pagoRepository.findById(pago_id);

        assertThat(pagoEncontrado).isPresent();
        assertThat(pagoEncontrado.get().getId()).isEqualTo(pago_id);
        assertThat(pagoEncontrado.get().getMetodoPago()).isEqualTo(metodoPagoEfectivo);

        Pago pagoActualizado = pagoEncontrado.get();
        pagoActualizado.setMetodoPago(metodoPagoPse);

        pagoRepository.save(pagoActualizado);

        assertThat(pagoActualizado.getId()).isEqualTo(pago_id);
        assertThat(pagoActualizado.getMetodoPago()).isEqualTo(metodoPagoPse);

    }

    @Test
    @DisplayName("[delete] dado un pago existente cuando se elimine se debe de eliminar de la base de datos")
    void test_delete(){

        UUID pago_id = pagoCliente1.getId();
        Optional<Pago> pagoEncontrado = pagoRepository.findById(pago_id);

        assertThat(pagoEncontrado).isPresent();
        assertThat(pagoEncontrado.get().getId()).isEqualTo(pago_id);

        pagoRepository.deleteById(pagoEncontrado.get().getId());

        Optional<Pago> pagoEncontrado2 = pagoRepository.findById(pago_id);
        assertThat(pagoEncontrado2).isEmpty();

    }

    @Test
    @DisplayName("[save] dado un nuevo pago, este se debe poder guardar en la base de dato")
    void test_save(){

        Pago newPago = Pago.builder()
                .fechaPago(LocalDate.of(1993, 7, 20))
                .metodoPago("PAYPAL")
                .totalPago((float)23451)
                .build();

        Pago pagoGuardado = pagoRepository.save(newPago);
        assertThat(pagoGuardado.getId()).isNotNull();

    }
}