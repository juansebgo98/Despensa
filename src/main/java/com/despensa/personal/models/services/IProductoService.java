package com.despensa.personal.models.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.despensa.personal.models.entity.Almacenamiento;
import com.despensa.personal.models.entity.Producto;
import com.despensa.personal.models.entity.Tienda;

public interface IProductoService {

	public List<Producto> findAll();

	public Page<Producto> findAll(Pageable pageable);

	public Producto findById(Long id);

	public Producto save(Producto producto);

	public void delete(Long id);

	public List<Producto> obtenerProductosAlmacenamiento(Almacenamiento almacenamiento);

	public List<Producto> obtenerProductosTienda(Tienda tienda);
}
