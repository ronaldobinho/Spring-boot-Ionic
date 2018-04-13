package com.ronaldocarvalho.cursomc.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.ronaldocarvalho.cursomc.domain.Categoria;
import com.ronaldocarvalho.cursomc.repositoiries.CategoriaRepository;
import com.ronaldocarvalho.cursomc.services.exceptions.DataIntegrityExpetion;
import com.ronaldocarvalho.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class CategoriaService {
	// Acesso a camada de repository
	@Autowired
	private CategoriaRepository repo;

	public Categoria find(Integer id) {
		Categoria obj = repo.findOne(id);
		// Tratamento de excessao
		if (obj == null) {
			throw new ObjectNotFoundException(
					"Objeto não encontrado! ID: " + id + ", Tipo: " + Categoria.class.getName());

		}
		return obj;

	}

	public Categoria insert(Categoria obj) {
		// Seta o id como nulo para garantir que é uma insersao
		obj.setId(null);

		return repo.save(obj);

	}

	public Categoria update(Categoria obj) {
		find(obj.getId());
		return repo.save(obj);
	}

	public void delete(Integer id) {
		find(id);
		try {
			repo.delete(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityExpetion("Nao é possivel deletar uma categoria que possui produtos");
		}
	}
	
	public List<Categoria> findAll(){
		return repo.findAll();
		
	}
	
}
