package movil.pos.producto.service;

import java.util.List;

import movil.pos.producto.repository.entity.UnidadMedida;

public interface UnidadMedidaService {
    

    public List<UnidadMedida> encontrarTodasUnidadesMedida();

    public UnidadMedida crearUnidadMedida(UnidadMedida unidadMedida);
    public UnidadMedida actualizarUnidadMedida(UnidadMedida unidadMedida);
    public UnidadMedida borrarUnidadMedida(UnidadMedida unidadMedida);
    public UnidadMedida obtenerUnidadMedida(Long id);
}