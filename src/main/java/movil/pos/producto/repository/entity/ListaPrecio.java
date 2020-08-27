package movil.pos.producto.repository.entity;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@Entity
@Table(name = "lista_precio", schema = "movilpos")
public class ListaPrecio {

	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "listaPrecioSequenceGenerator")
    @SequenceGenerator(name = "listaPrecioSequenceGenerator", sequenceName = "lista_precio_id_seq", allocationSize = 1,schema = "movilpos")
    private Long id;

    @JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
    @OneToMany(mappedBy = "listaPrecio",fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    //@JoinColumn(name = "lista_precio_id"/*,referencedColumnName = "id"*/)
    private Set<ListaPrecioDetalle> listaPrecioDetalle;    
    
    String nombre;
    boolean activa;

    /*public ListaPrecio(String nombre, boolean activa){
        this.nombre  = nombre;
        this.activa = activa;
    }*/
    
}
    