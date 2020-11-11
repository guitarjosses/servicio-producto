package movil.pos.producto.repository;

import java.util.List;

import movil.pos.producto.repository.entity.Compra;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompraRepository extends JpaRepository<Compra, Long>{

    public Compra findByNumeroCompra(String numeroCompra);
    public List<Compra> findByProveedorId(Long proveedorId);
    
}