package com.despensa.personal.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.despensa.personal.models.entity.Almacenamiento;
import com.despensa.personal.models.entity.Producto;
import com.despensa.personal.models.services.IProductoService;

import jakarta.validation.Valid;
@CrossOrigin(origins = {"http://localhost:4200"})
//@CrossOrigin(origins = {"http://192.168.18.158:4200"})
@RestController
@RequestMapping("/api")
public class ProductoRestController {
	
	@Autowired
	private IProductoService productoService;
	
	/**
	 * Obtenemos todos los productos
	 * @return
	 */
	@GetMapping("/productos")
	public List<Producto> index(){
		return productoService.findAll();
	}
	
	@GetMapping("/productos/test")
	public List<Producto> test(){
		List<Producto> test = new ArrayList<>();
		Producto p = new Producto();
		
		p.setId(1L);
		p.setNombre("Test");
		
		test.add(p);
		return test;
	}
	
	/**
	 * Obtenemos la lista de productos paginada 
	 * @param page numero de pagina
	 * @return
	 */
	@GetMapping("/productos/page/{page}")
	public Page<Producto> index(@PathVariable Integer page){
		return productoService.findAll(PageRequest.of(page, 5));
	}
	
	/**
	 * Obtenemos todos los productos de un almacenamiento
	 * @param id Id del almacenamiento del que queremos obtener la lista de productos
	 * @return
	 */
	@GetMapping("/productos/almacenamiento/{id}")
	public ResponseEntity<?> index(@PathVariable Long id){
		Map<String, Object> response = new HashMap<>();
		Almacenamiento almacenamiento = new Almacenamiento();
		almacenamiento.setId(id);
		List<Producto> listaProductos = productoService.obtenerProductosAlmacenamiento(almacenamiento);
		
		if(listaProductos == null){
			response.put("mensaje", "No se ha podido recuperar productos del almacenamiento ID:".concat(id.toString()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<Producto>>(listaProductos, HttpStatus.OK);
	}
	
	/**
	 * Obtener producto po rID
	 * @param id
	 * @return
	 */
	@GetMapping("/productos/{id}")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<?> show(@PathVariable Long id) {
		Producto producto = null;
		Map<String, Object> response = new HashMap<>();
		try {
			producto = productoService.findById(id);			
		}catch (DataAccessException e) {
			response.put("mensaje", "Error al consultar la base de datos");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		if(producto == null){
			response.put("mensaje", "El producto ID:".concat(id.toString().concat(" no existe en la base de datos")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Producto>(producto, HttpStatus.OK);
	}
	
	/**
	 * Creamos un producto
	 * @param producto producto a crear
	 * @param result
	 * @return
	 */
	@PostMapping("/productos")
	public ResponseEntity<?> create(@Valid @RequestBody Producto producto, BindingResult result) {

		Producto productoNew = null;
		Map<String, Object> response = new HashMap<>();
		
		if(result.hasErrors()) {

			List<String> errors = result.getFieldErrors()
					.stream()
					.map(err -> "El campo '" + err.getField() +"' "+ err.getDefaultMessage())
					.collect(Collectors.toList());
			
			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}

		try {
			productoNew = productoService.save(producto);
		} catch(DataAccessException e) {
			response.put("mensaje", "Error al realizar el insert en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", "El producto ha sido creado con éxito!");
		response.put("producto", productoNew);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}
	
	/**
	 * Actualizar producto en la base de datos 
	 * @param producto
	 * @param result
	 * @param id
	 * @return
	 */
	@PutMapping("/productos/{id}")
	public ResponseEntity<?> update(@Valid @RequestBody Producto producto, BindingResult result, @PathVariable Long id) {
		Producto productoActual = productoService.findById(id);
		Map<String, Object> response = new HashMap<>();
		if(result.hasErrors()) {
			List<String> errors = result.getFieldErrors().stream().map(err->"El campo '"+err.getField()+"' "+err.getDefaultMessage()).collect(Collectors.toList());
			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
		if(productoActual==null) {
			response.put("mensaje", "Error al actualizar el producto, no existe en la base de datos");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		Producto productoActualizado=null;
		try {
//			productoActual.setNombre(producto.getNombre());
//			productoActual.setCantidad(producto.getCantidad());
//			productoActual.setInventarios(producto.getInventarios());
			
			producto.setId(id);
			
			productoActualizado = productoService.save(producto);			
		}catch (DataAccessException e) {
			response.put("mensaje", "Error al actualizar el producto en  la base de datos");
			response.put("error", e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", "El producto ha sido actualizado con exito");
		response.put("producto", productoActualizado);
		return new ResponseEntity<Map<String,Object>>(response,HttpStatus.CREATED);
	}
	
	@DeleteMapping("/productos/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		Map<String, Object> response = new HashMap<>();

		try {
			productoService.delete(id);
		}catch (DataAccessException e) {
			response.put("mensaje", "Error al borrar el producto en  la base de datos");
			response.put("error", e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
		response.put("mensaje", "Se ha borrar el producto en la base de datos");
		return new ResponseEntity<Map<String, Object>>(response,HttpStatus.OK);
	}

}
