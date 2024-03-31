package microservicios.api.dto.itemPedido;

import microservicios.api.entities.ItemPedido;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ItemPedidoMapper {
    ItemPedido DtoToEntity(ItemPedidoDTO itemPedidoDTO);
    ItemPedidoDTO EntityToDto(ItemPedido itemPedido);
    ItemPedido DtoToSaveToEntity(ItemPedidoToSaveDTO itemPedidoToSaveDTO);
}
