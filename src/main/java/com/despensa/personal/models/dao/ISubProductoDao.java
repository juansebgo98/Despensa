package com.despensa.personal.models.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import com.despensa.personal.models.entity.SubProducto;

public interface ISubProductoDao extends JpaRepository<SubProducto, Long>{

}
