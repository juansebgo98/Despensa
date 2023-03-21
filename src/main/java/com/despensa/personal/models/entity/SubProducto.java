package com.despensa.personal.models.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name="sub_productos")
public class SubProducto implements Serializable{

    private static final long serialVersionUID = -5805849772705210750L;

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name="fecha_caducidad")
    private LocalDate fechaCaducidad;

    @NotNull
    private BigDecimal precio;

    @ManyToOne
    @JoinColumn(name = "producto_id", referencedColumnName = "id")
    @JsonBackReference
    private Producto producto;

	public SubProducto(Long id, @NotNull LocalDate fechaCaducidad, @NotNull BigDecimal precio, Producto producto) {
		super();
		this.id = id;
		this.fechaCaducidad = fechaCaducidad;
		this.precio = precio;
		this.producto = producto;
	}

	public SubProducto() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDate getFechaCaducidad() {
		return fechaCaducidad;
	}

	public void setFechaCaducidad(LocalDate fechaCaducidad) {
		this.fechaCaducidad = fechaCaducidad;
	}

	public BigDecimal getPrecio() {
		return precio;
	}

	public void setPrecio(BigDecimal precio) {
		this.precio = precio;
	}

	public Producto getProducto() {
		return producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}
    
    
}
