package microservicios.api.service.detalleEnvio;

import microservicios.api.dto.detalleEnvio.DetalleEnvioDTO;
import microservicios.api.dto.detalleEnvio.DetalleEnvioMapper;
import microservicios.api.dto.detalleEnvio.DetalleEnvioToSaveDTO;
import microservicios.api.dto.pedido.PedidoMapper;
import microservicios.api.entities.DetalleEnvio;
import microservicios.api.entities.Pedido;
import microservicios.api.repository.DetalleEnvioRepository;
import microservicios.api.repository.PedidoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class DetalleEnvioServiceImpl implements DetalleEnvioService{
    private final DetalleEnvioMapper detalleEnvioMapper;
    private final DetalleEnvioRepository detalleEnvioRepository;
    private final PedidoRepository pedidoRepository;

    public DetalleEnvioServiceImpl(DetalleEnvioMapper detalleEnvioMapper,DetalleEnvioRepository detalleEnvioRepository,
                                   PedidoRepository pedidoRepository){
        this.detalleEnvioMapper = detalleEnvioMapper;
        this.detalleEnvioRepository = detalleEnvioRepository;
        this.pedidoRepository = pedidoRepository;
    }

    @Override
    public DetalleEnvioDTO guardarDetalleEnvio(DetalleEnvioToSaveDTO detalleEnvioToSaveDTO) {
        Pedido pedido = pedidoRepository.findById(detalleEnvioToSaveDTO.pedido().id())
                .orElseThrow(()->new RuntimeException("Pedido not found"));
        DetalleEnvio detalleEnvio = detalleEnvioMapper.ToSaveDtoToEntity(detalleEnvioToSaveDTO);
        detalleEnvio.setPedido(pedido);
        DetalleEnvio detalleEnvioSaved = detalleEnvioRepository.save(detalleEnvio);
        return detalleEnvioMapper.DetalleEnvioToDto(detalleEnvioSaved);
    }

    @Override
    public DetalleEnvioDTO actualizarDetalleEnvio(UUID id, DetalleEnvioToSaveDTO detalleEnvioToUpdate) {
         DetalleEnvio detalleEnvioFinded = detalleEnvioRepository.findById(id)
                 .orElseThrow(()->new RuntimeException("Detalle de envío not found"));
         if(detalleEnvioToUpdate.direccion() != null){ detalleEnvioFinded.setDireccion(detalleEnvioToUpdate.direccion()); }
         if(detalleEnvioToUpdate.transportadora() != null){ detalleEnvioFinded.setTransportadora(detalleEnvioToUpdate.transportadora());}
         if(detalleEnvioToUpdate.numeroGuia()!=null){ detalleEnvioFinded.setNumeroGuia(detalleEnvioToUpdate.numeroGuia());}
         DetalleEnvio detalleEnvioUpdated = detalleEnvioRepository.save(detalleEnvioFinded);
         return detalleEnvioMapper.DetalleEnvioToDto(detalleEnvioUpdated);
    }

    @Override
    public List<DetalleEnvioDTO> obtenerTodosLosDetalleEnvio() {
        List<DetalleEnvio> detalleEnvios = detalleEnvioRepository.findAll();
        return detalleEnvios.stream().map(detalleEnvioMapper::DetalleEnvioToDto).toList();
    }

    @Override
    public DetalleEnvioDTO buscarDetalleEnvioById(UUID id) {
        DetalleEnvio detalleEnvioFinded = detalleEnvioRepository.findById(id)
                .orElseThrow(()->new RuntimeException("Detalle de envío not found"));
        return detalleEnvioMapper.DetalleEnvioToDto(detalleEnvioFinded);
    }

    @Override
    public void removerDetalleEnvio(UUID id) {
        DetalleEnvio detalleEnvioToRemove = detalleEnvioRepository.findById(id)
                .orElseThrow(()->new RuntimeException("Detalle de envío not found"));
        detalleEnvioRepository.delete(detalleEnvioToRemove);
    }

    @Override
    public DetalleEnvioDTO buscarDetalleEnvioByPedidoId(UUID idPedido) {
        DetalleEnvio detalleEnvioFinded = detalleEnvioRepository.findByPedidoId(idPedido);
        return detalleEnvioMapper.DetalleEnvioToDto(detalleEnvioFinded);
    }

    @Override
    public List<DetalleEnvioDTO> buscarDetalleEnvioByTransportadora(String transportadora) {
        List<DetalleEnvio> detalleEnviosFinded = detalleEnvioRepository.findByTransportadora(transportadora);
        return detalleEnviosFinded.stream().map(detalleEnvioMapper::DetalleEnvioToDto).toList();
    }

    @Override
    public List<DetalleEnvioDTO> buscarDetalleEnvioByStatus(String status) {
        List<DetalleEnvio> detalleEnviosFinded = detalleEnvioRepository.findByPedidoStatus(status);
        return detalleEnviosFinded.stream().map(detalleEnvioMapper::DetalleEnvioToDto).toList();
    }
}
