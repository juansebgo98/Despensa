package com.despensa.personal.models.services;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.despensa.personal.models.entity.Almacenamiento;

public interface IAlmacenamientoService {

	public List<Almacenamiento> findAll();
	
	public Page<Almacenamiento> findAll(Pageable pageable);

	public Almacenamiento findById(Long id);
	
	public Almacenamiento save(Almacenamiento producto);
	
	public List<Almacenamiento> obtenerAlmacenamientoProducto(Long producto);
	
	public void delete(Long id);
}
