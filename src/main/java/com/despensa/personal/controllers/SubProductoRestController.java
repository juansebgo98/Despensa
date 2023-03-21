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
import com.despensa.personal.models.entity.SubProducto;
import com.despensa.personal.models.services.IProductoService;
import com.despensa.personal.models.services.ISubProductoService;

import jakarta.validation.Valid;

@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/api")
public class SubProductoRestController {
	
	@Autowired
	private ISubProductoService subProductoService;
	@Autowired
	private IProductoService productoService;
	
	@GetMapping("/subProductos")
	public List<SubProducto> index(){
		return subProductoService.findAll();
	}
	
	@GetMapping("/subProductos/page/{page}")
	public Page<SubProducto> index(@PathVariable Integer page){
		return subProductoService.findAll(PageRequest.of(page, 5));
	}
	
	@GetMapping("/subProductos/{id}")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<?> show(@PathVariable Long id) {
		SubProducto subProducto = null;
		Map<String, Object> response = new HashMap<>();
		try {
			subProducto = subProductoService.findById(id);			
		}catch (DataAccessException e) {
			response.put("mensaje", "Error al consultar la base de datos");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		if(subProducto == null){
			response.put("mensaje", "El subProducto ID:".concat(id.toString().concat(" no existe en la base de datos")));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<SubProducto>(subProducto, HttpStatus.OK);
	}
	
	@GetMapping("/subProductos/producto/{id}")
	public ResponseEntity<?> buscarPorProducto(@PathVariable Long id){
		Map<String, Object> response = new HashMap<>();
		Producto producto = new Producto();
		producto.setId(id);
		List<SubProducto> lista = subProductoService.obtenerSubProductosPorProducto(producto);
		if(lista == null){
			response.put("mensaje", "No se ha podido recuperar productos del almacenamiento ID:".concat(id.toString()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<SubProducto>>(lista, HttpStatus.OK);
	}
	
	@PostMapping("/subProductos")
	public ResponseEntity<?> create(@Valid @RequestBody SubProducto subProducto, BindingResult result) {
	    Producto producto = productoService.findById(subProducto.getProducto().getId());
	    subProducto.setProducto(producto);
		
		SubProducto subProductoNew = null;
		Map<String, Object> response = new HashMap<>();
		
		if(result.hasErrors()) {

			List<String> errors = result.getFieldErrors()
					.stream()
					.map(err -> "El campo '" + err.getField() +"' "+ err.getDefaultMessage())
					.collect(Collectors.toList());
			
			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
		if (subProducto.getProducto() == null || subProducto.getProducto().getId() == null) {
	        response.put("mensaje", "El campo Producto no se ha encontrado en la base de dato o es null");
	        return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
	    }
		try {
			subProductoNew = subProductoService.save(subProducto);
			if(subProductoNew!=null && subProductoNew.getProducto()!=null) {
				Producto productoPrincipal = subProductoNew.getProducto();
				productoPrincipal.setCantidad(productoPrincipal.getCantidad()+1);
				productoService.save(productoPrincipal);
			}
			
		} catch(DataAccessException e) {
			response.put("mensaje", "Error al realizar el insert en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", "El subProducto ha sido creado con éxito!");
		response.put("subProducto", subProductoNew);
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}
	
	@PutMapping("/subProductos/{id}")
	public ResponseEntity<?> update(@Valid @RequestBody SubProducto subProducto, BindingResult result, @PathVariable Long id) {
		SubProducto subProductoActual = subProductoService.findById(id);
		Map<String, Object> response = new HashMap<>();
		if(result.hasErrors()) {
			List<String> errors = result.getFieldErrors().stream().map(err->"El campo '"+err.getField()+"' "+err.getDefaultMessage()).collect(Collectors.toList());
			response.put("errors", errors);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}
		if(subProductoActual==null) {
			response.put("mensaje", "Error al actualizar el subProducto, no existe en la base de datos");
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}
		SubProducto productoActualizado=null;
		try {
			subProductoActual.setFechaCaducidad(subProducto.getFechaCaducidad());
			subProductoActual.setProducto(subProducto.getProducto());
			
			productoActualizado = subProductoService.save(subProductoActual);			
		}catch (DataAccessException e) {
			response.put("mensaje", "Error al actualizar el subProducto en  la base de datos");
			response.put("error", e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("mensaje", "El subProducto ha sido actualizado con exito");
		response.put("subProducto", productoActualizado);
		return new ResponseEntity<Map<String,Object>>(response,HttpStatus.CREATED);
	}
	
	@DeleteMapping("/subProductos/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		Map<String, Object> response = new HashMap<>();
		SubProducto sub = subProductoService.findById(id);

		try {
			if(sub == null){
				response.put("mensaje", "No se ha encontrado sub producto "+id+" en la base de datos");
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
			}
			Producto productoPrincipal = sub.getProducto();
			subProductoService.delete(id);
			productoPrincipal.setCantidad(productoPrincipal.getCantidad()-1);
			productoService.save(productoPrincipal);
			
		}catch (DataAccessException e) {
			response.put("mensaje", "Error al borrar el subProducto en  la base de datos");
			response.put("error", e.getMessage().concat(":").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
		response.put("mensaje", "Se ha borrar el subProducto "+sub.getId()+" en la base de datos");
		return new ResponseEntity<Map<String, Object>>(response,HttpStatus.OK);
	}

}
