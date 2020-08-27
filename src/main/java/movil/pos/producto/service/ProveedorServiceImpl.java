package movil.pos.producto.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import movil.pos.producto.repository.ProveedorRepository;
import movil.pos.producto.repository.entity.Proveedor;

@Service
public class ProveedorServiceImpl implements ProveedorService {

@Autowired
ProveedorRepository proveedorRepository;

    @Override
    public List<Proveedor> obtenerTodosLosProveedores() {
        return proveedorRepository.findAll();
    }

    @Override
    public Proveedor crearProveedor(Proveedor proveedor) {

        Proveedor proveedorDB = proveedorRepository.findByRucCi( proveedor.getRucCi());
        if (proveedorDB != null){
            return  proveedorDB;
        }

        proveedorDB = proveedorRepository.save ( proveedor );
        return proveedorDB;
    }

    @Override
    public Proveedor actualizarProveedor(Proveedor proveedor) {
        Proveedor proveedorDB = obtenerProveedor(proveedor.getId());
        if (proveedorDB == null){
            return  null;
        }
        proveedorDB.setRucCi(proveedor.getRucCi());
        proveedorDB.setNombre(proveedor.getNombre());
        proveedorDB.setDireccion(proveedor.getDireccion());
        proveedorDB.setTelefono(proveedor.getTelefono());
        proveedorDB.setMovil(proveedor.getMovil());
        proveedorDB.setCorreoElectronico(proveedor.getCorreoElectronico());
        proveedorDB.setActivo(proveedor.isActivo());

        return  proveedorRepository.save(proveedorDB);
    }

    @Override
    public Proveedor eliminarProveedor(Proveedor proveedor) {
        Proveedor proveedorDB = obtenerProveedor(proveedor.getId());
        if (proveedorDB ==null){
            return  null;
        }

        return proveedorRepository.save(proveedor);
    }

    @Override
    public Proveedor obtenerProveedor(Long id) {
        return proveedorRepository.findById(id).orElse(null);
    }
    
}