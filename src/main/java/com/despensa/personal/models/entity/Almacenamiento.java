package com.despensa.personal.models.entity;


import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;

@Entity
@Table(name="almacenamientos")
public class Almacenamiento implements Serializable{

	private static final long serialVersionUID = 2899027107106949550L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String nombre;
    
    private String lugar;
    
    @JsonIgnore
    @OneToMany(mappedBy = "almacenamiento")
    private List<Producto> productos;
    
    public Almacenamiento() {
    	}
	public Almacenamiento(Long id, String nombre, String lugar, List<Producto> productos) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.lugar = lugar;
		this.productos = productos;
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

	public String getLugar() {
		return lugar;
	}

	public void setLugar(String lugar) {
		this.lugar = lugar;
	}

	public List<Producto> getProductos() {
		return productos;
	}

	public void setProductos(List<Producto> productos) {
		this.productos = productos;
	}

}