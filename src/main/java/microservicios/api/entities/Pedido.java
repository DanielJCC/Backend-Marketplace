package microservicios.api.entities;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "pedidos")
@Builder
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @ManyToOne
    @JoinColumn(name = "cliente_ID")
    private Cliente cliente;
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime fechaPedido;
    private String status;
    @OneToMany(mappedBy = "pedido")
    private List<ItemPedido> itemsPedidos;
    @OneToOne(mappedBy = "pedido")
    private Pago pago;
    @OneToOne(mappedBy = "pedido")
    private DetalleEnvio detalleEnvio;
}
