package movil.pos.producto.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import movil.pos.producto.repository.entity.CategoriaProducto;

public interface CategoriaProductoRepository extends JpaRepository<CategoriaProducto, Long> {
    public CategoriaProducto findByNombre(String nombre);

}