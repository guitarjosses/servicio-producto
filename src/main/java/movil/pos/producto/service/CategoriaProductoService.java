package movil.pos.producto.service;

import java.util.List;

import movil.pos.producto.repository.entity.CategoriaProducto;

public interface CategoriaProductoService {
    

    public List<CategoriaProducto> encontrarTodasCategoriasProducto();

    public CategoriaProducto crearCategoriaProducto(CategoriaProducto categoriaProducto);
    public CategoriaProducto actualizarCategoriaProducto(CategoriaProducto custocategoriaProducto);
    public CategoriaProducto borrarCategoriaProducto(CategoriaProducto categoriaProducto);
    public CategoriaProducto obtenerCategoriaProducto(Long id);
}