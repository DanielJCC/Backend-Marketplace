package microservicios.api.dto.product;

import microservicios.api.dto.cliente.ClienteDTO;
import microservicios.api.dto.cliente.ClienteToSaveDTO;
import microservicios.api.entities.Cliente;
import microservicios.api.entities.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    Product ProductDtoToEntity(ProductDTO dto);
    ProductDTO ProductToProductDto(Product producto);
    Product ToSaveDtoToProduct(ProductToSaveDTO toSaveDto);
}
