package com.ronaldocarvalho.cursomc.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ronaldocarvalho.cursomc.domain.Cliente;
import com.ronaldocarvalho.cursomc.repositoiries.ClienteRepository;
import com.ronaldocarvalho.cursomc.services.exceptions.ObjectNotFoundException;
 
@Service
public class ClienteService {
	// Acesso a camada de repository
	@Autowired
	private ClienteRepository repo;

	public Cliente buscar(Integer id) {
		Cliente obj = repo.findOne(id);
//Tratamento de excessao
		if (obj == null) {
			throw new ObjectNotFoundException(
					"Objeto não encontrado! ID: " + id + ", Tipo: " + Cliente.class.getName());

		}
		return obj;

	}
}
