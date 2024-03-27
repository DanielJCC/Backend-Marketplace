package microservicios.api.dto.cliente;

public record ClienteToSaveDTO(
    String nombre,
    String email,
    String direccion
) {

}
