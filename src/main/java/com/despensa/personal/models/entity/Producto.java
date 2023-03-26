package com.despensa.personal.models.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "productos")
public class Producto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "imagen", nullable = false)
    private String imagen;

    @Column(name = "nombre")
    private String nombre;

    @OneToMany(mappedBy = "producto", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference(value = "inventarioProducto")
    private List<Inventario> inventarios = new ArrayList<>();

    public Producto() {
    	super();
	}    
    
	public Producto(Long id,
			@NotEmpty(message = "no puede estar vacio") @Size(min = 2, max = 50, message = "el tamaño tiene que estar entre 4 y 12") String nombre,
			@NotEmpty(message = "no puede estar vacio") String imagen, List<Inventario> inventarios) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.imagen = imagen;
		this.inventarios = inventarios;
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

	public String getImagen() {
		return imagen;
	}

	public void setImagen(String imagen) {
		this.imagen = imagen;
	}

	public List<Inventario> getInventarios() {
		return inventarios;
	}

	public void setInventarios(List<Inventario> inventarios) {
		this.inventarios = inventarios;
	}
    
	
}
