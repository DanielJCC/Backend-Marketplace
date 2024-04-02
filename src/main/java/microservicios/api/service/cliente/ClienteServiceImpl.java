package microservicios.api.service.cliente;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import microservicios.api.dto.exception.ClienteNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import microservicios.api.dto.cliente.ClienteDTO;
import microservicios.api.dto.cliente.ClienteMapper;
import microservicios.api.dto.cliente.ClienteToSaveDTO;
import microservicios.api.entities.Cliente;
import microservicios.api.repository.ClienteRepository;

@Service
public class ClienteServiceImpl implements ClienteService{
    private final ClienteMapper clienteMapper;
    private final ClienteRepository clienteRepository;

    @Autowired
    public ClienteServiceImpl(ClienteMapper clienteMapper, ClienteRepository clienteRepository){
        this.clienteMapper = clienteMapper;
        this.clienteRepository = clienteRepository;
    }

    @Override
    public ClienteDTO guardarCliente(ClienteToSaveDTO cliente) {
        Method[] metodos = clienteMapper.getClass().getMethods();
        Cliente client1 = clienteMapper.ToSaveDtoToCliente(cliente);
        System.out.println("Cliente: "+client1.toString());
        Cliente clienteSaved = clienteRepository.save(client1);
        System.out.println(clienteSaved);
        return clienteMapper.ClienteToClienteDto(clienteSaved);
    }

    @Override
    public ClienteDTO actualizarCliente(UUID id, ClienteToSaveDTO cliente) {
        Cliente clienteInDB = clienteRepository.findById(id)
                .orElseThrow(()->new ClienteNotFoundException("Cliente no encontrado"));
        if(cliente.nombre() != null){ clienteInDB.setNombre(cliente.nombre()); }
        if(cliente.email() != null){ clienteInDB.setEmail(cliente.email()); }
        if(cliente.direccion() != null){ clienteInDB.setDireccion(cliente.direccion()); }

        Cliente clienteGuardado = clienteRepository.save(clienteInDB);

        return clienteMapper.ClienteToClienteDto(clienteGuardado);
        // throw new UnsupportedOperationException("Unimplemented method 'actualizarCliente'");
    }

    @Override
    public List<ClienteDTO> obtenerTodosLosClientes() {
        List<Cliente> clientes = clienteRepository.findAll();

        return clientes.stream().map(clienteMapper::ClienteToClienteDto).toList();

        // throw new UnsupportedOperationException("Unimplemented method 'obtenerTodosLosClientes'");
    }

    @Override
    public ClienteDTO buscarClienteById(UUID id) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(()->new ClienteNotFoundException("Cliente no encontrado"));
        return clienteMapper.ClienteToClienteDto(cliente);
    }

    @Override
    public void removerCliente(UUID id) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(()->new ClienteNotFoundException("Cliente no encontrado"));
        clienteRepository.delete(cliente);
        //throw new UnsupportedOperationException("Unimplemented method 'removerCliente'");
    }

    @Override
    public ClienteDTO buscarClienteByEmail(String email) {
        Cliente cliente = clienteRepository.findByEmail(email);
        return clienteMapper.ClienteToClienteDto(cliente);
    }

    @Override
    public ClienteDTO buscarClienteByDireccion(String direccion) {
        Cliente cliente = clienteRepository.findByDireccion(direccion);
        return clienteMapper.ClienteToClienteDto(cliente);
    }

    @Override
    public List<ClienteDTO> buscarClientesByPrefijoDeNombre(String prefijo) {
        List<Cliente> clientes = clienteRepository.findByNombreStartingWith(prefijo);
        return clientes.stream().map(clienteMapper::ClienteToClienteDto).toList();
    }
}
