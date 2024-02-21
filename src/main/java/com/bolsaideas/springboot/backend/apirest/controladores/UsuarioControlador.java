package com.bolsaideas.springboot.backend.apirest.controladores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.bolsaideas.springboot.backend.apirest.modelos.entidades.Usuario;
import com.bolsaideas.springboot.backend.apirest.modelos.servicios.IUsuarioServicio;

@RestController
@RequestMapping("/api/users")
public class UsuarioControlador {

	@Autowired
	private IUsuarioServicio usuarioServicio;

	@GetMapping("/ping")
	public void pruebas() {
		System.out.println("ping");
	}

	@GetMapping
	public List<Usuario> listar() {
		return usuarioServicio.traeTodos();
	}


	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping
	public ResponseEntity<Usuario> create(@RequestBody Usuario usuario){
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(usuarioServicio.guardar(usuario));
	}

	@PostMapping("/registrar")
	public ResponseEntity<Usuario> registrar(@RequestBody Usuario usuario){
		usuario.setAdmin(false);
		return create(usuario);
	}
}
