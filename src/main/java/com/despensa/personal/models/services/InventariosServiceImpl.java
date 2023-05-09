package com.despensa.personal.models.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.despensa.personal.models.dao.IInventarioDao;
import com.despensa.personal.models.entity.Producto;
import com.despensa.personal.models.entity.Almacenamiento;
import com.despensa.personal.models.entity.Inventario;

@Service
public class InventariosServiceImpl implements IInventarioService {

	@Autowired
	private IInventarioDao inventarioDao;

	@Override
	@Transactional(readOnly = true)
	public List<Inventario> findAll() {
		return (List<Inventario>) inventarioDao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Inventario> findAll(Pageable pageable) {
		return inventarioDao.findAll(pageable);
	}

	@Override
	@Transactional(readOnly = true)
	public Inventario findById(Long id) {
		return inventarioDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public Inventario save(Inventario inventario) {
		return inventarioDao.save(inventario);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		inventarioDao.deleteById(id);

	}

	@Override
	@Transactional
	public List<Inventario> obtenerInventariosPorProducto(Producto producto) {
		return inventarioDao.obtenerPorProducto(producto);
	}

	@Override
	public List<Inventario> obtenerInventariosPorProductoAlmacen(Producto producto, Almacenamiento almacen) {
		return inventarioDao.obtenerPorProducto(producto, almacen);
	}

}
