package com.ronaldocarvalho.cursomc.resources;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ronaldocarvalho.cursomc.domain.Cliente;
import com.ronaldocarvalho.cursomc.services.ClienteService;

//Camada Rest
@RestController
@RequestMapping("/clientes")
public class ClienteResource {
	// Acesso a camada Servico
	@Autowired
	private ClienteService service;

	// Requisicao do id a ser buscado pelo metodo get
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Cliente> find(@PathVariable Integer id) {
			
		Cliente obj = service.find(id);
		return ResponseEntity.ok().body(obj);

	}
	
}
