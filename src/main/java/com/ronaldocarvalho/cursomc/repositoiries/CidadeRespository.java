package com.ronaldocarvalho.cursomc.repositoiries;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ronaldocarvalho.cursomc.domain.Cidade;

@Repository
public interface CidadeRespository extends JpaRepository<Cidade, Integer> {

	
}
