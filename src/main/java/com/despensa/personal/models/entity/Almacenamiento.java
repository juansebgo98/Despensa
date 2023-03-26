package com.despensa.personal.models.entity;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.*;

@Entity
@Table(name = "almacenamientos")
public class Almacenamiento implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "lugar")
    private String lugar;

    @OneToMany(mappedBy = "almacenamiento", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference(value = "inventarioAlmacenamiento")
    private List<Inventario> inventarios = new ArrayList<>();

	public Almacenamiento(Long id, String nombre, String lugar, List<Inventario> inventarios) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.lugar = lugar;
		this.inventarios = inventarios;
	}

	public Almacenamiento() {
		super();
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

	public List<Inventario> getInventarios() {
		return inventarios;
	}

	public void setInventarios(List<Inventario> inventarios) {
		this.inventarios = inventarios;
	}
   
    
}