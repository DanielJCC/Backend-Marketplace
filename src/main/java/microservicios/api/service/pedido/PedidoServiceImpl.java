package microservicios.api.service.pedido;

import microservicios.api.dto.pedido.PedidoDTO;
import microservicios.api.dto.pedido.PedidoMapper;
import microservicios.api.dto.pedido.PedidoToSaveDTO;
import microservicios.api.entities.Cliente;
import microservicios.api.entities.Pedido;
import microservicios.api.repository.ClienteRepository;
import microservicios.api.repository.PedidoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class PedidoServiceImpl implements PedidoService{
    private final PedidoMapper pedidoMapper;
    private final PedidoRepository pedidoRepository;
    private final ClienteRepository clienteRepository;

    public PedidoServiceImpl(PedidoMapper pedidoMapper, PedidoRepository pedidoRepository, ClienteRepository clienteRepository){
        this.pedidoMapper = pedidoMapper;
        this.pedidoRepository = pedidoRepository;
        this.clienteRepository = clienteRepository;
    }
    @Override
    public PedidoDTO guardarPedido(PedidoToSaveDTO pedido) {
        Cliente cliente = clienteRepository.findById(pedido.cliente().id())
                .orElseThrow(()->new RuntimeException("Cliente no encontrado"));
        Pedido pedido1 = pedidoMapper.ToSaveDtoToPedido(pedido);
        pedido1.setCliente(cliente);
        Pedido pedidoSaved = pedidoRepository.save(pedido1);
        return pedidoMapper.PedidoToPedidoDto(pedidoSaved);
    }

    @Override
    public PedidoDTO actualizarPedido(UUID id, PedidoToSaveDTO pedidoToUpdate) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(()->new RuntimeException("Pedido not found"));
        if(pedidoToUpdate.fechaPedido() != null){pedido.setFechaPedido(pedidoToUpdate.fechaPedido());}
        if(pedidoToUpdate.status() != null){pedido.setStatus(pedidoToUpdate.status());}
        Pedido pedidoUpdated = pedidoRepository.save(pedido);
        return pedidoMapper.PedidoToPedidoDto(pedidoUpdated);
    }

    @Override
    public List<PedidoDTO> obtenerTodosLosPedidos() {
        List<Pedido> pedidos = pedidoRepository.findAll();
        return pedidos.stream().map(pedidoMapper::PedidoToPedidoDto).toList();
    }

    @Override
    public PedidoDTO buscarPedidoById(UUID id) {
        Pedido pedidoFinded = pedidoRepository.findById(id)
                .orElseThrow(()->new RuntimeException("Pedido not found"));
        return pedidoMapper.PedidoToPedidoDto(pedidoFinded);
    }

    @Override
    public void eliminarPedido(UUID id) {
        Pedido pedidoToEliminate = pedidoRepository.findById(id)
                .orElseThrow(()->new RuntimeException("Pedido not found"));
        pedidoRepository.delete(pedidoToEliminate);
    }

    @Override
    public List<PedidoDTO> buscarPedidosEntreFechas(LocalDateTime fechaComienzo, LocalDateTime fechaFinal) {
        List<Pedido> pedidos = pedidoRepository.findByFechaPedidoBetween(fechaComienzo, fechaFinal);
        return pedidos.stream().map(pedidoMapper::PedidoToPedidoDto).toList();
    }

    @Override
    public List<PedidoDTO> buscarPedidosPorClienteYEstado(UUID idCliente, String status) {
        List<Pedido> pedidos = pedidoRepository.findByClienteIdAndStatus(idCliente, status);
        return pedidos.stream().map(pedidoMapper::PedidoToPedidoDto).toList();
    }

    @Override
    public List<PedidoDTO> buscarPedidosPorArticulosPorCliente(UUID idCliente) {
        List<Pedido> pedidos = pedidoRepository.findArticulosPedidosByClient(idCliente);
        return pedidos.stream().map(pedidoMapper::PedidoToPedidoDto).toList();
    }
}
