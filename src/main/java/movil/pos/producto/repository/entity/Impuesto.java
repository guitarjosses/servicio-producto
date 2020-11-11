package movil.pos.producto.repository.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "impuesto", schema = "movilpos")
public class Impuesto {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "impuestoSequenceGenerator")
    @SequenceGenerator(name = "impuestoSequenceGenerator", sequenceName = "impuesto_id_seq", allocationSize = 1,schema = "movilpos")
    private Long id;

    private String nombre;

    private int tasa;

    private boolean activo;
    
}