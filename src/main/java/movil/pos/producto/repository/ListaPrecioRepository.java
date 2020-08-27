package movil.pos.producto.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import movil.pos.producto.repository.entity.ListaPrecio;

public interface ListaPrecioRepository extends JpaRepository<ListaPrecio,Long>{
    public ListaPrecio findByNombre(String nombre);
}