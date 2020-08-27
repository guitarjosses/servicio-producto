package movil.pos.producto.controller;

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

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import movil.pos.producto.repository.entity.ListaPrecio;
import movil.pos.producto.service.ListaPrecioService;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Slf4j
@RestController
@RequestMapping("/listas/precio")
public class ListaPrecioRest {

    @Autowired
    ListaPrecioService listaPrecioService;

    // -------------------Retrieve All Listas de Precio--------------------------------------------
    @GetMapping
    public ResponseEntity<List<ListaPrecio>> listAllInvoices() {
        List<ListaPrecio> listasPrecio = listaPrecioService.encontrarTodosLasListasPrecios();
        if (listasPrecio.isEmpty()) {
            return  ResponseEntity.noContent().build();
        }
        return  ResponseEntity.ok(listasPrecio);
    }

    // -------------------Retrieve una Lista de Precios------------------------------------------
    @GetMapping(value = "/{id}")
    public ResponseEntity<ListaPrecio> getInvoice(@PathVariable("id") long id) {
        log.info("Fetching Lista de Precio with id {}", id);
        ListaPrecio listaPrecio  = listaPrecioService.obtenerListaPrecio(id);
        if (null == listaPrecio) {
            log.error("Lista de Precio with id {} not found.", id);
            return  ResponseEntity.notFound().build();
        }
        return  ResponseEntity.ok(listaPrecio);
    }

    // -------------------Create a Invoice-------------------------------------------
    @PostMapping
    public ResponseEntity<ListaPrecio> crearListaPrecio(@RequestBody ListaPrecio listaPrecio, BindingResult result) {
        log.info("Creating Lista de Precio : {}", listaPrecio);
        if (result.hasErrors()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, this.formatMessage(result));
        }
        ListaPrecio listaPrecioDB = listaPrecioService.crearListaPrecio(listaPrecio);

        return  ResponseEntity.status( HttpStatus.CREATED).body(listaPrecioDB);
    }

    // ------------------- Update a Invoice ------------------------------------------------
    @PutMapping(value = "/{id}")
    public ResponseEntity<?> actualizarListaPrecio(@PathVariable("id") long id, @RequestBody ListaPrecio listaPrecio) {
        log.info("Updating Lista de Precio with id {}", id);

        listaPrecio.setId(id);
        ListaPrecio listaPrecioActual=listaPrecioService.actualizarListaPrecio(listaPrecio);

        if (listaPrecioActual == null) {
            log.error("Unable to update. Lista de Precio with id {} not found.", id);
            return  ResponseEntity.notFound().build();
        }
        return  ResponseEntity.ok(listaPrecioActual);
    }

    // ------------------- Delete a Invoice-----------------------------------------
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<ListaPrecio> borrarListaPrecio(@PathVariable("id") long id) {
        log.info("Fetching & Deleting Lista de Precio with id {}", id);

        ListaPrecio listaPrecio = listaPrecioService.obtenerListaPrecio(id);
        if (listaPrecio == null) {
            log.error("Unable to delete. Lista de Precio with id {} not found.", id);
            return  ResponseEntity.notFound().build();
        }
        listaPrecio = listaPrecioService.borrarListaPrecio(listaPrecio);
        return ResponseEntity.ok(listaPrecio);
    }

    private String formatMessage( BindingResult result){
        List<Map<String,String>> errors = result.getFieldErrors().stream()
                .map(err ->{
                    Map<String,String> error =  new HashMap<>();
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