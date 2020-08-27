package movil.pos.producto.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import movil.pos.producto.repository.entity.Impuesto;

public interface ImpuestoRepository extends JpaRepository<Impuesto,Long>{
    public Impuesto findByNombre(String nombre);
}