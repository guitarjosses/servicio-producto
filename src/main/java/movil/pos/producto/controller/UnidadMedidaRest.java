package movil.pos.producto.controller;

import movil.pos.producto.service.UnidadMedidaService;

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
import movil.pos.producto.repository.entity.UnidadMedida;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/unidades/medida")
public class UnidadMedidaRest {

    @Autowired
    UnidadMedidaService unidadMedidaService;
  
    // -------------------Recuperando todas las Unidades de Medida--------------------------------------------
  
    @GetMapping
    public ResponseEntity<List<UnidadMedida>> listaUnidadesMedida() {
        List<UnidadMedida> unidadMedida =  new ArrayList<>();
        unidadMedida = unidadMedidaService.encontrarTodasUnidadesMedida();
            if (unidadMedida.isEmpty()) 
                return ResponseEntity.noContent().build();
          
        return  ResponseEntity.ok(unidadMedida);
    }
  
    // -------------------Recuperando una Unidad de Medida------------------------------------------
  
    @GetMapping(value = "/{id}")
    public ResponseEntity<UnidadMedida> obtenerUnidadMedida(@PathVariable("id") long id) {
        log.info("Buscando Unidad de Medida con id {}", id);
        UnidadMedida unidadMedida = unidadMedidaService.obtenerUnidadMedida(id);
        if (  null == unidadMedida) {
            log.error("No se encontro Unidad de Medida with id {} .", id);
            return  ResponseEntity.notFound().build();
        }
        return  ResponseEntity.ok(unidadMedida);
    }
  
    // -------------------Crear una Unidad de Medida-------------------------------------------
  
    @PostMapping
    public ResponseEntity<UnidadMedida> crearUnidadMedida(@RequestBody UnidadMedida unidadMedida, BindingResult result) {
        log.info("Creando Unidad de Medida : {}", unidadMedida);
        if (result.hasErrors()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, this.formatMessage(result));
        }
  
        UnidadMedida unidadMedidaDB = unidadMedidaService.crearUnidadMedida(unidadMedida);
  
        return  ResponseEntity.status( HttpStatus.CREATED).body(unidadMedidaDB);
    }
  
    // ------------------- Actualizar una Unidad de Medida ------------------------------------------------
  
    @PutMapping(value = "/{id}")
    public ResponseEntity<?> actualizarUnidadMedida(@PathVariable("id") long id, @RequestBody UnidadMedida unidadMedida) {
        log.info("Actualizando Unidad de Medida with id {}", id);
  
        UnidadMedida unidadMedidaActual = unidadMedidaService.obtenerUnidadMedida(id);
  
        if ( null == unidadMedidaActual ) {
            log.error("No se puede actualizar. No se econcontró Unidad de Medida con id {} .", id);
            return  ResponseEntity.notFound().build();
        }
        unidadMedida.setId(id);
        unidadMedidaActual=unidadMedidaService.actualizarUnidadMedida(unidadMedida);
        return  ResponseEntity.ok(unidadMedidaActual);
    }
  
    // ------------------- Eliminar una Unidad de Medida-----------------------------------------
  
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<UnidadMedida> borrarUnidadMedida(@PathVariable("id") long id) {
        log.info("Obteniendo & Eliminando Unidad de Medida con id {}", id);
  
        UnidadMedida unidadMedida = unidadMedidaService.obtenerUnidadMedida(id);
        if ( null == unidadMedida ) {
            log.error("No se puede eliminar. No se econtro Unidad de Medida con id {} .", id);
            return  ResponseEntity.notFound().build();
        }
        unidadMedida = unidadMedidaService.borrarUnidadMedida(unidadMedida);
        return  ResponseEntity.ok(unidadMedida);
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