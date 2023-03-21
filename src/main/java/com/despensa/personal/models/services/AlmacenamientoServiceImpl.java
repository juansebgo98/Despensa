package com.despensa.personal.models.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.despensa.personal.models.dao.IAlmacenamientoDao;
import com.despensa.personal.models.entity.Almacenamiento;

@Service
public class AlmacenamientoServiceImpl implements IAlmacenamientoService{

	@Autowired
	private IAlmacenamientoDao almacenamientoDao;
	
	@Override
	@Transactional(readOnly = true)
	public List<Almacenamiento> findAll() {
		return (List<Almacenamiento>) almacenamientoDao.findAll();
	}
	

	@Override
	@Transactional(readOnly = true)
	public Page<Almacenamiento> findAll(Pageable pageable) {
		return almacenamientoDao.findAll(pageable);
	}

	@Override
	@Transactional(readOnly = true)
	public Almacenamiento findById(Long id) {
		return almacenamientoDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public Almacenamiento save(Almacenamiento producto) {
		return almacenamientoDao.save(producto);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		almacenamientoDao.deleteById(id);
		
	}


	@Override
	public Almacenamiento obtenerAlmacenamientoProducto(Long producto) {
		return almacenamientoDao.obtenerAlmacenamientoProducto(producto);
	}

}
