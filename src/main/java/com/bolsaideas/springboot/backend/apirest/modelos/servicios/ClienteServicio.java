package com.bolsaideas.springboot.backend.apirest.modelos.servicios;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bolsaideas.springboot.backend.apirest.modelos.dao.IClienteDao;
import com.bolsaideas.springboot.backend.apirest.modelos.entidades.Cliente;

@Service
public class ClienteServicio implements IClienteServicio{

	@Autowired
	private IClienteDao clienteDao;
	
	@Override
	@Transactional(readOnly = true )
	public List<Cliente> traerClientes() {
		return (List<Cliente>) clienteDao.findAll();
	}

	@Override
	public Cliente traerPorId(Long id) {
		return clienteDao.findById(id).orElse(null);
	}

	@Override
	public Cliente guardar(Cliente cliente) {
		return clienteDao.save(cliente);
	}
	
	@Override
	public void eliminar(Long id) {
		clienteDao.deleteById(id);
	}

}
