package microservicios.api.dto.product;

public record ProductToSaveDTO(
    String nombre,
    Float price,
    Integer stock
) {

}
