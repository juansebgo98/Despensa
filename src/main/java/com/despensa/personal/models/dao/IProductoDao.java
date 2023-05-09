package com.despensa.personal.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.despensa.personal.models.entity.Producto;

public interface IProductoDao extends JpaRepository<Producto, Long> {

	@Query("SELECT p FROM Producto p INNER JOIN p.inventarios i WHERE i.almacenamiento.id = :almacenamientoId")
	List<Producto> consultarProductosPorAlmacenamiento(@Param("almacenamientoId") Long almacenamiento_id);

	@Query("SELECT p FROM Producto p INNER JOIN p.tienda t WHERE t.id = :tiendaId")
	List<Producto> consultarProductosPorTienda(@Param("tiendaId") Long almacenamiento_id);

}
