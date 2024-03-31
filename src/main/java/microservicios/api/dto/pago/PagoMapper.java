package microservicios.api.dto.pago;

import microservicios.api.entities.Pago;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PagoMapper {
    Pago PagoDtoToEntity(PagoDTO pagoDTO);
    PagoDTO PagoToPagoDto(Pago pago);
    Pago toSaveDtoToEntity(PagoToSaveDTO pagoToSaveDTO);
}
