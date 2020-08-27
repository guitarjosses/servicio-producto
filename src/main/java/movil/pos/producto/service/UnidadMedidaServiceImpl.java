package movil.pos.producto.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import movil.pos.producto.repository.UnidadMedidaRepository;
import movil.pos.producto.repository.entity.UnidadMedida;

@Service
public class UnidadMedidaServiceImpl implements UnidadMedidaService {
    @Autowired
    UnidadMedidaRepository unidadMedidaRepository;

    @Override
    public List<UnidadMedida> encontrarTodasUnidadesMedida() {
        return unidadMedidaRepository.findAll();
    }

    @Override
    public UnidadMedida crearUnidadMedida(UnidadMedida unidadMedida) {
        UnidadMedida unidadMedidaDB = unidadMedidaRepository.findByNombre( unidadMedida.getNombre() );
        if (unidadMedidaDB != null){
            return  unidadMedidaDB;
        }

        unidadMedidaDB = unidadMedidaRepository.save ( unidadMedida );
        return unidadMedidaDB;
    }

    @Override
    public UnidadMedida actualizarUnidadMedida(UnidadMedida unidadMedida) {
        UnidadMedida categoriaProductoDB = obtenerUnidadMedida(unidadMedida.getId());
        if (categoriaProductoDB == null){
            return  null;
        }
        categoriaProductoDB.setNombre(unidadMedida.getNombre());
        categoriaProductoDB.setDescripcion(unidadMedida.getDescripcion());
        categoriaProductoDB.setActiva(unidadMedida.isActiva());

        return  unidadMedidaRepository.save(categoriaProductoDB);
    }

    @Override
    public UnidadMedida borrarUnidadMedida(UnidadMedida unidadMedida) {
        UnidadMedida categoriaProductoDB = obtenerUnidadMedida(unidadMedida.getId());
        if (categoriaProductoDB ==null){
            return  null;
        }

        return unidadMedidaRepository.save(unidadMedida);
    }

    @Override
    public UnidadMedida obtenerUnidadMedida(Long id) {
        return  unidadMedidaRepository.findById(id).orElse(null);
    }
    
}