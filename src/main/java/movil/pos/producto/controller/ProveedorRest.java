package movil.pos.producto.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

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

import lombok.extern.slf4j.Slf4j;
import movil.pos.producto.repository.entity.Proveedor;
import movil.pos.producto.service.ProveedorService;

@Slf4j
@RestController
@RequestMapping("/proveedores")
public class ProveedorRest {

    @Autowired
    ProveedorService proveedorService;

    // -------------------Retrieve All Categorias Producto--------------------------------------------
  
    @GetMapping
    public ResponseEntity<List<Proveedor>> obtenerTodosLosProveedores() {
        List<Proveedor> proveedores =  new ArrayList<>();
        proveedores = proveedorService.obtenerTodosLosProveedores();
            if (proveedores.isEmpty()) 
                return ResponseEntity.noContent().build();
          
        return  ResponseEntity.ok(proveedores);
    }
  
    // -------------------Retrieve Single Categoria Producto------------------------------------------
  
    @GetMapping(value = "/{id}")
    public ResponseEntity<Proveedor> obtenerProveedor(@PathVariable("id") long id) {
        log.info("Fetching Proveedor with id {}", id);
        Proveedor proveedor = proveedorService.obtenerProveedor(id);
        if (  null == proveedor) {
            log.error("Proveedor with id {} not found.", id);
            return  ResponseEntity.notFound().build();
        }
        return  ResponseEntity.ok(proveedor);
    }
  
    // -------------------Create a Categoria Producto-------------------------------------------
  
    @PostMapping
    public ResponseEntity<Proveedor> crearProveedor(@RequestBody Proveedor proveedor, BindingResult result) {
        log.info("Creating Proveedor: {}", proveedor);
        if (result.hasErrors()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, this.formatMessage(result));
        }
  
        Proveedor proveedorDB = proveedorService.crearProveedor(proveedor);
  
        return  ResponseEntity.status( HttpStatus.CREATED).body(proveedorDB);
    }
  
    // ------------------- Update a Categoria Producto ------------------------------------------------
  
    @PutMapping(value = "/{id}")
    public ResponseEntity<Proveedor> actualizarProveedor(@PathVariable("id") long id, @RequestBody Proveedor proveedor) {
        log.info("Updating Proveedor with id {}", id);
  
        Proveedor proveedorActual = proveedorService.obtenerProveedor(id);
  
        if ( null == proveedorActual ) {
            log.error("Unable to update. Proveedor with id {} not found.", id);
            return  ResponseEntity.notFound().build();
        }
        proveedor.setId(id);
        proveedorActual=proveedorService.actualizarProveedor(proveedor);
        return  ResponseEntity.ok(proveedorActual);
    }
  
    // ------------------- Delete a Categoria Producto-----------------------------------------
  
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Proveedor> borrarProveedor(@PathVariable("id") long id) {
        log.info("Fetching & Deleting Proveedor with id {}", id);
  
        Proveedor proveedor = proveedorService.obtenerProveedor(id);
        if ( null == proveedor) {
            log.error("Unable to delete. Proveedor with id {} not found.", id);
            return  ResponseEntity.notFound().build();
        }
        proveedor = proveedorService.eliminarProveedor(proveedor);
        return  ResponseEntity.ok(proveedor);
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