package com.bolsaideas.springboot.backend.apirest.modelos.servicios;

import java.util.List;

import com.bolsaideas.springboot.backend.apirest.modelos.entidades.Usuario;
import org.springframework.stereotype.Service;

@Service
public interface IUsuarioServicio {

	List<Usuario> traeTodos();
	
	Usuario guardar(Usuario usuario); 
}
