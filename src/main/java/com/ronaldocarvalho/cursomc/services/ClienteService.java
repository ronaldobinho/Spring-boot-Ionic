package com.ronaldocarvalho.cursomc.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.ronaldocarvalho.cursomc.domain.Cidade;
import com.ronaldocarvalho.cursomc.domain.Cliente;
import com.ronaldocarvalho.cursomc.domain.Endereco;
import com.ronaldocarvalho.cursomc.domain.enums.TipoCliente;
import com.ronaldocarvalho.cursomc.dto.ClienteDTO;
import com.ronaldocarvalho.cursomc.dto.ClienteNewDTO;
import com.ronaldocarvalho.cursomc.repositoiries.CidadeRespository;
import com.ronaldocarvalho.cursomc.repositoiries.ClienteRepository;
import com.ronaldocarvalho.cursomc.repositoiries.EnderecoRepository;
import com.ronaldocarvalho.cursomc.services.exceptions.DataIntegrityExpetion;
import com.ronaldocarvalho.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {
	// Acesso a camada de repository
	@Autowired
	private ClienteRepository repo;

	@Autowired
	private CidadeRespository cidadeRespository;

	@Autowired
	private EnderecoRepository enderecoRepository;

	public Cliente find(Integer id) {
		Cliente obj = repo.findOne(id);
		// Tratamento de excessao
		if (obj == null) {
			throw new ObjectNotFoundException(
					"Objeto não encontrado! ID: " + id + ", Tipo: " + Cliente.class.getName());

		}
		return obj;

	}

	public Cliente insert(Cliente obj) {
		// Seta o id como nulo para garantir que é uma insersao
		obj.setId(null);
		obj = repo.save(obj);
		enderecoRepository.save(obj.getEnderecos());
		return obj;

	}

	public Cliente update(Cliente obj) {
		Cliente newObj = find(obj.getId());
		updateData(newObj, obj);
		return repo.save(newObj);
	}

	public void delete(Integer id) {
		find(id);
		try {
			repo.delete(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityExpetion("Nao é possivel deletar porque há entidades relacionadas");
		}
	}

	public List<Cliente> findAll() {
		return repo.findAll();

	}

	// Paginacao
	public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = new PageRequest(page, linesPerPage, Direction.valueOf(direction), orderBy);

		return repo.findAll(pageRequest);
	}

	public Cliente fromDTO(ClienteDTO objDto) {
		return new Cliente(objDto.getId(), objDto.getNome(), objDto.getEmail(), null, null);

	}

	public Cliente fromDTO(ClienteNewDTO objDto) {
		Cliente cli = new Cliente(null, objDto.getNome(), objDto.getEmail(), objDto.getCpfOuCnpj(),
				TipoCliente.toEnum(objDto.getTipo()));

		Cidade cid = cidadeRespository.findOne(objDto.getCidadeId());

		Endereco end = new Endereco(null, objDto.getLogradouro(), objDto.getNumero(), objDto.getComplemento(),
				objDto.getBairro(), objDto.getCep(), cli, cid);

		cli.getEnderecos().add(end);
		
		cli.getTelefones().add(objDto.getTelefone1());
		if (objDto.getTelefone2() != null) {
			cli.getTelefones().add(objDto.getTelefone2());	
		}
		if (objDto.getTelefone3() != null) {
			cli.getTelefones().add(objDto.getTelefone3());
		}

		return cli;
	}

	private void updateData(Cliente newObj, Cliente obj) {
		newObj.setNome(obj.getNome());
		newObj.setEmail(obj.getEmail());

	}

}
