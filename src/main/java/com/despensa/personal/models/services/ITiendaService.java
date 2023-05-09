package com.despensa.personal.models.services;

import java.util.List;

import com.despensa.personal.models.entity.Tienda;

public interface ITiendaService {

	public List<Tienda> findAll();

	public Tienda findById(Long id);

	public Tienda save(Tienda producto);

	public void delete(Long id);

}
