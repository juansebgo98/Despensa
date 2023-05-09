package com.despensa.personal.models.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import com.despensa.personal.models.entity.Tienda;

public interface ITiendaDao extends JpaRepository<Tienda, Long> {

}
