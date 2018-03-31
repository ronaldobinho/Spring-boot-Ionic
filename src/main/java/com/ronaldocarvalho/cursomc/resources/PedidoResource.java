package com.ronaldocarvalho.cursomc.resources;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ronaldocarvalho.cursomc.domain.Pedido;
import com.ronaldocarvalho.cursomc.services.PedidoService;

//Camada Rest
@RestController
@RequestMapping("/pedidos")
public class PedidoResource {
	// Acesso a camada Servico
	@Autowired
	private PedidoService service;

	// Requisicao do id a ser buscado pelo metodo get
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> find(@PathVariable Integer id) {
			
		Pedido obj = service.buscar(id);
		return ResponseEntity.ok().body(obj);

	}
	
}
