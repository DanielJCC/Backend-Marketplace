package microservicios.api.service;

import java.util.UUID;

import microservicios.api.service.cliente.ClienteServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.BDDMockito.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import microservicios.api.dto.cliente.ClienteDTO;
import microservicios.api.dto.cliente.ClienteToSaveDTO;
import microservicios.api.entities.Cliente;
import microservicios.api.repository.ClienteRepository;

@ExtendWith(MockitoExtension.class)
public class ClienteServiceImplTest {

    @Mock
    private ClienteRepository clienteRepository;
//    @Mock
//    private ClienteMapper clienteMapper;
    @InjectMocks
    private ClienteServiceImpl clienteService;
    Cliente cliente;
    @BeforeEach
    void setUp(){
//        clienteMapper = Mappers.getMapper(ClienteMapper.class);
        cliente = Cliente.builder()
                .id(UUID.randomUUID())
                .nombre("Test")
                .email("test@test.com")
                .direccion("Cra Test #Test-01")
                .build();
    }

    @Test
    void givenCliente_whenGuardarCliente_thenReturnClienteGuardado(){
        cliente = Cliente.builder()
                .id(UUID.randomUUID())
                .nombre("Test")
                .email("test@test.com")
                .direccion("Cra Test #Test-01")
                .build();
        given(clienteRepository.save(any())).willReturn(cliente);

        ClienteToSaveDTO clienteAGuardar = new ClienteToSaveDTO(
                "test",
                "test@test.com",
                "Cra Test #Test-01");
        //WHEN
        ClienteDTO clienteDTO = clienteService.guardarCliente(clienteAGuardar);
        //THEN
        assertThat(clienteDTO).isNotNull();
        assertThat(clienteDTO.nombre()).isEqualTo("test");
    }

    @Test
    public void givenCliente_whenSavedCliente_thenReturnClienteObject(){
//        given(clienteRepository.save(cliente))
//                .willReturn(cliente);
        ClienteToSaveDTO clienteAGuardar = new ClienteToSaveDTO(
                cliente.getNombre(),
                cliente.getEmail(),
                cliente.getDireccion());
        ClienteDTO clienteSaved = clienteService.guardarCliente(clienteAGuardar);
        assertThat(clienteSaved).isNotNull();
    }

//    @Test
//    public void givenCliente(){
//        ClienteDTO clienteSaved =
//    }
}
