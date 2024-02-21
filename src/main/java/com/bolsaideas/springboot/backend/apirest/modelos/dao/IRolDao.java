package com.bolsaideas.springboot.backend.apirest.modelos.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import com.bolsaideas.springboot.backend.apirest.modelos.entidades.Rol;

public interface IRolDao extends JpaRepository<Rol, Long> {

	Optional<Rol> findByNombre(String nombre);

}
