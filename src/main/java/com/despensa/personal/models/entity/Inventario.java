package com.despensa.personal.models.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "inventario")
public class Inventario implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "almacenamiento_id")
    @JsonBackReference(value = "inventarioAlmacenamiento")
    private Almacenamiento almacenamiento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "producto_id")
    @JsonBackReference(value = "inventarioProducto")
    private Producto producto;

    @Column(name = "cantidad")
    private Integer cantidad;

    @Column(name = "precio", nullable = false)
    private BigDecimal precio;

    @Column(name = "fecha_caducidad")
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
