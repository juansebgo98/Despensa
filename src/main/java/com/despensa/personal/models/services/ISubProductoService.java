package com.despensa.personal.models.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.despensa.personal.models.entity.Producto;
import com.despensa.personal.models.entity.Inventario;

public interface IInventarioService {

	public List<Inventario> findAll();
	
	public Page<Inventario> findAll(Pageable pageable);

	public Inventario findById(Long id);
	
	public Inventario save(Inventario subProducto);
	
	public void delete(Long id);
	
	public List<Inventario> obtenerInventariosPorProducto(Producto producto);

}
