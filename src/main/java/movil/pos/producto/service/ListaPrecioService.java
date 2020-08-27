package movil.pos.producto.service;

import java.util.List;

import movil.pos.producto.repository.entity.ListaPrecio;

public interface ListaPrecioService {
    public List<ListaPrecio> encontrarTodosLasListasPrecios();

    public ListaPrecio crearListaPrecio(ListaPrecio listaPrecio);
    public ListaPrecio actualizarListaPrecio(ListaPrecio listaPrecio);
    public ListaPrecio borrarListaPrecio(ListaPrecio listaPrecio);
    public ListaPrecio obtenerListaPrecio(Long id);
}