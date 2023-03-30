package com.despensa.personal.controllers;

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

import com.despensa.personal.models.entity.Producto;
import com.despensa.personal.models.entity.Almacenamiento;
import com.despensa.personal.models.entity.Inventario;
import com.despensa.personal.models.services.IProductoService;
import com.despensa.personal.models.services.IInventarioService;

import jakarta.validation.Valid;

@CrossOrigin(origins = {"http://localhost:4200"})
//@CrossOrigin(origins = {"http://192.168.18.158:4200"})
@RestController
@RequestMapping("/api")
public class InventarioRestController {
	
	@Autowired
	private IInventarioService inventarioService;
	@Autowired
	private IProductoService productoService;
	
	@GetMapping("/inventarios")
	public List<Inventario> index(){
		return inventarioService.findAll();
	}
	
	@GetMapping("/inventarios/page/{page}")
	public Page<Inventario> index(@PathVariable Integer page){
		return inventarioService.findAll(PageRequest.of(page, 5));
	}
	
	@GetMapping("/inventarios/{id}")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<?> show(@PathVariable Long id) {
		Inventario inventario = null;
		Map<String, Object> response = new HashMap<>();
		try {
			inventario = inventarioService.findById(id);			
		}catch (DataAccessException e) {
			response.put("mensaje", "Error al consultar la base de datos");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		if(inventario == null){
			response.put("mensaje", "El inventario ID:".concat(id.toString().concat(" no existe en la base de datos")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Inventario>(inventario, HttpStatus.OK);
	}
	
	@GetMapping("/inventarios/producto/{id}")
	public ResponseEntity<?> buscarPorProducto(@PathVariable Long id){
		Map<String, Object> response = new HashMap<>();
		Producto producto = new Producto();
		producto.setId(id);
		List<Inventario> lista = inventarioService.obtenerInventariosPorProducto(producto);
		if(lista == null){
			response.put("mensaje", "No se ha podido recuperar productos del almacenamiento ID:".concat(id.toString()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<Inventario>>(lista, HttpStatus.OK);
	}
	
	@GetMapping("/inventarios/producto/{idPro}/almacenamiento/{idAl}")
	public ResponseEntity<?> buscarPorProductoAlmacenamiento(@PathVariable Long idPro, @PathVariable Long idAl){
		Map<String, Object> response = new HashMap<>();
		Producto producto = new Producto();
		producto.setId(idPro);
		Almacenamiento almacen = new Almacenamiento();
		almacen.setId(idAl);
		List<Inventario> lista = inventarioService.obtenerInventariosPorProductoAlmacen(producto,almacen);
		if(lista == null){
			response.put("mensaje", "No se ha podido recuperar productos del almacenamiento ID:".concat(idPro.toString()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<Inventario>>(lista, HttpStatus.OK);
	}
	
	@PostMapping("/inventarios")
	public ResponseEntity<?> create(@Valid @RequestBody Inventario inventario, BindingResult result) {
		
		Inventario inventarioNew = null;
		Map<String, Object> response = new HashMap<>();
		
		if(result.hasErrors()) {

			List<String> errors = result.getFieldErrors()
					.stream()
					.map(err -> "El campo '" + err.getField() +"' "+ err.getDefaultMessage())
					.collect(Collectors.toList());
			
			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
		if (inventario.getProducto() == null || inventario.getProducto().getId() == null) {
	        response.put("mensaje", "El campo Producto no se ha encontrado en la base de dato o es null");
	        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
	    }else {
	    	 Producto producto = productoService.findById(inventario.getProducto().getId());
	 	    inventario.setProducto(producto);
	    }
		try {
			inventarioNew = inventarioService.save(inventario);
			
		} catch(DataAccessException e) {
			response.put("mensaje", "Error al realizar el insert en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", "El inventario ha sido creado con éxito!");
		response.put("inventario", inventarioNew);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}
	
	@PutMapping("/inventarios/{id}")
	public ResponseEntity<?> update(@Valid @RequestBody Inventario inventario, BindingResult result, @PathVariable Long id) {
		Inventario inventarioActual = inventarioService.findById(id);
		Map<String, Object> response = new HashMap<>();
		if(result.hasErrors()) {
			List<String> errors = result.getFieldErrors().stream().map(err->"El campo '"+err.getField()+"' "+err.getDefaultMessage()).collect(Collectors.toList());
			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
		if(inventarioActual==null) {
			response.put("mensaje", "Error al actualizar el inventario, no existe en la base de datos");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		Inventario productoActualizado=null;
		try {
//			inventarioActual.setFechaCaducidad(inventario.getFechaCaducidad());
//			inventarioActual.setProducto(inventario.getProducto());
//			inventarioActual.setAlmacenamiento(inventario.getAlmacenamiento());
//			inventarioActual.setCantidad(inventario.getCantidad());
//			inventarioActual.setId(id);
//			inventarioActual.setPrecio(inventario.getPrecio());
			
			inventario.setId(id);
			if(inventario.getAlmacenamiento()== null) {
				inventario.setAlmacenamiento(inventarioActual.getAlmacenamiento());
			}
			if(inventario.getProducto()== null) {
				inventario.setProducto(inventarioActual.getProducto());
			}
//			productoActualizado = inventarioService.save(inventarioActual);		
			productoActualizado = inventarioService.save(inventario);		
			
		}catch (DataAccessException e) {
			response.put("mensaje", "Error al actualizar el inventario en  la base de datos");
			response.put("error", e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", "El inventario ha sido actualizado con exito");
		response.put("inventario", productoActualizado);
		return new ResponseEntity<Map<String,Object>>(response,HttpStatus.CREATED);
	}
	
	@PutMapping("/inventarios/{id}/aumentar/{cantidad}")
	public ResponseEntity<?> aumentarCantidad(@PathVariable Long id, @PathVariable Integer cantidad) {
		Inventario inventarioActual = inventarioService.findById(id);
		Map<String, Object> response = new HashMap<>();
		
		if(inventarioActual==null) {
			response.put("mensaje", "Error al actualizar el inventario, no existe en la base de datos");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		Inventario inventarioActualizado=null;
		try {
			inventarioActual.setCantidad(inventarioActual.getCantidad()+cantidad);
			inventarioActual.setId(id);
			
			
			inventarioActualizado = inventarioService.save(inventarioActual);		
			
		}catch (DataAccessException e) {
			response.put("mensaje", "Error al actualizar el inventario en  la base de datos");
			response.put("error", e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", "El inventario ha sido actualizado con exito");
		response.put("inventario", inventarioActualizado);
		return new ResponseEntity<Map<String,Object>>(response,HttpStatus.CREATED);
	}
	
	@PutMapping("/inventarios/{id}/reducir/{cantidad}")
	public ResponseEntity<?> reducirCantidad( @PathVariable Long id,@PathVariable Integer cantidad) {
		Inventario inventarioActual = inventarioService.findById(id);
		Map<String, Object> response = new HashMap<>();
		
		if(inventarioActual==null) {
			response.put("mensaje", "Error al actualizar el inventario, no existe en la base de datos");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		Inventario inventarioActualizado=null;
		try {
			if(inventarioActual.getCantidad()-cantidad >= 0) {
				inventarioActual.setCantidad(inventarioActual.getCantidad()-cantidad);
				inventarioActual.setId(id);
								
				inventarioActualizado = inventarioService.save(inventarioActual);		
				
			}else {
				response.put("mensaje", "La cantidad no puede ser negativa: Valor actual "+inventarioActual.getCantidad());
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			
		}catch (DataAccessException e) {
			response.put("mensaje", "Error al actualizar el inventario en  la base de datos");
			response.put("error", e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", "El inventario ha sido actualizado con exito");
		response.put("inventario", inventarioActualizado);
		return new ResponseEntity<Map<String,Object>>(response,HttpStatus.CREATED);
	}
	
	@DeleteMapping("/inventarios/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		Map<String, Object> response = new HashMap<>();
		Inventario sub = inventarioService.findById(id);

		try {
			if(sub == null){
				response.put("mensaje", "No se ha encontrado sub producto "+id+" en la base de datos");
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			inventarioService.delete(id);
			
		}catch (DataAccessException e) {
			response.put("mensaje", "Error al borrar el inventario en  la base de datos");
			response.put("error", e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
		response.put("mensaje", "Se ha borrar el inventario "+sub.getId()+" en la base de datos");
		return new ResponseEntity<Map<String, Object>>(response,HttpStatus.OK);
	}

}
