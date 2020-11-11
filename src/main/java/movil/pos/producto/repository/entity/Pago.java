package movil.pos.producto.repository.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "pago", schema = "movilpos")
public class Pago implements Serializable {

    private static final long serialVersionUID = -8981377173465696512L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "pagoSequenceGenerator")
    @SequenceGenerator(name = "pagoSequenceGenerator", sequenceName = "pago_id_seq", allocationSize = 1,schema = "movilpos")
    private Long id;

    @Column(name = "numero_pago")
    private String numeroPago;

    LocalDateTime fecha;

    @ManyToOne
    @JoinColumn(name = "proveedor_id")
    private Proveedor proveedor;

    @ManyToOne
    @JoinColumn(name = "compra_id")
    private Compra compra;

    @Column(name = "metodo_pago")
    private int metodoPago;

    @Column(name = "monto_efectivo")
    private BigDecimal montoEfectivo;

    @Column(name = "monto_tarjeta")
    private BigDecimal montoTarjeta;

    @Column(name = "monto_cheque")
    private BigDecimal montoCheque;

    @Column(name = "monto_transferencia")
    private BigDecimal montoTransferencia;

    @Column(name = "referencia_tarjeta")
    private String referenciaTarjeta;

    @Column(name = "numero_cheque")
    private String numeroCheque;

    @Column(name = "referenciaTransferencia")
    private String referenciaTransferencia;

    private BigDecimal subtotal;
    private BigDecimal impuesto;
    private BigDecimal descuento;
    private BigDecimal total;

    private boolean activo;
    
}