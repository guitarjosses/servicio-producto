package movil.pos.producto.repository;

import java.util.List;

import movil.pos.producto.repository.entity.Pago;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PagoRepository extends JpaRepository<Pago, Long>{

    
    public Pago findByNumeroPago(String numeroPago);
    public List<Pago> findByProveedorId(Long proveedorId);
    
}