package com.despensa.personal.models.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name="sub_productos")
@JsonIgnoreProperties({"producto"})
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

    @JoinColumn(name = "producto_id")
    @ManyToOne
    private Producto producto;
    
    public SubProducto() {
    	
    }
	

	public LocalDate getFechaCaducidad() {
		return fechaCaducidad;
	}


	public void setFechaCaducidad(LocalDate fechaCaducidad) {
		this.fechaCaducidad = fechaCaducidad;
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public Producto getProducto() {
		return producto;
	}


	public void setProducto(Producto producto) {
		this.producto = producto;
	}


	public BigDecimal getPrecio() {
		return precio;
	}


	public void setPrecio(BigDecimal precio) {
		this.precio = precio;
	}
    
	
}
