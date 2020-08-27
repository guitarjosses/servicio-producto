package movil.pos.producto.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import movil.pos.producto.repository.CategoriaProductoRepository;
import movil.pos.producto.repository.entity.CategoriaProducto;

@Service
public class CategoriaProductoServiceImpl implements CategoriaProductoService {
    @Autowired
    CategoriaProductoRepository categoriaProductoRepository;

    @Override
    public List<CategoriaProducto> encontrarTodasCategoriasProducto() {
        return categoriaProductoRepository.findAll();
    }

    @Override
    public CategoriaProducto crearCategoriaProducto(CategoriaProducto categoriaProducto) {
        CategoriaProducto categoriaProductoDB = categoriaProductoRepository.findByNombre( categoriaProducto.getNombre() );
        if (categoriaProductoDB != null){
            return  categoriaProductoDB;
        }

        categoriaProductoDB = categoriaProductoRepository.save ( categoriaProducto );
        return categoriaProductoDB;
    }

    @Override
    public CategoriaProducto actualizarCategoriaProducto(CategoriaProducto categoriaProducto) {
        CategoriaProducto categoriaProductoDB = obtenerCategoriaProducto(categoriaProducto.getId());
        if (categoriaProductoDB == null){
            return  null;
        }
        categoriaProductoDB.setNombre(categoriaProducto.getNombre());
        categoriaProductoDB.setDescripcion(categoriaProducto.getDescripcion());
        categoriaProductoDB.setActiva(categoriaProducto.isActiva());

        return  categoriaProductoRepository.save(categoriaProductoDB);
    }

    @Override
    public CategoriaProducto borrarCategoriaProducto(CategoriaProducto categoriaProducto) {
        CategoriaProducto categoriaProductoDB = obtenerCategoriaProducto(categoriaProducto.getId());
        if (categoriaProductoDB ==null){
            return  null;
        }

        return categoriaProductoRepository.save(categoriaProducto);
    }

    @Override
    public CategoriaProducto obtenerCategoriaProducto(Long id) {
        return  categoriaProductoRepository.findById(id).orElse(null);
    }
    
}