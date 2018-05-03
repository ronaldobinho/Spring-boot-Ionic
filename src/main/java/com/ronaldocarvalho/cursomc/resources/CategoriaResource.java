package com.ronaldocarvalho.cursomc.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.ronaldocarvalho.cursomc.domain.Categoria;
import com.ronaldocarvalho.cursomc.dto.CategoriaDTO;
import com.ronaldocarvalho.cursomc.services.CategoriaService;

//Camada Rest
@RestController
@RequestMapping("/categorias")
public class CategoriaResource {
	// Acesso a camada Servico
	@Autowired
	private CategoriaService service;

	// Requisicao do id a ser buscado pelo metodo get
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Categoria> find(@PathVariable Integer id) {

		Categoria obj = service.find(id);
		return ResponseEntity.ok().body(obj);

	}

	@PreAuthorize("hasAnyRole('ADMIN')")
	@RequestMapping(method = RequestMethod.POST)
	// @RequestBody converte o objeto JSON recebido para java
	public ResponseEntity<Void> insert(@Valid @RequestBody CategoriaDTO objDto) {
		Categoria obj = service.fromDTO(objDto);
		obj = service.insert(obj);

		// fornece o id retornado do metodo HTTP que retornou para o obj
		// Atribui ao metodo HTTP o id salvo do objeto, retorna para o ResponseEntity
		// esse id disponibilizando o link
		/// Categoria/({/ID}
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();

		return ResponseEntity.created(uri).build();
	}

	@PreAuthorize("hasAnyRole('ADMIN')")
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> update(@Valid @RequestBody CategoriaDTO objDto, @PathVariable Integer id) {
		Categoria obj = service.fromDTO(objDto);
		obj.setId(id);
		obj = service.update(obj);
		// Retorna no formatdo JSon no formado Entity vazio
		return ResponseEntity.noContent().build();
	}

	@PreAuthorize("hasAnyRole('ADMIN')")
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		service.delete(id);
		
		return ResponseEntity.noContent().build();
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<CategoriaDTO>> findAll() {

		List <Categoria> list  = service.findAll();
		List <CategoriaDTO> listDto = list.stream().map(obj -> new CategoriaDTO(obj)).collect(Collectors.toList());
		return ResponseEntity.ok().body(listDto);

	}
	
	@RequestMapping(value="/page", method = RequestMethod.GET)
	public ResponseEntity<Page<CategoriaDTO>> findPage(
			@RequestParam(value="page", defaultValue="0") Integer page, 
			@RequestParam(value="linesPerPage", defaultValue="24")Integer linesPerPage,
			@RequestParam(value="orderBy", defaultValue="nome")String orderBy, 
			@RequestParam(value="direction", defaultValue="ASC")String direction) {

		Page <Categoria> list  = service.findPage(page,linesPerPage, orderBy, direction);
		Page <CategoriaDTO> listDto = list.map(obj -> new CategoriaDTO(obj));
		return ResponseEntity.ok().body(listDto);

	}

}
