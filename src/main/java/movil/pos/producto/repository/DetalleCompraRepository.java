package movil.pos.producto.repository;

import movil.pos.producto.repository.entity.DetalleCompra;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DetalleCompraRepository extends JpaRepository<DetalleCompra, Long>{

    public List<DetalleCompra> findByCompraId(Long compraId);

}