package movil.pos.producto.repository.entity;

import java.math.BigDecimal;

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
@Table(name = "producto", schema = "movilpos")
public class Producto {

	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "productoSequenceGenerator")
    @SequenceGenerator(name = "productoSequenceGenerator", sequenceName = "producto_id_seq", allocationSize = 1,schema = "movilpos")
    private Long id;
    
	private String codigo;
	private String nombre;
    
    @ManyToOne
    @JoinColumn(name = "categoria_producto_id")
    private CategoriaProducto categoriaProducto;

    @ManyToOne
    @JoinColumn(name = "unidad_medida_id")
    private UnidadMedida unidadMedida;

    @ManyToOne
    @JoinColumn(name = "impuesto_id")
    private Impuesto impuesto;
    
    @Column(name = "es_servicio")
    private boolean esServicio;
    
    @Column(name = "precio_compra")
    private BigDecimal precioCompra;

    @Column(name = "porcentaje_precio_venta_minorista")
    private BigDecimal porcentajePrecioVentaMinorista;
    
    @Column(name = "precio_venta_minorista")
    private BigDecimal precioVentaMinorista;
    
    @Column(name = "porcentaje_precio_venta_mayorista")
    private BigDecimal porcentajePrecioVentaMayorista;
    
    @Column(name = "precio_venta_mayorista")
    private BigDecimal precioVentaMayorista;  
    
    @Column(name = "stock_actual")
    private int stockActual;
    
    @Column(name = "stock_minimo")
    private int stockMinimo;
    
	private boolean activo;

}