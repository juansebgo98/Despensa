package com.despensa.personal.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.despensa.personal.models.entity.Almacenamiento;

public interface IAlmacenamientoDao extends JpaRepository<Almacenamiento, Long> {

	@Query("SELECT a FROM Almacenamiento a INNER JOIN Inventario i ON a.id = i.almacenamiento.id WHERE i.producto.id = :productoId")
	public List<Almacenamiento> obtenerAlmacenamientoProducto(@Param("productoId") Long productoId);

	@Query("SELECT a FROM Almacenamiento a INNER JOIN Inventario i ON a.id = i.almacenamiento.id WHERE i.id = :idInventario")
	public List<Almacenamiento> obtenerAlmacenamientoInventario(@Param("idInventario")Long idInventario);

}
