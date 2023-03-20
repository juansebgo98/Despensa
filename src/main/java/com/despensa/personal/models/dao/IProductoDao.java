package com.despensa.personal.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.despensa.personal.models.entity.Almacenamiento;
import com.despensa.personal.models.entity.Producto;

public interface IProductoDao extends JpaRepository<Producto, Long>{

	@Query("Select p from Producto p WHERE p.almacenamiento = :almacenamiento")
    List<Producto> consultarProductosPorAlmacenamiento(@Param("almacenamiento") Almacenamiento almacenamiento_id);

	
}
