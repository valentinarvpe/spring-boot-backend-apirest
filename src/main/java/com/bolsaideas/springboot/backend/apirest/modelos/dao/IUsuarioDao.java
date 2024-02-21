package com.bolsaideas.springboot.backend.apirest.modelos.dao;

import org.springframework.data.repository.CrudRepository;

import com.bolsaideas.springboot.backend.apirest.modelos.entidades.Usuario;

import java.util.Optional;

public interface IUsuarioDao extends CrudRepository<Usuario, Long> {

    boolean existsByNombreUsuario(String nombreUsuario);

    Optional<Usuario> findByNombreUsuario(String nombreUsuario);
}
