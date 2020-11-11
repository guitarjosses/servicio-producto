package movil.pos.producto.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import movil.pos.producto.repository.entity.Producto;
import movil.pos.producto.service.ProductoService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/productos")
public class ProductoRest {

    @Autowired
    ProductoService productoService;
  
    // -------------------Retrieve All Productos--------------------------------------------
  
    @GetMapping
    public ResponseEntity<List<Producto>> obtenerTodosLosProductos() {
        List<Producto> productos =  new ArrayList<>();
        productos = productoService.encontrarTodosLosProductos();
            if (productos.isEmpty()) 
                return ResponseEntity.noContent().build();
          
        return  ResponseEntity.ok(productos);
    }
  
    @GetMapping("/ultimo")
    public ResponseEntity<Producto> obtenerUltimoProducto(){
        Producto producto;
        producto = productoService.obtenerUltimoRegistro();

        return ResponseEntity.ok(producto);
    }
    // -------------------Retrieve Single Producto------------------------------------------
  
    @GetMapping(value = "/{id}")
    public ResponseEntity<Producto> obtenerProducto(@PathVariable("id") long id) {
        log.info("Fetching Producto with id {}", id);
        Producto producto = productoService.obtenerProducto(id);
        if (  null == producto) {
            log.error("Producto with id {} not found.", id);
            return  ResponseEntity.notFound().build();
        }
        return  ResponseEntity.ok(producto);
    }
  
    @GetMapping(value = "por/codigo/{codigo}")
    public ResponseEntity<Producto> obtenerProducto(@PathVariable("codigo") String codigo) {
        log.info("Fetching Producto with codigo {}", codigo);
        Producto producto = productoService.obtenerProducto(codigo);
        if (  null == producto) {
            log.error("Producto with codigo {} not found.", codigo);
            return  ResponseEntity.notFound().build();
        }
        return  ResponseEntity.ok(producto);
    }

    // -------------------Create a Producto-------------------------------------------
  
    @PostMapping
    public ResponseEntity<Producto> crearProducto(@RequestBody Producto producto, BindingResult result) {
        log.info("Registrando Producto : {}", producto);
        if (result.hasErrors()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, this.formatMessage(result));
        }
  
        Producto productoDB = productoService.crearProducto(producto);
  
        return  ResponseEntity.status( HttpStatus.CREATED).body(productoDB);
    }
  
    // ------------------- Update a Producto ------------------------------------------------
  
    @PutMapping(value = "/{id}")
    public ResponseEntity<?> actualizarProductos(@PathVariable("id") long id, @RequestBody Producto producto) {
        log.info("Updating Producto with id {}", id);
  
        Producto productoActual = productoService.obtenerProducto(id);
  
        if ( null == productoActual ) {
            log.error("Unable to update. Producto with id {} not found.", id);
            return  ResponseEntity.notFound().build();
        }
        producto.setId(id);
        productoActual=productoService.actualizarProducto(producto);
        return  ResponseEntity.ok(productoActual);
    }

    @PutMapping(value = "/actualizar/stock/{id}")
    public ResponseEntity<?> actualizarSctockProducto(@PathVariable("id") long id, @RequestBody Producto producto) {
  
        Producto productoActual = productoService.obtenerProducto(id);
  
        if ( null == productoActual ) {
            return  ResponseEntity.notFound().build();
        }
        producto.setId(id);
        productoActual=productoService.actualizarStockProducto(producto);
        return  ResponseEntity.ok(productoActual);
    }

    @PutMapping(value = "/actualizar/stock/precio/compra/{id}")
    public ResponseEntity<?> actualizarSctockYPrecioCompraProducto(@PathVariable("id") long id, @RequestBody Producto producto) {
  
        Producto productoActual = productoService.obtenerProducto(id);
  
        if ( null == productoActual ) {
            return  ResponseEntity.notFound().build();
        }
        producto.setId(id);
        productoActual=productoService.actualizarStockProducto(producto);
        return  ResponseEntity.ok(productoActual);
    }

  
    // ------------------- Delete a Categoria Producto-----------------------------------------
  
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Producto> borrarProducto(@PathVariable("id") long id) {
        log.info("Fetching & Deleting Producto with id {}", id);
  
        Producto producto = productoService.obtenerProducto(id);
        if ( null == producto ) {
            log.error("Unable to delete. Producto with id {} not found.", id);
            return  ResponseEntity.notFound().build();
        }
        producto = productoService.borrarProducto(producto);
        return  ResponseEntity.ok(producto);
    }
  
    private String formatMessage( BindingResult result){
        List<Map<String,String>> errors = result.getFieldErrors().stream()
                .map(err ->{
                    Map<String,String>  error =  new HashMap<>();
                    error.put(err.getField(), err.getDefaultMessage());
                    return error;
  
                }).collect(Collectors.toList());
        ErrorMessage errorMessage = ErrorMessage.builder()
                .code("01")
                .messages(errors).build();
        ObjectMapper mapper = new ObjectMapper();
        String jsonString="";
        try {
            jsonString = mapper.writeValueAsString(errorMessage);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return jsonString;
    }      
    
}