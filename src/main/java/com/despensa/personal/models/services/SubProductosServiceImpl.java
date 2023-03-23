package com.despensa.personal.models.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.despensa.personal.models.dao.IInventarioDao;
import com.despensa.personal.models.entity.Producto;
import com.despensa.personal.models.entity.Inventario;

@Service
public class InventariosServiceImpl implements IInventarioService{

	@Autowired
	private IInventarioDao subProductoDao;
	
	@Override
	@Transactional(readOnly = true)
	public List<Inventario> findAll() {
		return (List<Inventario>) subProductoDao.findAll();
	}
	

	@Override
	@Transactional(readOnly = true)
	public Page<Inventario> findAll(Pageable pageable) {
		return subProductoDao.findAll(pageable);
	}

	@Override
	@Transactional(readOnly = true)
	public Inventario findById(Long id) {
		return subProductoDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public Inventario save(Inventario subProducto) {
		return subProductoDao.save(subProducto);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		subProductoDao.deleteById(id);
		
	}

	@Override
	@Transactional
	public List<Inventario> obtenerInventariosPorProducto(Producto producto) {
		return subProductoDao.obtenerPorProducto(producto);
	}


}
