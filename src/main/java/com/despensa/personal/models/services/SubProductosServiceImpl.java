package com.despensa.personal.models.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.despensa.personal.models.dao.ISubProductoDao;
import com.despensa.personal.models.entity.SubProducto;

@Service
public class SubProductosServiceImpl implements ISubProductoService{

	@Autowired
	private ISubProductoDao subProductoDao;
	
	@Override
	@Transactional(readOnly = true)
	public List<SubProducto> findAll() {
		return (List<SubProducto>) subProductoDao.findAll();
	}
	

	@Override
	@Transactional(readOnly = true)
	public Page<SubProducto> findAll(Pageable pageable) {
		return subProductoDao.findAll(pageable);
	}

	@Override
	@Transactional(readOnly = true)
	public SubProducto findById(Long id) {
		return subProductoDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public SubProducto save(SubProducto subProducto) {
		return subProductoDao.save(subProducto);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		subProductoDao.deleteById(id);
		
	}


}
