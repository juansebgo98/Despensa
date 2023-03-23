package com.despensa.personal.models.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name="inventario")
public class Inventario implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="almacenamiento_id", referencedColumnName="id")
    private Almacenamiento almacenamiento;

    @ManyToOne
    @JoinColumn(name="producto_id", referencedColumnName="id")
    private Producto producto;

    @Column(nullable = false)
    private Integer cantidad;

    @NotNull
    private BigDecimal precio;

    @NotNull
    @Column(name="fecha_caducidad")
    private LocalDate fechaCaducidad;

	public Inventario(Long id, Almacenamiento almacenamiento, Producto producto, Integer cantidad,
			@NotNull BigDecimal precio, @NotNull LocalDate fechaCaducidad) {
		super();
		this.id = id;
		this.almacenamiento = almacenamiento;
		this.producto = producto;
		this.cantidad = cantidad;
		this.precio = precio;
		this.fechaCaducidad = fechaCaducidad;
	}

	public Inventario() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Almacenamiento getAlmacenamiento() {
		return almacenamiento;
	}

	public void setAlmacenamiento(Almacenamiento almacenamiento) {
		this.almacenamiento = almacenamiento;
	}

	public Producto getProducto() {
		return producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}

	public Integer getCantidad() {
		return cantidad;
	}

	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}

	public BigDecimal getPrecio() {
		return precio;
	}

	public void setPrecio(BigDecimal precio) {
		this.precio = precio;
	}

	public LocalDate getFechaCaducidad() {
		return fechaCaducidad;
	}

	public void setFechaCaducidad(LocalDate fechaCaducidad) {
		this.fechaCaducidad = fechaCaducidad;
	}


	
}
