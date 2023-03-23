package com.despensa.personal.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.despensa.personal.models.entity.Inventario;
import com.despensa.personal.models.entity.Producto;

public interface IInventarioDao extends JpaRepository<Inventario, Long>{

	@Query("Select s from Inventario s WHERE s.producto = :producto")
	List<Inventario> obtenerPorProducto(Producto producto);

}
