package com.bolsaideas.springboot.backend.apirest.modelos.servicios;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bolsaideas.springboot.backend.apirest.modelos.dao.IRolDao;
import com.bolsaideas.springboot.backend.apirest.modelos.dao.IUsuarioDao;
import com.bolsaideas.springboot.backend.apirest.modelos.entidades.Rol;
import com.bolsaideas.springboot.backend.apirest.modelos.entidades.Usuario;

@Service
public class UsuarioServicioImpl implements IUsuarioServicio{

	@Autowired
	private IUsuarioDao usuarioDao;

	@Autowired
	private IRolDao rolDao;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Override
	@Transactional
	public List<Usuario> traeTodos() {
		return (List<Usuario>) usuarioDao.findAll();
	}

	@Override
	@Transactional
	public Usuario guardar(Usuario usuario) {
		Optional<Rol> OptionalRolUsuario = rolDao.findByNombre("ROLE_USER");
		List<Rol> roles = new ArrayList<>();
		OptionalRolUsuario.ifPresent(roles::add);
		
		if (usuario.isAdmin()) {
			Optional<Rol> optionalRolAdmin =rolDao.findByNombre("ROLE_ADMIN");
			optionalRolAdmin.ifPresent(roles::add);
		}
		
		usuario.setRoles(roles);
		String contrasenaCodificada = passwordEncoder.encode(usuario.getContrasena());
		usuario.setContrasena(contrasenaCodificada);
		return usuarioDao.save(usuario);

	}

}
