package com.ronaldocarvalho.cursomc.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ronaldocarvalho.cursomc.domain.Cidade;
import com.ronaldocarvalho.cursomc.repositoiries.CidadeRespository;

@Service
public class CidadeService {

	@Autowired
	private CidadeRespository repo;

	public List<Cidade> findByEstado(Integer estadoId) {
		return repo.findCidades(estadoId);
	}

}
