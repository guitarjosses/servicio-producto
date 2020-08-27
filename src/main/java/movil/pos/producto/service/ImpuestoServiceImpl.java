package movil.pos.producto.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import movil.pos.producto.repository.ImpuestoRepository;
import movil.pos.producto.repository.entity.Impuesto;

@Service
public class ImpuestoServiceImpl implements ImpuestoService {

    @Autowired
    ImpuestoRepository impuestoRepository;

    @Override
    public List<Impuesto> encontrarTodoLosImpuestos() {
        return impuestoRepository.findAll();
    }

    @Override
    public Impuesto crearImpuesto(Impuesto impuesto) {
        Impuesto impuestoDB = impuestoRepository.findByNombre( impuesto.getNombre() );
        if (impuestoDB != null){
            return  impuestoDB;
        }

        impuestoDB = impuestoRepository.save ( impuesto );
        return impuestoDB;
    }

    @Override
    public Impuesto actualizarImpuesto(Impuesto impuesto) {
        Impuesto impuestoDB = obtenerImpuesto(impuesto.getId());
        if (impuestoDB == null){
            return  null;
        }
        impuestoDB.setNombre(impuesto.getNombre());
        impuestoDB.setTasa(impuesto.getTasa());
        impuestoDB.setActivo(impuesto.isActivo());

        return  impuestoRepository.save(impuestoDB);
    }

    @Override
    public Impuesto borrarImpuesto(Impuesto impuesto) {
        Impuesto impuestoDB = obtenerImpuesto(impuesto.getId());
        if (impuestoDB ==null){
            return  null;
        }

        return impuestoRepository.save(impuesto);
    }

    @Override
    public Impuesto obtenerImpuesto(Long id) {
        return impuestoRepository.findById(id).orElse(null);
    }
    
}