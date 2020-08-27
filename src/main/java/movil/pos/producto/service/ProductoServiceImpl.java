package movil.pos.producto.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import movil.pos.producto.repository.ProductoRepository;
import movil.pos.producto.repository.entity.Producto;

@Service
public class ProductoServiceImpl implements ProductoService {
    @Autowired
    ProductoRepository productoRepository;

    @Override
    public Producto obtenerUltimoRegistro(){
        Producto productoDB = productoRepository.findTopByOrderByIdDesc();
        return productoDB;
    }

    @Override
    public List<Producto> encontrarTodosLosProductos() {
        return productoRepository.findAll();
    }

    @Override
    public Producto crearProducto(Producto producto) {
        Producto productoDB = productoRepository.findByNombre( producto.getNombre() );
        if (productoDB != null){
            return  productoDB;
        }

        productoDB = productoRepository.save (producto);
        return productoDB;
    }

    @Override
    public Producto actualizarProducto(Producto producto) {
        Producto productoDB = obtenerProducto(producto.getId());
        if (productoDB == null){
            return  null;
        }

        productoDB.setCodigo(producto.getCodigo());
        productoDB.setNombre(producto.getNombre());
        productoDB.setCategoriaProductoId(producto.getCategoriaProductoId());
        productoDB.setImpuestoId(producto.getImpuestoId());
        productoDB.setEsServicio(producto.isEsServicio());
        productoDB.setUnidadMedidaId(producto.getUnidadMedidaId());
        productoDB.setPrecioCompra(producto.getPrecioCompra());
        productoDB.setPorcentajePrecioVentaMinorista(producto.getPorcentajePrecioVentaMinorista());
        productoDB.setPrecioVentaMinorista(producto.getPrecioVentaMinorista());
        productoDB.setPorcentajePrecioVentaMayorista(producto.getPorcentajePrecioVentaMayorista());
        productoDB.setPrecioVentaMayorista(producto.getPrecioVentaMayorista());
        productoDB.setStockActual(producto.getStockActual());
        productoDB.setStockMinimo(producto.getStockMinimo());
        productoDB.setActivo(producto.isActivo());

        return  productoRepository.save(productoDB);
    }

    @Override
    public Producto borrarProducto(Producto producto) {
        Producto productoDB = obtenerProducto(producto.getId());
        if (productoDB ==null){
            return  null;
        }

        return productoRepository.save(producto);
    }

    @Override
    public Producto obtenerProducto(Long id) {
        return  productoRepository.findById(id).orElse(null);
    }

    @Override
    public List<Producto> findByCategoriaProductoId(Long categoriaProductoId) {
        return  productoRepository.findByCategoriaProductoId(categoriaProductoId);
    }

    
}