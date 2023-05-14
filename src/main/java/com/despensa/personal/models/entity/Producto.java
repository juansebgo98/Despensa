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

	@Column(name = "cantidad_minima")
	private Integer cantidadMinima;
	
	@Column(name = "lista_compra")
	private Boolean listaCompra;

	@ManyToOne()
	@JoinColumn(name = "tienda")
	private Tienda tienda;

	@OneToMany(mappedBy = "producto", cascade = CascadeType.REMOVE, orphanRemoval = true)
	@JsonManagedReference(value = "inventarioProducto")
	private List<Inventario> inventarios = new ArrayList<>();

	public Producto() {
		super();
	}

	public Producto(Long id,
			@NotEmpty(message = "no puede estar vacio") @Size(min = 2, max = 50, message = "el tamaño tiene que estar entre 4 y 12") String nombre,
			@NotEmpty(message = "no puede estar vacio") String imagen, List<Inventario> inventarios,
			Integer cantidadMinima, Tienda tienda) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.imagen = imagen;
		this.inventarios = inventarios;
		this.cantidadMinima = cantidadMinima;
		this.tienda = tienda;
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

	public int getCantidadMinima() {
		return cantidadMinima;
	}

	public void setCantidadMinima(int cantidadMinima) {
		this.cantidadMinima = cantidadMinima;
	}

	public Tienda getTienda() {
		return tienda;
	}

	public void setTienda(Tienda tienda) {
		this.tienda = tienda;
	}

	public void setCantidadMinima(Integer cantidadMinima) {
		this.cantidadMinima = cantidadMinima;
	}

	public Boolean getListaCompra() {
		return listaCompra;
	}

	public void setListaCompra(Boolean listaCompra) {
		this.listaCompra = listaCompra;
	}

}
