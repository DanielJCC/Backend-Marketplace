package microservicios.api.dto.cliente;

import microservicios.api.dto.product.ProductMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import microservicios.api.entities.Cliente;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface ClienteMapper {
//    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);
    Cliente ClienteDtoToEntity(ClienteDTO dto);
    ClienteDTO ClienteToClienteDto(Cliente cliente);
    @Mapping(target = "id",ignore = true)
    Cliente ToSaveDtoToCliente(ClienteToSaveDTO toSaveDto);
}
