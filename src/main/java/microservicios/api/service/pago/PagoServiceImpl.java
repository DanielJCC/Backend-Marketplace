package microservicios.api.service.pago;

import microservicios.api.dto.pago.PagoDTO;
import microservicios.api.dto.pago.PagoMapper;
import microservicios.api.dto.pago.PagoToSaveDTO;
import microservicios.api.entities.Pago;
import microservicios.api.entities.Pedido;
import microservicios.api.repository.PagoRepository;
import microservicios.api.repository.PedidoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class PagoServiceImpl implements PagoService{
    private final PagoMapper pagoMapper;
    private final PagoRepository pagoRepository;
    private final PedidoRepository pedidoRepository;

    public PagoServiceImpl(PagoMapper pagoMapper, PagoRepository pagoRepository, PedidoRepository pedidoRepository){
        this.pagoMapper = pagoMapper;
        this.pagoRepository = pagoRepository;
        this.pedidoRepository = pedidoRepository;
    }
    @Override
    public PagoDTO guardarPago(PagoToSaveDTO pagoToSaveDTO) {
        Pedido pedidoFinded = pedidoRepository.findById(pagoToSaveDTO.pedido().id())
                .orElseThrow(()->new RuntimeException("Pedido not found"));
        Pago pago = pagoMapper.toSaveDtoToEntity(pagoToSaveDTO);
        pago.setPedido(pedidoFinded);
        Pago pagoSaved = pagoRepository.save(pago);
        return pagoMapper.PagoToPagoDto(pagoSaved);
    }

    @Override
    public PagoDTO actualizarPago(UUID id, PagoToSaveDTO pagoToUpdate) {
        Pago pagoInDB = pagoRepository.findById(id)
                .orElseThrow(()->new RuntimeException("Pago not found"));
        if(pagoToUpdate.totalPago()!=null){ pagoInDB.setTotalPago(pagoToUpdate.totalPago());}
        if(pagoToUpdate.fechaPago()!=null){ pagoInDB.setFechaPago(pagoToUpdate.fechaPago());}
        if(pagoToUpdate.metodoPago()!=null){ pagoInDB.setMetodoPago(pagoToUpdate.metodoPago());}
        Pago pagoUpdated = pagoRepository.save(pagoInDB);
        return pagoMapper.PagoToPagoDto(pagoUpdated);
    }

    @Override
    public List<PagoDTO> obtenerTodosLosPagos() {
        List<Pago> pagos = pagoRepository.findAll();
        return pagos.stream().map(pagoMapper::PagoToPagoDto).toList();
    }

    @Override
    public PagoDTO buscarPagoById(UUID id) {
        Pago pagoFinded = pagoRepository.findById(id)
                .orElseThrow(()->new RuntimeException("Pago not found"));
        return pagoMapper.PagoToPagoDto(pagoFinded);
    }

    @Override
    public void removerPago(UUID id) {
        Pago pagoToRemove = pagoRepository.findById(id)
                .orElseThrow(()->new RuntimeException("Pago not found"));
        pagoRepository.delete(pagoToRemove);
    }

    @Override
    public List<PagoDTO> buscarPagosEntreFechas(LocalDate startDate, LocalDate endDate) {
        List<Pago> pagos = pagoRepository.findByFechaPagoBetween(startDate, endDate);
        return pagos.stream().map(pagoMapper::PagoToPagoDto).toList();
    }

    @Override
    public PagoDTO buscarPagoPorIdPedidoYMetodoPago(UUID idPedido, String metodoPago) {
        Pago pago = pagoRepository.findByPedidoIdAndMetodoPago(idPedido, metodoPago);
        return pagoMapper.PagoToPagoDto(pago);
    }
}
