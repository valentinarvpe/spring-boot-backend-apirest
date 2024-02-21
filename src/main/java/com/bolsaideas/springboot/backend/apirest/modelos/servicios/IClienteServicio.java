package com.bolsaideas.springboot.backend.apirest.modelos.servicios;

import java.util.List;

import com.bolsaideas.springboot.backend.apirest.modelos.entidades.Cliente;

public interface IClienteServicio {

	public List<Cliente> traerClientes();
	
	public Cliente traerPorId(Long id);
	
	public Cliente guardar(Cliente cliente);
	
	public void eliminar(Long id);
	
	
}
