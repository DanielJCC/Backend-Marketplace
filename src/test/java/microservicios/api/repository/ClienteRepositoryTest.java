package microservicios.api.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import microservicios.api.AbstractIntegrationDBTest;
import microservicios.api.entities.Cliente;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ClienteRepositoryTest extends AbstractIntegrationDBTest{
    private ClienteRepository clienteRepository;
    
    @Autowired
    public ClienteRepositoryTest(ClienteRepository clienteRepository){
        this.clienteRepository = clienteRepository;
    }

    // private void initMockClientes(){
    //     Cliente cliente = Cliente.builder()
    //             .nombre("Daniel Cogollos")
    //             .email("danijocogollo@gmail.com")
    //             .direccion("Cra 66b #53-06")
    //             .build();
    //     clienteRepository.save(cliente);
    // }

    @BeforeEach
    void setUp(){
        clienteRepository.deleteAll();
        // initMockClientes();
    }

    @Test
    @DisplayName("[CREATE] Dado un nuevo cliente, cuando se guarda, debe persistirse en la base de datos")
    void givenAnCliente_whenSave_thenClienteWithId(){
        Cliente cliente = Cliente.builder()
                .nombre("Daniel Cogollos")
                .email("danijocogollo@gmail.com")
                .direccion("Cra 66b #53-06")
                .build();
        Cliente clienteSaved = clienteRepository.save(cliente);
        assertThat(clienteSaved.getId()).isNotNull();
    }

    @Test
    @DisplayName("[READ] Dado dos clientes existentes, cuando se buscan todos los clientes, debe retornar una lista con los dos clientes")
    void givenTwoClientes_whenFindAll_thenListUsers(){
        Cliente cliente = clienteRepository.save(Cliente.builder()
            .nombre("Daniel Cogollos")
            .email("danijocogollo@gmail.com")
            .direccion("Cra 66b #53-06")
            .build());
        Cliente cliente2 = clienteRepository.save(Cliente.builder()
            .nombre("Juan Astori")
            .email("juan_Astori@gmail.com")
            .direccion("Cra 50 #20-32")
            .build());
        List<Cliente> clientesEncontrados = clienteRepository.findAll();
        assertThat(clientesEncontrados).isNotEmpty();
        assertThat(clientesEncontrados).hasSize(2);
        assertThat(clientesEncontrados.get(0).getId()).isEqualTo(cliente.getId());
        assertThat(clientesEncontrados.get(1).getId()).isEqualTo(cliente2.getId());
    }

    @Test
    @DisplayName("[UPDATE] Dado un usuario existente, cuando se actualiza la información, debe reflejarse en la base de datos")
    void givenAnCliente_whenUpdate_thenClienteIsUpdated(){
        Cliente cliente = clienteRepository.save(Cliente.builder()
            .nombre("Daniel Cogollos")
            .email("danijocogollo@gmail.com")
            .direccion("Cra 66b #53-06")
            .build());
        cliente.setNombre("Daniel José Cogollos Cerón");
        cliente.setEmail("danielcogollosjc@unimagdalena.edu.co");
        Cliente clienteUpdated = clienteRepository.save(cliente);
        assertThat(clienteRepository.findAll()).hasSize(1);
        assertThat(clienteUpdated.getNombre()).isEqualTo("Daniel José Cogollos Cerón");
        assertThat(clienteUpdated.getEmail()).isEqualTo("danielcogollosjc@unimagdalena.edu.co");
    }

    @Test
    @DisplayName("[DELETE] Dado un cliente existente, cuando se elimina, debe reflejarse en la base de datos")
    void givenAnCliente_whenDelete_thenClientIsDeleted(){
        Cliente cliente = clienteRepository.save(Cliente.builder()
            .nombre("Daniel Cogollos")
            .email("danijocogollo@gmail.com")
            .direccion("Cra 66b #53-06")
            .build());
        assertThat(clienteRepository.findAll()).hasSize(1);
        clienteRepository.delete(cliente);
        assertThat(clienteRepository.findAll()).hasSize(0);
    }

    @Test
    @DisplayName("Encontrar clientes por email")
    void givenAnCliente_whenFindByEmail_thenFindedCliente(){
        Cliente cliente = clienteRepository.save(Cliente.builder()
            .nombre("Daniel Cogollos")
            .email("danijocogollo@gmail.com")
            .direccion("Cra 66b #53-06")
            .build());

        assertThat(clienteRepository.findByEmail("danijocogollo@gmail.com").get(0)).isEqualTo(cliente);
    }

    @Test
    @DisplayName("Encontrar clientes por dirección")
    void givenAnCliente_whenFindByDireccion_thenFindedCliente(){
        Cliente cliente = clienteRepository.save(Cliente.builder()
            .nombre("Daniel Cogollos")
            .email("danijocogollo@gmail.com")
            .direccion("Cra 66b #53-06")
            .build());

        assertThat(clienteRepository.findByDireccion("Cra 66b #53-06").get(0)).isEqualTo(cliente);
    }

    @Test
    @DisplayName("Encontrar clientes por todos los clientes que comiencen por un numbre")
    void givenThreeClientes_whenFindByNombreStartingWith_thenFindedClientes(){
        Cliente cliente = clienteRepository.save(Cliente.builder()
            .nombre("Daniel Cogollos")
            .email("danijocogollo@gmail.com")
            .direccion("Cra 66b #53-06")
            .build());
        Cliente cliente2 = clienteRepository.save(Cliente.builder()
            .nombre("Juan Astori")
            .email("juan_Astori@gmail.com")
            .direccion("Cra 50 #20-32")
            .build());
        Cliente cliente3 = clienteRepository.save(Cliente.builder()
            .nombre("David Gutierrez")
            .email("davidGuti@yahoo.com")
            .direccion("Cra 4 #22-83")
            .build());

        List<Cliente> clientesFinded = clienteRepository.findByNombreStartingWith("Da");
        assertThat(clientesFinded).hasSize(2);
        assertThat(clientesFinded.get(0)).isEqualTo(cliente);
        assertThat(clientesFinded.get(1)).isEqualTo(cliente3);
    }
}
