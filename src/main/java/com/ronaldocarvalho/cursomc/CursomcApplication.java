package com.ronaldocarvalho.cursomc;

import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.ronaldocarvalho.cursomc.domain.Categoria;
import com.ronaldocarvalho.cursomc.domain.Cidade;
import com.ronaldocarvalho.cursomc.domain.Cliente;
import com.ronaldocarvalho.cursomc.domain.Endereco;
import com.ronaldocarvalho.cursomc.domain.Estado;
import com.ronaldocarvalho.cursomc.domain.ItemPedido;
import com.ronaldocarvalho.cursomc.domain.Pagamento;
import com.ronaldocarvalho.cursomc.domain.PagamentoComBoleto;
import com.ronaldocarvalho.cursomc.domain.PagamentoComCartao;
import com.ronaldocarvalho.cursomc.domain.Pedido;
import com.ronaldocarvalho.cursomc.domain.Produto;
import com.ronaldocarvalho.cursomc.domain.enums.EstadoPagamento;
import com.ronaldocarvalho.cursomc.domain.enums.TipoCliente;
import com.ronaldocarvalho.cursomc.repositoiries.CategoriaRepository;
import com.ronaldocarvalho.cursomc.repositoiries.CidadeRespository;
import com.ronaldocarvalho.cursomc.repositoiries.ClienteRepository;
import com.ronaldocarvalho.cursomc.repositoiries.EnderecoRepository;
import com.ronaldocarvalho.cursomc.repositoiries.EstadoRepository;
import com.ronaldocarvalho.cursomc.repositoiries.ItemPedidoRepository;
import com.ronaldocarvalho.cursomc.repositoiries.PagamentoRepository;
import com.ronaldocarvalho.cursomc.repositoiries.PedidoRepository;
import com.ronaldocarvalho.cursomc.repositoiries.ProdutoRepository;

@SpringBootApplication
public class CursomcApplication implements CommandLineRunner {

	@Autowired
	private CategoriaRepository categoriaRepository;

	@Autowired
	private ProdutoRepository produtoRepository;

	@Autowired
	private EstadoRepository estadoRepository;

	@Autowired
	private CidadeRespository cidadeRespository;

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private EnderecoRepository enderecoRepository;

	@Autowired
	private PedidoRepository pedidoRepository;

	@Autowired
	private PagamentoRepository pagamentoRepository;

	@Autowired
	private ItemPedidoRepository itemPedidoRepository;

	public static void main(String[] args) {
		SpringApplication.run(CursomcApplication.class, args);
	}

	@Override
	public void run(String... arg0) throws Exception {
		// TODO Auto-generated method stub

		Categoria cat1 = new Categoria(null, "Informática");
		Categoria cat2 = new Categoria(null, "Escritório");
		Categoria cat3 = new Categoria(null, "Cama Mesa E Banho");
		Categoria cat4 = new Categoria(null, "Jardinagem");
		Categoria cat5 = new Categoria(null, "Eletronicos");
		Categoria cat6 = new Categoria(null, "Decoracao");
		Categoria cat7 = new Categoria(null, "Perfumaria");
		Categoria cat8 = new Categoria(null, "Ferramentas");
		

		Produto p1 = new Produto(null, "Computador", 2000.00);
		Produto p2 = new Produto(null, "Impressora", 800.00);
		Produto p3 = new Produto(null, "Mouse", 80.0);

		cat1.getProdutos().addAll(Arrays.asList(p1, p2, p3));
		cat2.getProdutos().addAll(Arrays.asList(p2));

		p1.getCategorias().addAll(Arrays.asList(cat1));
		p2.getCategorias().addAll(Arrays.asList(cat2, cat1));
		p3.getCategorias().addAll(Arrays.asList(cat1));

		categoriaRepository.save(Arrays.asList(cat1, cat2, cat3, cat4,cat5,cat6,cat7,cat8));
		produtoRepository.save(Arrays.asList(p1, p2, p3));

		Estado est1 = new Estado(null, "Minas Gerais");
		Estado est2 = new Estado(null, "Sao Paulo");

		Cidade c1 = new Cidade(null, "Uberlandia", est1);
		Cidade c2 = new Cidade(null, "Sao Paulo", est2);
		Cidade c3 = new Cidade(null, "Campinas", est2);

		est1.getCidades().addAll(Arrays.asList(c1));
		est2.getCidades().addAll(Arrays.asList(c2, c3));

		estadoRepository.save(Arrays.asList(est1, est2));
		cidadeRespository.save(Arrays.asList(c1, c2, c3));

		Cliente cli1 = new Cliente(null, "Maria", "maria@gmail.com", "123434554", TipoCliente.PESSOAFISICA);

		cli1.getTelefones().addAll(Arrays.asList("2793734", "23820320"));

		Endereco e1 = new Endereco(null, "Rua Flores", "2324", "Casa2", "JD Marica", "2324242", cli1, c1);
		Endereco e2 = new Endereco(null, "Av Matos", "4545", "Nao", "Aeroporto", "883423", cli1, c2);

		cli1.getEnderecos().addAll(Arrays.asList(e1, e2));

		clienteRepository.save(Arrays.asList(cli1));
		enderecoRepository.save(Arrays.asList(e1, e2));

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy HH:mm");

		Pedido pedi1 = new Pedido(null, sdf.parse("30/09/2017 10:32"), cli1, e1);
		Pedido pedi2 = new Pedido(null, sdf.parse("10/10/2017 11:33"), cli1, e2);

		Pagamento pagto1 = new PagamentoComCartao(null, EstadoPagamento.QUITADO, pedi1, 6);
		pedi1.setPagamento(pagto1);

		Pagamento pagto2 = new PagamentoComBoleto(null, EstadoPagamento.PENDENTE, pedi2, sdf.parse("20/10/2017 00:00"),
				null);
		pedi2.setPagamento(pagto2);

		cli1.getPedidos().addAll(Arrays.asList(pedi1, pedi2));

		pedidoRepository.save(Arrays.asList(pedi1, pedi2));
		pagamentoRepository.save(Arrays.asList(pagto1, pagto2));

		ItemPedido ip1 = new ItemPedido(pedi1, p1, 0.00, 1, 2000.00);
		ItemPedido ip2 = new ItemPedido(pedi1, p3, 0.00, 2, 80.0);
		ItemPedido ip3 = new ItemPedido(pedi2, p2, 100.0, 1, 800.0);

		p1.getItens().addAll(Arrays.asList(ip1, ip2));
		p2.getItens().addAll(Arrays.asList(ip3));
		p3.getItens().addAll(Arrays.asList(ip2));

		itemPedidoRepository.save(Arrays.asList(ip1, ip2, ip3));
	}

}
