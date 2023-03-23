package com.despensa.personal.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
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
import com.despensa.personal.models.services.IAlmacenamientoService;

import jakarta.validation.Valid;

@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/api")
public class AlmacenamientoRestController {
	
	@Autowired
	private IAlmacenamientoService almacenamientoService;
	
	/**
	 * Obtenemos todos los almacenamiento
	 * @return
	 */
	@GetMapping("/almacenamiento")
	public List<Almacenamiento> index(){
		return almacenamientoService.findAll();
	}
	
	/**
	 * Obtenemos la lista de almacenamiento paginada 
	 * @param page numero de pagina
	 * @return
	 */
	@GetMapping("/almacenamiento/page/{page}")
	public Page<Almacenamiento> index(@PathVariable Integer page){
		return almacenamientoService.findAll(PageRequest.of(page, 5));
	}
	
	/**
	 * Obtenemos el almacenamiento apartir de un producto
	 * @param id Id del almacenamiento del que queremos obtener la lista de almacenamiento
	 * @return
	 */
	@GetMapping("/almacenamiento/producto/{id}")
	public ResponseEntity<?> index(@PathVariable Long id){
		Map<String, Object> response = new HashMap<>();
		Almacenamiento almacenamiento = almacenamientoService.obtenerAlmacenamientoProducto(id);
		
		if(almacenamiento == null){
			response.put("mensaje", "No se ha podido recuperar almacenamiento del producto ID:".concat(id.toString()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Almacenamiento>(almacenamiento, HttpStatus.OK);
	}
	
	/**
	 * Obtener almacenamiento po rID
	 * @param id
	 * @return
	 */
	@GetMapping("/almacenamiento/{id}")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<?> show(@PathVariable Long id) {
		Almacenamiento almacenamiento = null;
		Map<String, Object> response = new HashMap<>();
		try {
			almacenamiento = almacenamientoService.findById(id);			
		}catch (DataAccessException e) {
			response.put("mensaje", "Error al consultar la base de datos");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		if(almacenamiento == null){
			response.put("mensaje", "El almacenamiento ID:".concat(id.toString().concat(" no existe en la base de datos")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Almacenamiento>(almacenamiento, HttpStatus.OK);
	}
	
	/**
	 * Creamos un almacenamiento
	 * @param almacenamiento almacenamiento a crear
	 * @param result
	 * @return
	 */
	@PostMapping("/almacenamiento")
	public ResponseEntity<?> create(@Valid @RequestBody Almacenamiento almacenamiento, BindingResult result) {

		Almacenamiento almacenamientoNew = null;
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
			almacenamientoNew = almacenamientoService.save(almacenamiento);
		} catch(DataAccessException e) {
			response.put("mensaje", "Error al realizar el insert en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", "El almacenamiento ha sido creado con éxito!");
		response.put("almacenamiento", almacenamientoNew);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}
	
	/**
	 * Actualizar almacenamiento en la base de datos 
	 * @param almacenamiento
	 * @param result
	 * @param id
	 * @return
	 */
	@PutMapping("/almacenamiento/{id}")
	public ResponseEntity<?> update(@Valid @RequestBody Almacenamiento almacenamiento, BindingResult result, @PathVariable Long id) {
		Almacenamiento almacenamientoActual = almacenamientoService.findById(id);
		Map<String, Object> response = new HashMap<>();
		if(result.hasErrors()) {
			List<String> errors = result.getFieldErrors().stream().map(err->"El campo '"+err.getField()+"' "+err.getDefaultMessage()).collect(Collectors.toList());
			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
		if(almacenamientoActual==null) {
			response.put("mensaje", "Error al actualizar el almacenamiento, no existe en la base de datos");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		Almacenamiento productoActualizado=null;
		try {
			almacenamientoActual.setNombre(almacenamiento.getNombre());
			almacenamientoActual.setLugar(almacenamiento.getLugar());
			almacenamientoActual.setProductos(almacenamiento.getProductos());
			// a
			productoActualizado = almacenamientoService.save(almacenamientoActual);			
		}catch (DataAccessException e) {
			response.put("mensaje", "Error al actualizar el almacenamiento en  la base de datos");
			response.put("error", e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", "El almacenamiento ha sido actualizado con exito");
		response.put("almacenamiento", productoActualizado);
		return new ResponseEntity<Map<String,Object>>(response,HttpStatus.CREATED);
	}
	
	@DeleteMapping("/almacenamiento/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		Map<String, Object> response = new HashMap<>();

		try {
			almacenamientoService.delete(id);
		}catch(DataIntegrityViolationException e) {
			response.put("mensaje", "Aun hay productos en el almacenamiento");
			response.put("error", e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}catch (DataAccessException e) {
			response.put("mensaje", "Error al borrar el almacenamiento en  la base de datos");
			response.put("error", e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
		response.put("mensaje", "Se ha borrar el almacenamiento en la base de datos");
		return new ResponseEntity<Map<String, Object>>(response,HttpStatus.OK);
	}

}
