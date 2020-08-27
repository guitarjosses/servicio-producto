package movil.pos.producto.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import movil.pos.producto.repository.entity.Producto;

public interface ProductoRepository extends JpaRepository<Producto, Long>{
    public List<Producto> findByCategoriaProductoId(Long categoriaProductoId);
    public Producto findByNombre(String nombre);
    public Producto findTopByOrderByIdDesc();
}