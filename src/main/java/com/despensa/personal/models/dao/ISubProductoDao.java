package com.despensa.personal.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.despensa.personal.models.entity.Producto;
import com.despensa.personal.models.entity.SubProducto;

public interface ISubProductoDao extends JpaRepository<SubProducto, Long>{

	@Query("Select s from SubProducto s WHERE s.producto = :producto")
	List<SubProducto> obtenerPorProducto(Producto producto);

}
