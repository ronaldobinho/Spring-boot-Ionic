package com.ronaldocarvalho.cursomc.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.ronaldocarvalho.cursomc.domain.Estado;
import com.ronaldocarvalho.cursomc.repositoiries.EstadoRepository;

@Service
public class EstadoService {
	
	@Autowired
	private EstadoRepository estadoRepository;
	
	public List<Estado> findAll(){
		
		return estadoRepository.findAllByOrderByNome();
		
	}
}
