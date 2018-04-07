package com.ronaldocarvalho.cursomc.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ronaldocarvalho.cursomc.domain.Pedido;
import com.ronaldocarvalho.cursomc.repositoiries.PedidoRepository;
import com.ronaldocarvalho.cursomc.services.exceptions.ObjectNotFoundException;
 
@Service
public class PedidoService {
	// Acesso a camada de repository
	@Autowired
	private PedidoRepository repo;

	public Pedido find(Integer id) {
		Pedido obj = repo.findOne(id);
//Tratamento de excessao
		if (obj == null) {
			throw new ObjectNotFoundException(
					"Objeto não encontrado! ID: " + id + ", Tipo: " + Pedido.class.getName());

		}
		return obj;

	}
}
