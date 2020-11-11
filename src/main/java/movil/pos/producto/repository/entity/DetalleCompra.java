package movil.pos.producto.repository.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "detalle_compra", schema = "movilpos")
public class DetalleCompra implements Serializable{

    private static final long serialVersionUID = -2699946521373993394L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "detalleCompraSequenceGenerator")
    @SequenceGenerator(name = "detalleCompraSequenceGenerator", sequenceName = "detalle_compra_id_seq", allocationSize = 1,schema = "movilpos")
    private Long id;

    @Column(name = "compra_id")
    private Long compraId;

    @Column(name = "producto_id")
    private Long productoId;

    BigDecimal precio;
    BigDecimal cantidad;
    BigDecimal subtotal;
    BigDecimal impuesto;
    BigDecimal descuento;
    BigDecimal total;

    
}