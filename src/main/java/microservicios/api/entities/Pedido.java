package microservicios.api.entities;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.*;
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
    @OneToMany(mappedBy = "pedido",cascade = CascadeType.ALL)
    private List<ItemPedido> itemsPedidos;
    @OneToOne(mappedBy = "pedido",cascade = CascadeType.ALL)
    private Pago pago;
    @OneToOne(mappedBy = "pedido",cascade = CascadeType.ALL)
    private DetalleEnvio detalleEnvio;
}
