package movil.pos.producto.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import movil.pos.producto.repository.entity.ListaPrecioDetalle;

public interface ListaPrecioDetalleRepository extends JpaRepository<ListaPrecioDetalle, Long> {
    List<ListaPrecioDetalle> findByListaPrecioId(Long listaPrecioId);
}