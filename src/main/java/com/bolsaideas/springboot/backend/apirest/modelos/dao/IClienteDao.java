package com.bolsaideas.springboot.backend.apirest.modelos.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bolsaideas.springboot.backend.apirest.modelos.entidades.Cliente;

public interface IClienteDao extends JpaRepository<Cliente, Long> {

}
