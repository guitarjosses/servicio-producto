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
@Table(name = "unidad_medida", schema = "movilpos")
public class UnidadMedida implements Serializable{

    /**
     *
     */
    private static final long serialVersionUID = 2130107915696144900L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "unidadMedidaSequenceGenerator")
    @SequenceGenerator(name = "unidadMedidaSequenceGenerator", sequenceName = "unidad_medida_id_seq", allocationSize = 1,schema = "movilpos")
    private Long id;
    
    String nombre;
    String descripcion;
    boolean activa;
    
}