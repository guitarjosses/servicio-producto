package movil.pos.producto.service;

import java.util.List;

import movil.pos.producto.repository.entity.Impuesto;

public interface ImpuestoService {
    public List<Impuesto> encontrarTodoLosImpuestos();

    public Impuesto crearImpuesto(Impuesto impuesto);
    public Impuesto actualizarImpuesto(Impuesto impuesto);
    public Impuesto borrarImpuesto(Impuesto impuesto);
    public Impuesto obtenerImpuesto(Long id);
}