package movil.pos.producto.service;

import java.util.List;

import movil.pos.producto.repository.entity.Producto;

public interface ProductoService {
    
    public List<Producto> encontrarTodosLosProductos();

    public Producto crearProducto(Producto producto);
    public Producto actualizarProducto(Producto producto);
    public Producto borrarProducto(Producto producto);
    public Producto obtenerProducto(Long id);
    public List<Producto> findByCategoriaProductoId(Long idCategoriaProducto);
    public Producto obtenerUltimoRegistro();
    public Producto obtenerProducto(String codigo);

}