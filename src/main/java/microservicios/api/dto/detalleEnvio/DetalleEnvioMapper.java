package microservicios.api.dto.detalleEnvio;

import microservicios.api.entities.DetalleEnvio;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DetalleEnvioMapper {
    DetalleEnvio DetalleEnvioDtoToEntity(DetalleEnvioDTO detalleEnvioDTO);
    DetalleEnvioDTO DetalleEnvioToDto(DetalleEnvio detalleEnvio);
    DetalleEnvio ToSaveDtoToEntity(DetalleEnvioToSaveDTO detalleEnvioToSaveDTO);
}
