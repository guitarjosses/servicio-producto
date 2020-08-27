package movil.pos.producto.repository.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@Entity(name="ListaPrecioDetalle")
@Table(name = "lista_precio_detalle", schema = "movilpos")
public class ListaPrecioDetalle {


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "listaPrecioDetalleSequenceGenerator")
    @SequenceGenerator(name = "listaPrecioDetalleSequenceGenerator", sequenceName = "lista_precio_detalle_id_seq", allocationSize = 1,schema = "movilpos")
    private Long id;

    @Column(name = "id_producto")
    private Long idProducto;

    private BigDecimal porcentaje;

    @Column(name = "precio_venta")
    private BigDecimal precioVenta;

    private boolean activa;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "lista_precio_id", nullable = false)
    @JsonIgnore
    private ListaPrecio listaPrecio;

    /*public ListaPrecioDetalle(BigDecimal porcentaje, BigDecimal precioVenta, boolean activa){
        this.porcentaje = porcentaje;
        this.precioVenta = precioVenta;
        this.activa = activa;
    }*/
    
}