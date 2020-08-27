package movil.pos.producto.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import movil.pos.producto.repository.entity.Proveedor;

public interface ProveedorRepository extends JpaRepository<Proveedor, Long>{
    public Proveedor findByRucCi(String rucci);
    public List<Proveedor> findByNombre(String nombre);
}