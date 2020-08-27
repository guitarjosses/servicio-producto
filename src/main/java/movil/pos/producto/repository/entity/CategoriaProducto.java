package movil.pos.producto.repository.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "categoria_producto", schema = "movilpos")

public class CategoriaProducto  implements Serializable{

    /**
     *
     */
    private static final long serialVersionUID = -1465323378241930173L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "categoriaProductoSequenceGenerator")
    @SequenceGenerator(name = "categoriaProductoSequenceGenerator", sequenceName = "categoria_producto_id_seq", allocationSize = 1,schema = "movilpos")
    private Long id;

    String nombre;
    String descripcion;
    boolean activa;
    
}