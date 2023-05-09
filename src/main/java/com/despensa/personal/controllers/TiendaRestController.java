package com.despensa.personal.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
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

import com.despensa.personal.models.entity.Tienda;
import com.despensa.personal.models.services.ITiendaService;

import jakarta.validation.Valid;

//@CrossOrigin(origins = {"http://localhost:4200"})
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api")
public class TiendaRestController {

	@Autowired
	private ITiendaService tiendaService;

	/**
	 * Obtenemos todos los productos
	 * 
	 * @return
	 */
	@GetMapping("/tiendas")
	public List<Tienda> obtenerTodasLasTiendas() {
		return tiendaService.findAll();
	}

	/**
	 * Obtener producto por ID
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping("/tiendas/{id}")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<?> obtenerTiendaPorId(@PathVariable Long id) {
		Tienda tienda = null;
		Map<String, Object> response = new HashMap<>();
		try {
			tienda = tiendaService.findById(id);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al consultar la base de datos");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		if (tienda == null) {
			response.put("mensaje", "La tienda ID:".concat(id.toString().concat(" no existe en la base de datos")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Tienda>(tienda, HttpStatus.OK);
	}

	/**
	 * Creamos un producto
	 * 
	 * @param producto producto a crear
	 * @param result
	 * @return
	 */
	@PostMapping("/tiendas")
	public ResponseEntity<?> crearTienda(@Valid @RequestBody Tienda producto, BindingResult result) {

		Tienda tiendaNew = null;
		Map<String, Object> response = new HashMap<>();

		if (result.hasErrors()) {

			List<String> errors = result.getFieldErrors().stream()
					.map(err -> "El campo '" + err.getField() + "' " + err.getDefaultMessage())
					.collect(Collectors.toList());

			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}

		try {
			tiendaNew = tiendaService.save(producto);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar el insert en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("mensaje", "La tienda ha sido creado con éxito!");
		response.put("producto", tiendaNew);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}

	/**
	 * Actualizar producto en la base de datos
	 * 
	 * @param producto
	 * @param result
	 * @param id
	 * @return
	 */
	@PutMapping("/tiendas/{id}")
	public ResponseEntity<?> actualizarTienda(@Valid @RequestBody Tienda producto, BindingResult result,
			@PathVariable Long id) {
		Tienda productoActual = tiendaService.findById(id);
		Map<String, Object> response = new HashMap<>();
		if (result.hasErrors()) {
			List<String> errors = result.getFieldErrors().stream()
					.map(err -> "El campo '" + err.getField() + "' " + err.getDefaultMessage())
					.collect(Collectors.toList());
			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
		if (productoActual == null) {
			response.put("mensaje", "Error al actualizar la tienda, no existe en la base de datos");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		Tienda productoActualizado = null;
		try {

			producto.setId(id);

			productoActualizado = tiendaService.save(producto);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al actualizar la tienda en  la base de datos");
			response.put("error", e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("mensaje", "La tienda ha sido actualizado con exito");
		response.put("producto", productoActualizado);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}

	@DeleteMapping("/tiendas/{id}")
	public ResponseEntity<?> eliminarTienda(@PathVariable Long id) {
		Map<String, Object> response = new HashMap<>();

		try {
			tiendaService.delete(id);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al borrar la tienda en  la base de datos");
			response.put("error", e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.put("mensaje", "Se ha borrar la tienda en la base de datos");
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}

}
