package movil.pos.producto.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import movil.pos.producto.repository.ListaPrecioDetalleRepository;
import movil.pos.producto.repository.ListaPrecioRepository;
import movil.pos.producto.repository.ProductoRepository;
import movil.pos.producto.repository.entity.ListaPrecio;

@Service
public class ListaPrecioServiceImpl implements ListaPrecioService {

    @Autowired
    ListaPrecioRepository listaPrecioRepository;

    @Autowired
    ListaPrecioDetalleRepository listaPrecioDetalleRepository;

    @Autowired
    ProductoRepository productoRepository;

    @Override
    public List<ListaPrecio> encontrarTodosLasListasPrecios() {
        return listaPrecioRepository.findAll();
    }

    @Override
    public ListaPrecio crearListaPrecio(ListaPrecio listaPrecio) {
        ListaPrecio listaPrecioDB = listaPrecioRepository.findByNombre( listaPrecio.getNombre());
        if (listaPrecioDB !=null){
            return  listaPrecioDB;
        }

        listaPrecioDB = listaPrecioRepository.save(listaPrecio);

        return listaPrecioDB;
    }

    @Override
    public ListaPrecio actualizarListaPrecio(ListaPrecio listaPrecio) {
        ListaPrecio listaPrecioDB = obtenerListaPrecio(listaPrecio.getId());
        if (listaPrecioDB == null){
            return  null;
        }
        listaPrecioDB.setNombre(listaPrecio.getNombre());
        listaPrecioDB.setActiva(listaPrecio.isActiva());

        return listaPrecioRepository.save(listaPrecioDB);
    }

    @Override
    public ListaPrecio borrarListaPrecio(ListaPrecio listaPrecio) {
        ListaPrecio listaPrecioDB = obtenerListaPrecio(listaPrecio.getId());
        if (listaPrecioDB == null){
            return  null;
        }
        return listaPrecioRepository.save(listaPrecioDB);
    }

    @Override
    public ListaPrecio obtenerListaPrecio(Long id) {
        ListaPrecio listaPrecio= listaPrecioRepository.findById(id).orElse(null);

        return listaPrecio ;
    }
    
}