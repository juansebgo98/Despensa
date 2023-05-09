package com.despensa.personal.models.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.despensa.personal.models.dao.IProductoDao;
import com.despensa.personal.models.entity.Almacenamiento;
import com.despensa.personal.models.entity.Producto;
import com.despensa.personal.models.entity.Tienda;

@Service
public class ProductosServiceImpl implements IProductoService {

	@Autowired
	private IProductoDao productoDao;

	@Override
	@Transactional(readOnly = true)
	public List<Producto> findAll() {
		return (List<Producto>) productoDao.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Producto> findAll(Pageable pageable) {
		return productoDao.findAll(pageable);
	}

	@Override
	@Transactional(readOnly = true)
	public Producto findById(Long id) {
		return productoDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public Producto save(Producto producto) {
		return productoDao.save(producto);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		productoDao.deleteById(id);

	}

	@Override
	@Transactional
	public List<Producto> obtenerProductosAlmacenamiento(Almacenamiento almacenamiento) {
		return productoDao.consultarProductosPorAlmacenamiento(almacenamiento.getId());

	}

	@Override
	public List<Producto> obtenerProductosTienda(Tienda tienda) {
		return productoDao.consultarProductosPorTienda(tienda.getId());
	}

}
