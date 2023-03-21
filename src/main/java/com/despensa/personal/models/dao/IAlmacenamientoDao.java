package com.despensa.personal.models.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.despensa.personal.models.entity.Almacenamiento;

public interface IAlmacenamientoDao extends JpaRepository<Almacenamiento, Long>{

	@Query("SELECT a FROM Almacenamiento a INNER JOIN a.productos p WHERE p.id = :productoId")
	public Almacenamiento obtenerAlmacenamientoProducto(@Param("productoId") Long productoId);

	
}
