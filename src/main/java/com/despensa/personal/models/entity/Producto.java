package com.despensa.personal.models.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name="productos")
public class Producto implements Serializable{

	private static final long serialVersionUID = -6590703761721298027L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;
    
    @NotEmpty(message ="no puede estar vacio")
	@Size(min=2, max=50, message="el tamaño tiene que estar entre 4 y 12")
	@Column(nullable=false)
    protected String nombre;
    
	@NotEmpty(message ="no puede estar vacio")
	protected Integer cantidad;

	@Column(name = "imagen", columnDefinition = "TEXT")
	@NotEmpty(message ="no puede estar vacio")
    private String imagen;
    
	@NotEmpty(message ="no puede estar vacio")
    private String descripcion;
	
	@OneToMany(mappedBy = "producto", cascade = CascadeType.ALL)
	private List<SubProducto> subProductos = new ArrayList<>();
	
   
    @ManyToOne
    private Almacenamiento almacenamiento;
    
	
	
	public Producto() {
		super();
	}

	public Producto(Long id,
			@NotEmpty(message = "no puede estar vacio") @Size(min = 2, max = 50, message = "el tamaño tiene que estar entre 4 y 12") String nombre,
			@NotEmpty(message = "no puede estar vacio") Integer cantidad,
			@NotEmpty(message = "no puede estar vacio") String imagen,
			@NotEmpty(message = "no puede estar vacio") String descripcion, List<SubProducto> subProductos,
			Almacenamiento almacenamiento) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.cantidad = cantidad;
		this.imagen = imagen;
		this.descripcion = descripcion;
		this.subProductos = subProductos;
		this.almacenamiento = almacenamiento;
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


	public Almacenamiento getAlmacenamiento() {
		return almacenamiento;
	}

	public void setAlmacenamiento(Almacenamiento almacenamiento) {
		this.almacenamiento = almacenamiento;
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

	public Integer getCantidad() {
		return cantidad;
	}

	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}

	public List<SubProducto> getSubProductos() {
		return subProductos;
	}

	public void setSubProductos(List<SubProducto> subProductos) {
		this.subProductos = subProductos;
	}

	
}
