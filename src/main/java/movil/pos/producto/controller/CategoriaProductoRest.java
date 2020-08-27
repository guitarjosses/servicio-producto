package movil.pos.producto.controller;

import movil.pos.producto.service.CategoriaProductoService;

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
import movil.pos.producto.repository.entity.CategoriaProducto;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/categorias/producto")
public class CategoriaProductoRest {

    @Autowired
    CategoriaProductoService categoriaProductoService;
  
    // -------------------Retrieve All Categorias Producto--------------------------------------------
  
    @GetMapping
    public ResponseEntity<List<CategoriaProducto>> obtenerTodasLasCategoriasProducto() {
        List<CategoriaProducto> categoriasProducto =  new ArrayList<>();
        categoriasProducto = categoriaProductoService.encontrarTodasCategoriasProducto();
            if (categoriasProducto.isEmpty()) 
                return ResponseEntity.noContent().build();
          
        return  ResponseEntity.ok(categoriasProducto);
    }
  
    // -------------------Retrieve Single Categoria Producto------------------------------------------
  
    @GetMapping(value = "/{id}")
    public ResponseEntity<CategoriaProducto> obtenerCategoriaProducto(@PathVariable("id") long id) {
        log.info("Fetching Categoria Producto with id {}", id);
        CategoriaProducto categoriaProducto = categoriaProductoService.obtenerCategoriaProducto(id);
        if (  null == categoriaProducto) {
            log.error("Categoria Producto with id {} not found.", id);
            return  ResponseEntity.notFound().build();
        }
        return  ResponseEntity.ok(categoriaProducto);
    }
  
    // -------------------Create a Categoria Producto-------------------------------------------
  
    @PostMapping
    public ResponseEntity<CategoriaProducto> crearCategoriaProducto(@RequestBody CategoriaProducto categoriaProducto, BindingResult result) {
        log.info("Creating Categoria Producto : {}", categoriaProducto);
        if (result.hasErrors()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, this.formatMessage(result));
        }
  
        CategoriaProducto categoriaProductoDB = categoriaProductoService.crearCategoriaProducto(categoriaProducto);
  
        return  ResponseEntity.status( HttpStatus.CREATED).body(categoriaProductoDB);
    }
  
    // ------------------- Update a Categoria Producto ------------------------------------------------
  
    @PutMapping(value = "/{id}")
    public ResponseEntity<?> actualizarCategoriaProdcuto(@PathVariable("id") long id, @RequestBody CategoriaProducto categoriaProducto) {
        log.info("Updating Categoria Producto with id {}", id);
  
        CategoriaProducto categoriaProductoActual = categoriaProductoService.obtenerCategoriaProducto(id);
  
        if ( null == categoriaProductoActual ) {
            log.error("Unable to update. categoria Producto with id {} not found.", id);
            return  ResponseEntity.notFound().build();
        }
        categoriaProducto.setId(id);
        categoriaProductoActual=categoriaProductoService.actualizarCategoriaProducto(categoriaProducto);
        return  ResponseEntity.ok(categoriaProductoActual);
    }
  
    // ------------------- Delete a Categoria Producto-----------------------------------------
  
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<CategoriaProducto> borrarCategoriaProducto(@PathVariable("id") long id) {
        log.info("Fetching & Deleting Categoria Producto with id {}", id);
  
        CategoriaProducto categoriaProducto = categoriaProductoService.obtenerCategoriaProducto(id);
        if ( null == categoriaProducto ) {
            log.error("Unable to delete. Categoria Producto with id {} not found.", id);
            return  ResponseEntity.notFound().build();
        }
        categoriaProducto = categoriaProductoService.borrarCategoriaProducto(categoriaProducto);
        return  ResponseEntity.ok(categoriaProducto);
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