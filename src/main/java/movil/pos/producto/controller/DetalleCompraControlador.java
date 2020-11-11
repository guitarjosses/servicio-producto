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
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import movil.pos.producto.repository.DetalleCompraRepository;
import movil.pos.producto.repository.entity.DetalleCompra;

@RestController
@RequestMapping("/api")
public class DetalleCompraControlador {

    @Autowired
    DetalleCompraRepository detalleCompraRepositorio;

    @GetMapping("detalle/compra/{id}")
    public ResponseEntity <List<DetalleCompra>> obtenerDetallesCompraPorCompraId(@PathVariable(value = "id") Long compraId) {
        

        List<DetalleCompra> detallesCompra = detalleCompraRepositorio.findByCompraId(compraId);
                if (  null == detallesCompra) {
                    return  ResponseEntity.notFound().build();
                }
                return  ResponseEntity.ok(detallesCompra);
    }

    @PostMapping("/detalle/compra")
    public ResponseEntity<DetalleCompra> crearDetalleCompra( @Validated @RequestBody DetalleCompra detalleCompra, BindingResult result) {


        if (result.hasErrors()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, this.formatMessage(result));
        }
  
        DetalleCompra _detalleCompra  = detalleCompraRepositorio.save(detalleCompra);
  
        return  ResponseEntity.status( HttpStatus.CREATED).body(_detalleCompra);
    
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