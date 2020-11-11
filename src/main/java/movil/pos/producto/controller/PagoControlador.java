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
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import movil.pos.producto.repository.ProveedorRepository;
import movil.pos.producto.ResourceNotFoundException;
import movil.pos.producto.repository.CompraRepository;
import movil.pos.producto.repository.PagoRepository;
import movil.pos.producto.repository.entity.Pago;

@RestController
@RequestMapping("/api")
public class PagoControlador {
    

    private Pago pago;

    @Autowired
    PagoRepository pagoRepositorio;

    @Autowired
    ProveedorRepository proveedorRepositorio;

    @Autowired
    CompraRepository compraRepositorio;


    @GetMapping("/pago")
    public ResponseEntity<List<Pago>> getPagos() {

        List<Pago> pagos =  new ArrayList<>();
        if (pagos.isEmpty()) 
        return ResponseEntity.noContent().build();
  
return  ResponseEntity.ok(pagos);
    }

    @PostMapping("proveedor/{proveedorId}/compra/{compraId}/pago")
    public ResponseEntity<Pago> crearPago(@PathVariable(value = "proveedorId") Long proveedorId,@PathVariable(value = "compraId") Long compraId,
           @Validated @RequestBody Pago pago, BindingResult result) {

            if (result.hasErrors()){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, this.formatMessage(result));
            }

        this.pago = pago;
        proveedorRepositorio.findById(proveedorId).map(proveedor -> {
            this.pago.setProveedor(proveedor);
            return this.pago;
        }).orElseThrow(() -> new ResourceNotFoundException("Proveedor ","id",proveedorId));

        compraRepositorio.findById(compraId).map(compra -> {
            this.pago.setCompra(compra);
            return this.pago;
        }).orElseThrow(() -> new ResourceNotFoundException("Compra ","id",compraId));
        
        
         pagoRepositorio.save(pago);

         return ResponseEntity.status( HttpStatus.CREATED).body(pago);
    }

    @PutMapping(value = "/anular/pago/{id}")
    public ResponseEntity<Pago> anularPago(@PathVariable("id") long id){

        Pago pagoActual = pagoRepositorio.findById(id).orElse(null);

        if(pagoActual==null){
            return  ResponseEntity.notFound().build();
        }

        pagoActual.setActivo(false);

        Pago pagoActualizado = pagoRepositorio.save(pagoActual);
        return ResponseEntity.ok(pagoActualizado);
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