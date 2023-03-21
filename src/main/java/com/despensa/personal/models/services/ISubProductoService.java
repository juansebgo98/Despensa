package com.despensa.personal.models.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.despensa.personal.models.entity.Producto;
import com.despensa.personal.models.entity.SubProducto;

public interface ISubProductoService {

	public List<SubProducto> findAll();
	
	public Page<SubProducto> findAll(Pageable pageable);

	public SubProducto findById(Long id);
	
	public SubProducto save(SubProducto subProducto);
	
	public void delete(Long id);
	
	public List<SubProducto> obtenerSubProductosPorProducto(Producto producto);

}
