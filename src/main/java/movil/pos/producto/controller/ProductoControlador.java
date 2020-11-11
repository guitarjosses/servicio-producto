package movil.pos.producto.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import movil.pos.producto.ResourceNotFoundException;
import movil.pos.producto.repository.CategoriaProductoRepository;
import movil.pos.producto.repository.ImpuestoRepository;
import movil.pos.producto.repository.UnidadMedidaRepository;
import movil.pos.producto.repository.ProductoRepository;
import movil.pos.producto.repository.entity.Producto;

@RestController
@RequestMapping("/api")
public class ProductoControlador {

    private Producto producto;

    @Autowired
    ProductoRepository productoRepositorio;

    @Autowired
    CategoriaProductoRepository categoriaProductoRepositorio;

    @Autowired
    UnidadMedidaRepository unidadMedidaRepositorio;

    @Autowired
    ImpuestoRepository impuestoRepositorio;

    @GetMapping("/producto")
    public List<Producto> getProductos() {
        return productoRepositorio.findAll();
    }

    @PostMapping("categoriaProducto/{categoriaProductoId}/unidadMedida/{unidadMedidaId}/impuesto/{impuestoId}/producto")
    public Producto crearProducto(@PathVariable(value = "categoriaProductoId") Long categoriaProductoId,
            @PathVariable(value = "unidadMedidaId") Long unidadMedidaId, @PathVariable(value = "impuestoId") Long impuestoId, @Validated @RequestBody Producto producto) {
        
                this.producto = producto;

        categoriaProductoRepositorio.findById(categoriaProductoId).map(categoriaProducto -> {
            this.producto.setCategoriaProducto(categoriaProducto);
            return this.producto;
        }).orElseThrow(() -> new ResourceNotFoundException("Categoria de Producto ","id",categoriaProductoId));
        
        unidadMedidaRepositorio.findById(unidadMedidaId).map(unidadMedida -> {
            this.producto.setUnidadMedida(unidadMedida);
            return this.producto;
        }).orElseThrow(() -> new ResourceNotFoundException("Unidad de Medida ","id",unidadMedidaId));

        impuestoRepositorio.findById(impuestoId).map(impuesto -> {
            this.producto.setImpuesto(impuesto);
            return this.producto;
        }).orElseThrow(() -> new ResourceNotFoundException("Impuesto ","id",impuestoId));
        
        return productoRepositorio.save(producto);
    }

    @PutMapping("categoriaProducto/{categoriaProductoId}/unidadMedida/{unidadMedidaId}/impuesto/{impuestoId}/producto/{id}")
    public Producto actualizarProducto(@PathVariable(value = "categoriaProductoId") Long categoriaProductoId,
            @PathVariable(value = "unidadMedidaId") Long unidadMedidaId, @PathVariable(value = "impuestoId") Long impuestoId, @PathVariable(value = "id") Long productoId,@Validated @RequestBody Producto nuevoProducto) {

        categoriaProductoRepositorio.findById(categoriaProductoId).map(categoriaProducto -> {
            this.producto.setCategoriaProducto(categoriaProducto);
            return this.producto;
        }).orElseThrow(() -> new ResourceNotFoundException("Categoria de Producto ","id",categoriaProductoId));
        
        unidadMedidaRepositorio.findById(unidadMedidaId).map(unidadMedida -> {
            this.producto.setUnidadMedida(unidadMedida);
            return this.producto;
        }).orElseThrow(() -> new ResourceNotFoundException("Unidad de Medida ","id",unidadMedidaId));

        impuestoRepositorio.findById(impuestoId).map(impuesto -> {
            this.producto.setImpuesto(impuesto);
            return this.producto;
        }).orElseThrow(() -> new ResourceNotFoundException("Impuesto ","id",impuestoId));
        
        return productoRepositorio.findById(productoId).map(producto->{
            producto.setCodigo(nuevoProducto.getCodigo());
            producto.setNombre(nuevoProducto.getNombre());
            producto.setCategoriaProducto(this.producto.getCategoriaProducto());
            producto.setUnidadMedida(this.producto.getUnidadMedida());
            producto.setImpuesto(this.producto.getImpuesto());
            producto.setEsServicio(nuevoProducto.isEsServicio());
            producto.setPrecioCompra(nuevoProducto.getPrecioCompra());
            producto.setPorcentajePrecioVentaMinorista(nuevoProducto.getPorcentajePrecioVentaMinorista());
            producto.setPrecioVentaMinorista(nuevoProducto.getPrecioVentaMinorista());
            producto.setPorcentajePrecioVentaMayorista(nuevoProducto.getPorcentajePrecioVentaMayorista());
            producto.setPrecioVentaMayorista(nuevoProducto.getPrecioVentaMayorista());
            producto.setStockActual(nuevoProducto.getStockActual());
            producto.setStockMinimo(nuevoProducto.getStockMinimo());
            producto.setActivo(nuevoProducto.isActivo());
            
            return productoRepositorio.save(producto);
        }).orElseThrow(() -> new ResourceNotFoundException("Producto ","id",productoId));
    }
    
}