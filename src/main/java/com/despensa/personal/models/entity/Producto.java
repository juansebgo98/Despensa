package com.despensa.personal.models.entity;

import java.io.Serializable;
import java.util.List;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name="productos")
public class Producto implements Serializable{

	private static final long serialVersionUID = -6590703761721298027L;

	@Id
	@Column(name = "id")
    protected Long id;
    
    @NotEmpty(message ="no puede estar vacio")
	@Size(min=2, max=50, message="el tamaño tiene que estar entre 4 y 12")
	@Column(nullable=false)
    protected String nombre;
    
	@Column(name = "imagen", columnDefinition = "TEXT")
	@NotEmpty(message ="no puede estar vacio")
    private String imagen;
    
	@NotEmpty(message ="no puede estar vacio")
    private String descripcion;
	
    @ManyToMany(mappedBy = "productos")
    private List<Inventario> inventarios;

    public Producto() {
    	super();
	}    
    
	public Producto(Long id,
			@NotEmpty(message = "no puede estar vacio") @Size(min = 2, max = 50, message = "el tamaño tiene que estar entre 4 y 12") String nombre,
			@NotEmpty(message = "no puede estar vacio") String imagen,
			@NotEmpty(message = "no puede estar vacio") String descripcion, List<Inventario> inventarios) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.imagen = imagen;
		this.descripcion = descripcion;
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

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public List<Inventario> getInventarios() {
		return inventarios;
	}

	public void setInventarios(List<Inventario> inventarios) {
		this.inventarios = inventarios;
	}
    
	
}
