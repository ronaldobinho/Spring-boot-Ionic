package com.ronaldocarvalho.cursomc.services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.ronaldocarvalho.cursomc.domain.Cliente;
import com.ronaldocarvalho.cursomc.domain.ItemPedido;
import com.ronaldocarvalho.cursomc.domain.PagamentoComBoleto;
import com.ronaldocarvalho.cursomc.domain.Pedido;
import com.ronaldocarvalho.cursomc.domain.enums.EstadoPagamento;
import com.ronaldocarvalho.cursomc.repositoiries.ClienteRepository;
import com.ronaldocarvalho.cursomc.repositoiries.ItemPedidoRepository;
import com.ronaldocarvalho.cursomc.repositoiries.PagamentoRepository;
import com.ronaldocarvalho.cursomc.repositoiries.PedidoRepository;
import com.ronaldocarvalho.cursomc.repositoiries.ProdutoRepository;
import com.ronaldocarvalho.cursomc.security.UserSS;
import com.ronaldocarvalho.cursomc.services.exceptions.AutorizathionException;
import com.ronaldocarvalho.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class PedidoService {
	// Acesso a camada de repository
	@Autowired
	private PedidoRepository repo;

	@Autowired
	private BoletoService boletoService;

	@Autowired
	private PagamentoRepository pagamentoRepository;

	@Autowired
	private ProdutoRepository produtoRepository;

	@Autowired
	private ItemPedidoRepository itemPedidoRepository;

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private EmailService emailService;

	public Pedido find(Integer id) {
		Pedido obj = repo.findOne(id);
		// Tratamento de excessao
		if (obj == null) {
			throw new ObjectNotFoundException("Objeto não encontrado! ID: " + id + ", Tipo: " + Pedido.class.getName());

		}
		return obj;

	}

	public Pedido insert(Pedido obj) {
		obj.setId(null);
		obj.setInstante(new Date());
		obj.setCliente(clienteRepository.findOne(obj.getCliente().getId()));
		obj.getPagamento().setEstado(EstadoPagamento.PENDENTE);
		obj.getPagamento().setPedido(obj);
		if (obj.getPagamento() instanceof PagamentoComBoleto) {
			PagamentoComBoleto pagto = (PagamentoComBoleto) obj.getPagamento();
			boletoService.preencherPagamentoComBoleto(pagto, obj.getInstante());
		}
		obj = repo.save(obj);
		pagamentoRepository.save(obj.getPagamento());
		for (ItemPedido ip : obj.getItens()) {
			ip.setDesconto(0.0);
			ip.setProduto(produtoRepository.findOne(ip.getProduto().getId()));
			ip.setPreco(ip.getProduto().getPreco());
			ip.setPedido(obj);
		}
		itemPedidoRepository.save(obj.getItens());
		emailService.sendOrderConfirmationHtmlEmail(obj);
		return obj;
	}

	public Page<Pedido> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		UserSS user = UserService.authenticated();
		if(user == null) {
			throw new AutorizathionException("Acesso Negado");
		}
		PageRequest pageRequest = new PageRequest(page, linesPerPage, Direction.valueOf(direction), orderBy);
		Cliente cliente = clienteRepository.findOne(user.getId());
		return repo.findByCliente(cliente, pageRequest);

	}
}
