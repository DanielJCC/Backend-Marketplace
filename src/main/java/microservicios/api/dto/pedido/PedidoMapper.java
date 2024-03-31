package microservicios.api.dto.pedido;

import microservicios.api.entities.Pedido;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PedidoMapper {
    Pedido PedidoDtoToEntity(PedidoDTO pedidoDTO);
//    @Mapping(target = "")
    PedidoDTO PedidoToPedidoDto(Pedido pedido);
    Pedido ToSaveDtoToPedido(PedidoToSaveDTO pedidoToSaveDTO);
}
