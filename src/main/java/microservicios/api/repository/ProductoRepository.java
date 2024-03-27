package microservicios.api.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import microservicios.api.entities.Product;

public interface ProductoRepository extends JpaRepository<Product,UUID>{
    List<Product> findByNombre(String nombre);
    List<Product> findByPrecio(Float precio);
    @Query("select p from Product p where p.stock > 0")
    List<Product> findByInStock();
    List<Product> findByPrecioLessThanAndStockLessThan(Float precio, Integer stock);
}
