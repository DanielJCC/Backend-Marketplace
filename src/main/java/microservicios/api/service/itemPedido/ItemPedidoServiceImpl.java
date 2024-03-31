package microservicios.api.service.itemPedido;

import microservicios.api.dto.itemPedido.ItemPedidoDTO;
import microservicios.api.dto.itemPedido.ItemPedidoMapper;
import microservicios.api.dto.itemPedido.ItemPedidoToSaveDTO;
import microservicios.api.entities.ItemPedido;
import microservicios.api.entities.Pedido;
import microservicios.api.entities.Product;
import microservicios.api.repository.ItemPedidoRepository;
import microservicios.api.repository.PedidoRepository;
import microservicios.api.repository.ProductoRepository;
import org.mapstruct.ap.shaded.freemarker.core.ReturnInstruction;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ItemPedidoServiceImpl implements ItemPedidoService{
    private final ItemPedidoMapper itemPedidoMapper;
    private final ItemPedidoRepository itemPedidoRepository;
    private final ProductoRepository productoRepository;
    private final PedidoRepository pedidoRepository;

    public ItemPedidoServiceImpl(ItemPedidoMapper itemPedidoMapper, ItemPedidoRepository itemPedidoRepository,
                                 ProductoRepository productoRepository, PedidoRepository pedidoRepository){
        this.itemPedidoMapper = itemPedidoMapper;
        this.itemPedidoRepository = itemPedidoRepository;
        this.productoRepository = productoRepository;
        this.pedidoRepository = pedidoRepository;
    }

    @Override
    public ItemPedidoDTO guardarItemPedido(ItemPedidoToSaveDTO itemPedidoToSave) {
        Pedido pedidoFinded = pedidoRepository.findById(itemPedidoToSave.pedido().id())
                .orElseThrow(()->new RuntimeException("Pedido not found"));
        Product productFinded = productoRepository.findById(itemPedidoToSave.producto().id())
                .orElseThrow(()->new RuntimeException("Product not found"));
        ItemPedido itemPedido = itemPedidoMapper.DtoToSaveToEntity(itemPedidoToSave);
        itemPedido.setPedido(pedidoFinded);
        itemPedido.setProducto(productFinded);
        ItemPedido itemPedidoSaved = itemPedidoRepository.save(itemPedido);
        return itemPedidoMapper.EntityToDto(itemPedidoSaved);
    }

    @Override
    public ItemPedidoDTO actualizarItemPedido(UUID id, ItemPedidoToSaveDTO itemPedidoToUpdate) {
        ItemPedido itemPedidoFinded = itemPedidoRepository.findById(id)
                .orElseThrow(()->new RuntimeException("Item pedido not found"));
        if(itemPedidoToUpdate.cantidad() != null){itemPedidoFinded.setCantidad(itemPedidoToUpdate.cantidad());}
        if(itemPedidoToUpdate.precioUnitario() != null){ itemPedidoFinded.setPrecioUnitario(itemPedidoToUpdate.precioUnitario()); }
        ItemPedido itemPedidoUpdated = itemPedidoRepository.save(itemPedidoFinded);
        return itemPedidoMapper.EntityToDto(itemPedidoUpdated);
    }

    @Override
    public ItemPedidoDTO buscarItemPedidoById(UUID id) {
        ItemPedido itemPedidoFinded = itemPedidoRepository.findById(id)
                .orElseThrow(()->new RuntimeException("Item pedido not found"));
        return itemPedidoMapper.EntityToDto(itemPedidoFinded);
    }

    @Override
    public List<ItemPedidoDTO> obtenerTodosLosItemPedidos() {
        List<ItemPedido> itemsPedidos = itemPedidoRepository.findAll();
        return itemsPedidos.stream().map(itemPedidoMapper::EntityToDto).toList();
    }

    @Override
    public void removerItemPedido(UUID id) {
        ItemPedido itemPedidoFinded = itemPedidoRepository.findById(id)
                .orElseThrow(()->new RuntimeException("Item pedido not found"));
        itemPedidoRepository.delete(itemPedidoFinded);
    }

    @Override
    public List<ItemPedidoDTO> buscarItemPedidosByPedidoId(UUID idPedido) {
        List<ItemPedido> itemsPedidosFinded = itemPedidoRepository.findByPedidoId(idPedido);
        return itemsPedidosFinded.stream().map(itemPedidoMapper::EntityToDto).toList();
    }

    @Override
    public List<ItemPedidoDTO> buscarItemPedidosByProductoId(UUID idProducto) {
        List<ItemPedido> itemsPedidosFinded = itemPedidoRepository.findByPedidoId(idProducto);
        return itemsPedidosFinded.stream().map(itemPedidoMapper::EntityToDto).toList();
    }

    @Override
    public ItemPedidoDTO buscarItemPedidoAndTotalVentasByProductoId(UUID idProducto) {
        ItemPedido itemPedidoFinded = itemPedidoRepository.findTotalVentasByProducto(idProducto);
        return itemPedidoMapper.EntityToDto(itemPedidoFinded);
    }
}
