package com.despensa.personal.models.entity;

import java.io.Serializable;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;

@Entity
@Table(name = "tiendas")
public class Tienda implements Serializable {

	private static final long serialVersionUID = 5069802593955285527L;

	@Id
	@Column(name = "id")
	private Long id;

	@Column(name = "nombre")
	private String nombre;

	@OneToMany(mappedBy = "tienda", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Producto> productos;

	public Tienda() {

	}

	public Tienda(Long id, @NotEmpty(message = "no puede estar vacio") String nombre) {
		this.id = id;
		this.nombre = nombre;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

}
