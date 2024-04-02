package microservicios.api.service.cliente;

import microservicios.api.dto.cliente.ClienteDTO;
import microservicios.api.dto.cliente.ClienteMapper;
import microservicios.api.dto.cliente.ClienteMapperImpl;
import microservicios.api.dto.cliente.ClienteToSaveDTO;
import microservicios.api.entities.Cliente;
import microservicios.api.repository.ClienteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.BDDMockito.given;
import static org.mockito.ArgumentMatchers.any;
import static org.assertj.core.api.Assertions.assertThat;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClienteServiceImplTest {
    @Mock
    private ClienteMapper clienteMapper;
    @Mock
    private ClienteRepository clienteRepository;
    @InjectMocks
    private ClienteServiceImpl clienteService;
    Cliente cliente;
    Cliente cliente2;
    @BeforeEach
    void setUp(){
        cliente = Cliente.builder()
                .id(UUID.randomUUID())
                .nombre("Test")
                .email("test@test.com")
                .direccion("Cra Test #Test-01")
                .build();
        cliente2 = Cliente.builder()
                .id(UUID.randomUUID())
                .nombre("Test2")
                .email("test2@test.com")
                .direccion("Cra Test2 #Test-02")
                .build();
    }

    @Test
    void givenCliente_whenGuardarCliente_thenReturnClienteGuardado(){
        ClienteDTO clienteDTO = new ClienteDTO(
                cliente.getId(),
                "Test",
                "test@test.com",
                "Cra Test #Test-01"
        );
        given(clienteMapper.ToSaveDtoToCliente(any())).willReturn(cliente);
        given(clienteMapper.ClienteToClienteDto(any())).willReturn(clienteDTO);
        given(clienteRepository.save(any())).willReturn(cliente);
        ClienteToSaveDTO clienteToSave = new ClienteToSaveDTO(
                "Test",
                "test@test.com",
                "Cra Test #Test-01");
        ClienteDTO clienteSaved = clienteService.guardarCliente(clienteToSave);
        assertThat(clienteSaved).isNotNull();
        assertThat(clienteSaved.id()).isEqualTo(clienteDTO.id());
    }

    @Test
    void givenClienteAndId_whenActualizarCliente_thenReturnsClienteActualizado(){
        ClienteDTO clienteUpdatedDto = new ClienteDTO(
                cliente.getId(),
                "Test actualizado",
                cliente.getEmail(),
                cliente.getDireccion()
        );
        Cliente clienteUpdated = Cliente.builder()
                .id(cliente.getId())
                .nombre(clienteUpdatedDto.nombre())
                .email(clienteUpdatedDto.email())
                .direccion(clienteUpdatedDto.direccion())
                .build();
        given(clienteRepository.findById(cliente.getId())).willReturn(Optional.of(cliente));
        given(clienteRepository.save(any())).willReturn(clienteUpdated);
        given(clienteMapper.ClienteToClienteDto(any())).willReturn(clienteUpdatedDto);
        ClienteToSaveDTO clienteToUpdate = new ClienteToSaveDTO(
                "Test actualizado",
                null,
                null);
        ClienteDTO clienteActualizado = clienteService.actualizarCliente(cliente.getId(),clienteToUpdate);
        assertThat(clienteActualizado).isNotNull();
        assertThat(clienteActualizado.id()).isEqualTo(cliente.getId());
        assertThat(clienteActualizado.nombre()).isEqualTo(clienteToUpdate.nombre());
    }
    @Test
    void givenAnId_whenBuscarById_thenReturnsCliente(){
        ClienteDTO clienteFinded = new ClienteDTO(
                cliente.getId(),
                cliente.getNombre(),
                cliente.getEmail(),
                cliente.getDireccion()
        );
        given(clienteRepository.findById(cliente.getId())).willReturn(Optional.of(cliente));
        given(clienteMapper.ClienteToClienteDto(any())).willReturn(clienteFinded);
        ClienteDTO clienteEncontrado = clienteService.buscarClienteById(cliente.getId());
        assertThat(clienteEncontrado).isNotNull();
        assertThat(clienteEncontrado.id()).isEqualTo(cliente.getId());
    }
    @Test
    void givenClientes_WhenBuscarAllClientes_thenReturnsAllClientes(){
        ClienteDTO clienteDTO = new ClienteDTO(
                cliente.getId(),
                cliente.getNombre(),
                cliente.getEmail(),
                cliente.getDireccion()
        );
        ClienteDTO clienteDTO2 = new ClienteDTO(
                cliente2.getId(),
                cliente2.getNombre(),
                cliente2.getEmail(),
                cliente2.getDireccion()
        );
        given(clienteRepository.findAll()).willReturn(List.of(cliente,cliente2));
        given(clienteMapper.ClienteToClienteDto(any())).willReturn(clienteDTO,clienteDTO2);
        List<ClienteDTO> clientes = clienteService.obtenerTodosLosClientes();
        assertThat(clientes).isNotEmpty();
        assertThat(clientes.get(0)).isEqualTo(clienteDTO);
        assertThat(clientes.get(1)).isEqualTo(clienteDTO2);
    }
    @Test
    void givenCliente_whenEliminarCliente_thenNothing(){
        given(clienteRepository.findById(cliente.getId())).willReturn(Optional.of(cliente));
        willDoNothing().given(clienteRepository).delete(any());
        clienteService.removerCliente(cliente.getId());
        verify(clienteRepository, times(1)).delete(any());
    }
    @Test
    void givenCliente_whenBuscarPorEmail_thenReturnsCliente(){
        ClienteDTO clienteFinded = new ClienteDTO(
                cliente.getId(),
                cliente.getNombre(),
                cliente.getEmail(),
                cliente.getDireccion()
        );
        given(clienteRepository.findByEmail(cliente.getEmail())).willReturn(cliente);
        given(clienteMapper.ClienteToClienteDto(any())).willReturn(clienteFinded);
        ClienteDTO clienteEncontrado = clienteService.buscarClienteByEmail(cliente.getEmail());
        assertThat(clienteEncontrado).isNotNull();
        assertThat(clienteEncontrado.id()).isEqualTo(cliente.getId());
    }
    @Test
    void givenCliente_whenBuscarPorDireccion_thenReturnsCliente(){
        ClienteDTO clienteFinded = new ClienteDTO(
                cliente.getId(),
                cliente.getNombre(),
                cliente.getEmail(),
                cliente.getDireccion()
        );
        given(clienteRepository.findByDireccion(cliente.getDireccion())).willReturn(cliente);
        given(clienteMapper.ClienteToClienteDto(any())).willReturn(clienteFinded);
        ClienteDTO clienteEncontrado = clienteService.buscarClienteByDireccion(cliente.getDireccion());
        assertThat(clienteEncontrado).isNotNull();
        assertThat(clienteEncontrado.id()).isEqualTo(cliente.getId());
    }
    @Test
    void givenCliente_whenBuscarPorPrefijoNombre_thenReturnsCliente(){
        String prefixName = "Te";
        ClienteDTO clienteFinded = new ClienteDTO(
                cliente.getId(),
                cliente.getNombre(),
                cliente.getEmail(),
                cliente.getDireccion()
        );
        ClienteDTO clienteFinded2 = new ClienteDTO(
                cliente2.getId(),
                cliente2.getNombre(),
                cliente2.getEmail(),
                cliente2.getDireccion()
        );
        given(clienteRepository.findByNombreStartingWith(prefixName)).willReturn(List.of(cliente,cliente2));
        given(clienteMapper.ClienteToClienteDto(any())).willReturn(clienteFinded,clienteFinded2);
        List<ClienteDTO> clientesEncontrados = clienteService.buscarClientesByPrefijoDeNombre(prefixName);
        assertThat(clientesEncontrados).isNotEmpty();
        assertThat(clientesEncontrados.get(0).id()).isEqualTo(cliente.getId());
        assertThat(clientesEncontrados.get(1).id()).isEqualTo(cliente2.getId());
    }
}