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
import movil.pos.producto.repository.entity.Impuesto;
import movil.pos.producto.service.ImpuestoService;

@Slf4j
@RestController
@RequestMapping("/impuestos")
public class ImpuestoRest {

    @Autowired
    ImpuestoService impuestoService;

    @GetMapping
    public ResponseEntity<List<Impuesto>> obtenerTodosLosImpuestos() {
        List<Impuesto> impuestos =  new ArrayList<>();
        impuestos = impuestoService.encontrarTodoLosImpuestos();
            if (impuestos.isEmpty()) 
                return ResponseEntity.noContent().build();
          
        return  ResponseEntity.ok(impuestos);
    }
  
    // -------------------Retrieve Single Categoria Producto------------------------------------------
  
    @GetMapping(value = "/{id}")
    public ResponseEntity<Impuesto> obtenerImpuesto(@PathVariable("id") long id) {
        log.info("Fetching Impuesto with id {}", id);
        Impuesto impuesto = impuestoService.obtenerImpuesto(id);
        if (  null == impuesto) {
            log.error("Impuesto with id {} not found.", id);
            return  ResponseEntity.notFound().build();
        }
        return  ResponseEntity.ok(impuesto);
    }
  
    // -------------------Create a Categoria Producto-------------------------------------------
  
    @PostMapping
    public ResponseEntity<Impuesto> crearImpuesto(@RequestBody Impuesto impuesto, BindingResult result) {
        log.info("Creating Impuesto : {}", impuesto);
        if (result.hasErrors()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, this.formatMessage(result));
        }
  
        Impuesto impuestoDB = impuestoService.crearImpuesto(impuesto);
  
        return  ResponseEntity.status( HttpStatus.CREATED).body(impuestoDB);
    }
  
    // ------------------- Update a Categoria Producto ------------------------------------------------
  
    @PutMapping(value = "/{id}")
    public ResponseEntity<Impuesto> actualizarImpuesto(@PathVariable("id") long id, @RequestBody Impuesto impuesto) {
        log.info("Updating Impuesto with id {}", id);
  
        Impuesto ImpuestoActual = impuestoService.obtenerImpuesto(id);
  
        if ( null == ImpuestoActual ) {
            log.error("Unable to update. Impuesto with id {} not found.", id);
            return  ResponseEntity.notFound().build();
        }
        impuesto.setId(id);
        ImpuestoActual=impuestoService.actualizarImpuesto(impuesto);
        return  ResponseEntity.ok(ImpuestoActual);
    }
  
    // ------------------- Delete a Categoria Producto-----------------------------------------
  
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Impuesto> borrarImpuesto(@PathVariable("id") long id) {
        log.info("Fetching & Deleting Impuesto with id {}", id);
  
        Impuesto impuesto = impuestoService.obtenerImpuesto(id);
        if ( null == impuesto ) {
            log.error("Unable to delete. Impuesto with id {} not found.", id);
            return  ResponseEntity.notFound().build();
        }
        impuesto = impuestoService.borrarImpuesto(impuesto);
        return  ResponseEntity.ok(impuesto);
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