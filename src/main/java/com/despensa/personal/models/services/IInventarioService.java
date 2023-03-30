package com.despensa.personal.models.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.despensa.personal.models.entity.Producto;
import com.despensa.personal.models.entity.Almacenamiento;
import com.despensa.personal.models.entity.Inventario;

public interface IInventarioService {

	public List<Inventario> findAll();
	
	public Page<Inventario> findAll(Pageable pageable);

	public Inventario findById(Long id);
	
	public Inventario save(Inventario inventario);
	
	public void delete(Long id);
	
	public List<Inventario> obtenerInventariosPorProducto(Producto producto);

	public List<Inventario> obtenerInventariosPorProductoAlmacen(Producto producto, Almacenamiento almacen);

}
