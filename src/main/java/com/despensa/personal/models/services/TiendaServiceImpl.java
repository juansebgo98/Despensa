package com.despensa.personal.models.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.despensa.personal.models.dao.ITiendaDao;
import com.despensa.personal.models.entity.Tienda;

@Service
public class TiendaServiceImpl implements ITiendaService {

	@Autowired
	private ITiendaDao productoDao;

	@Override
	@Transactional(readOnly = true)
	public List<Tienda> findAll() {
		return (List<Tienda>) productoDao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Tienda findById(Long id) {
		return productoDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public Tienda save(Tienda producto) {
		return productoDao.save(producto);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		productoDao.deleteById(id);

	}

}
