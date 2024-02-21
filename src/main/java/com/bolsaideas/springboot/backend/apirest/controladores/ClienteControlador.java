package com.bolsaideas.springboot.backend.apirest.controladores;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
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

import com.bolsaideas.springboot.backend.apirest.modelos.entidades.Cliente;
import com.bolsaideas.springboot.backend.apirest.modelos.servicios.IClienteServicio;

import jakarta.validation.Valid;

@CrossOrigin(origins = "http://localhost:4200", originPatterns = "*")
@RestController
@RequestMapping("/api")
public class ClienteControlador {

	@Autowired
	private IClienteServicio clienteServicio;

	@GetMapping
	public void ping() {
		System.out.println("Hola mundo");
	}

	@GetMapping("/clientes")
	public List<Cliente> index(){
		return clienteServicio.traerClientes();
	}
	
	@GetMapping("/clientes/{id}")
	public ResponseEntity<?> traerCliente(@PathVariable Long id) {
		Cliente cliente = null;
		//Map es la intrfaz y hashmap es la implementacion
		Map<String, Object> resp = new HashMap<>(); 
		
		try {
			cliente = clienteServicio.traerPorId(id);
		} catch (DataAccessException e) {
			resp.put("mensaje", "Error al realizar la consulta");
			resp.put("error", e.getMessage()
					.concat(" : ").concat(e.getMostSpecificCause().getMessage()) );
			return new ResponseEntity<Map<String, Object>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
		}		
		if (cliente == null) {
			resp.put("mensaje", "El cliente ID:".concat(id.toString().
					concat(" no existe en la base de datos")));
			return new ResponseEntity<Map<String, Object>>(resp, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Cliente>(cliente, HttpStatus.OK);
	}
	
	@PostMapping("/clientes")
	public ResponseEntity<?> crear(@Valid @RequestBody Cliente cliente, BindingResult result) {
		Cliente clienteGuardado = null;
		Map<String, Object> resp = new HashMap<>();
		if (result.hasErrors()) {
			List<String> errores = result.getFieldErrors().stream()
					.map(err -> {
						return "El campo '" +  err.getField() + "' "  + err.getDefaultMessage();
					})
					.collect(Collectors.toList());
			resp.put("errores", errores);
			return new ResponseEntity<Map<String, Object>>(resp, HttpStatus.BAD_REQUEST);
		}
		try {
			cliente.setCreateAt(new Date());	
			clienteGuardado = clienteServicio.guardar(cliente);
		} catch (DataAccessException e) {
			resp.put("mensaje", "No fue posible realizar la transacción");
			resp.put("error", e.getMessage().concat(" : ")
					.concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		if (clienteGuardado == null) {
			resp.put("mensaje", "No se pudo crear el cliente");
			return new ResponseEntity<Map<String, Object>>(resp, HttpStatus.CONFLICT);
		}
		resp.put("mensaje", "El cliente ha sido creado con exito");
		resp.put("cliente", clienteGuardado);
		return new ResponseEntity<Map<String, Object>>(resp, HttpStatus.CREATED);
	}
	
	@PutMapping("/clientes/{id}")
	public ResponseEntity<?> actualizar(@Valid @RequestBody Cliente cliente, BindingResult result, @PathVariable Long id) {
		Cliente clienteActual = clienteServicio.traerPorId(id);
		Cliente clienteActualizado = null;
		Map<String, Object> resp = new HashMap<>();
		if (clienteActual == null) {
			resp.put("mensaje", "Error, No se pudo actualizar, El cliente ID: ".concat(id.toString())
					.concat(" No existe"));
			return new ResponseEntity<Map<String, Object>>(resp, HttpStatus.NOT_FOUND);
		}
		try {
			clienteActual.setApellido(cliente.getApellido());
			clienteActual.setNombre(cliente.getNombre());
			clienteActual.setEmail(cliente.getEmail());
			clienteActual.setCreateAt(cliente.getCreateAt());
			clienteActualizado = clienteServicio.guardar(clienteActual);	
		} catch (DataAccessException e) {
			resp.put("mensaje", "No fue posible realizar la transacción");
			resp.put("error", e.getMessage().concat(" : ")
					.concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);	
		}
		resp.put("mensaje", "El cliente ha sido actualizado con exito");
		resp.put("cliente", clienteActualizado);
		return new ResponseEntity<Map<String, Object>>(resp, HttpStatus.CREATED);
	}
	
	@DeleteMapping("/clientes/{id}")
	public ResponseEntity<?> eliminar(@PathVariable Long id) {
		Map<String, Object> resp = new HashMap<>();
		Cliente cliente = clienteServicio.traerPorId(id);
		if (cliente == null) {
			resp.put("mensaje", "Error, No se pudo actualizar, El cliente ID: ".concat(id.toString())
					.concat(" No existe"));
			return new ResponseEntity<Map<String, Object>>(resp, HttpStatus.NOT_FOUND);
		}
		try {
			clienteServicio.eliminar(id);
		} catch (DataAccessException e) {
			resp.put("mensaje", "No fue posible eliminar el cliente");
			resp.put("error", e.getMessage().concat(" : ")
					.concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);	
		}
		resp.put("mensaje", "El cliente ha sido eliminado con exito");
		return new ResponseEntity<Map<String, Object>>(resp, HttpStatus.OK); 
	}
}
