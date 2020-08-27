package movil.pos.producto.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import movil.pos.producto.repository.entity.UnidadMedida;

public interface UnidadMedidaRepository extends JpaRepository<UnidadMedida, Long> {
    public UnidadMedida findByNombre(String nombre);

}