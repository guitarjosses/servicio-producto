package movil.pos.producto.repository.entity;

import java.io.Serializable;

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
@Table(name = "proveedor", schema = "movilpos")
public class Proveedor implements Serializable{
    /**
    *
    */
    private static final long serialVersionUID = -5975052896281083994L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "proveedorSequenceGenerator")
    @SequenceGenerator(name = "proveedorSequenceGenerator", sequenceName = "proveedor_id_seq", allocationSize = 1,schema = "movilpos")
    private Long id;

    @Column(name = "ruc_ci")
    private String rucCi;

    private String nombre;
    private String direccion;
    private String ciudad;
    private String telefono;
    private String movil;

    @Column(name = "correo_electronico")
    private String correoElectronico;    
    
    private boolean activo;
}