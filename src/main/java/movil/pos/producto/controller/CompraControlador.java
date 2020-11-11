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
import movil.pos.producto.repository.entity.Compra;

@RestController
@RequestMapping("/api")
public class CompraControlador {

    private Compra compra;

    @Autowired
    CompraRepository compraRepositorio;

    @Autowired
    ProveedorRepository proveedorRepositorio;


    @GetMapping("/compra")
    public ResponseEntity<List<Compra>> getCompras() {

        List<Compra> compras =  new ArrayList<>();
        
        compras = compraRepositorio.findAll();

        if (compras.isEmpty()) 
        return ResponseEntity.noContent().build();
  
return  ResponseEntity.ok(compras);
    }

    @PostMapping("proveedor/{proveedorId}/compra")
    public ResponseEntity<Compra> crearCompra(@PathVariable(value = "proveedorId") Long proveedorId,
           @Validated @RequestBody Compra compra, BindingResult result) {

            if (result.hasErrors()){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, this.formatMessage(result));
            }

        this.compra = compra;
        proveedorRepositorio.findById(proveedorId).map(proveedor -> {
            this.compra.setProveedor(proveedor);
            return this.compra;
        }).orElseThrow(() -> new ResourceNotFoundException("Proveedor ","id",proveedorId));
        
        
         compraRepositorio.save(compra);

         return ResponseEntity.status( HttpStatus.CREATED).body(compra);
    }

    @PutMapping(value = "/anular/compra/{id}")
    public ResponseEntity<Compra> anularCompra(@PathVariable("id") long id){

        Compra compraActual = compraRepositorio.findById(id).orElse(null);

        if(compraActual==null){
            return  ResponseEntity.notFound().build();
        }

        compraActual.setActiva(false);

        Compra compraActualizada = compraRepositorio.save(compraActual);
        return ResponseEntity.ok(compraActualizada);
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