package movil.pos.producto.service;

import java.util.List;

import movil.pos.producto.repository.entity.Proveedor;

public interface ProveedorService {

    public List<Proveedor> obtenerTodosLosProveedores();

    public Proveedor crearProveedor(Proveedor proveedor);
    public Proveedor actualizarProveedor(Proveedor proveedor);
    public Proveedor eliminarProveedor(Proveedor proveedor);
    public Proveedor obtenerProveedor(Long id);
    
}