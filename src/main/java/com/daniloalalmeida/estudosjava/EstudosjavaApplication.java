package com.daniloalalmeida.estudosjava;

import com.daniloalalmeida.estudosjava.domain.*;
import com.daniloalalmeida.estudosjava.domain.enums.EstadoPagamento;
import com.daniloalalmeida.estudosjava.domain.enums.TipoCliente;
import com.daniloalalmeida.estudosjava.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.text.SimpleDateFormat;
import java.util.Arrays;

@SpringBootApplication
public class EstudosjavaApplication implements CommandLineRunner {

	@Autowired
	private CategoriaRepository categoriaRepository;
	@Autowired
	private ProdutoRepository produtoRepository;
	@Autowired
	private EstadoRepository estadoRepository;
	@Autowired
	private CidadeRepository cidadeRepository;
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
		SpringApplication.run(EstudosjavaApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		Categoria cat1 = new Categoria(null, "Cozinha");
		Categoria cat2 = new Categoria(null, "Sala");
		Categoria cat3 = new Categoria(null, "Informática");
		Categoria cat4 = new Categoria(null, "Escritório");

		Produto p1 = new Produto(null, "Computador", 2000.00);
		Produto p2 = new Produto(null, "Impressora", 800.00);
		Produto p3 = new Produto(null, "Mouse", 80.00);

		cat3.getProdutos().addAll(Arrays.asList(p1, p2, p3));
		cat4.getProdutos().addAll(Arrays.asList(p2));

		p1.getCategorias().addAll(Arrays.asList(cat3));
		p2.getCategorias().addAll(Arrays.asList(cat3, cat4));
		p3.getCategorias().addAll((Arrays.asList(cat3)));

		categoriaRepository.saveAll(Arrays.asList(cat1, cat2, cat3, cat4));
		produtoRepository.saveAll((Arrays.asList(p1, p2, p3)));

		Estado est1 = new Estado(null, "Minas Gerais");
		Estado est2 = new Estado(null, "São Paulo");

		Cidade cid1 = new Cidade(null, "Belo Horizonte", est1);
		Cidade cid2 = new Cidade(null, "Sorocaba", est2);
		Cidade cid3 = new Cidade(null, "Campinas", est2);

		est1.getCidades().addAll(Arrays.asList(cid1));
		est2.getCidades().addAll(Arrays.asList(cid2, cid3));

		estadoRepository.saveAll(Arrays.asList(est1, est2));
		cidadeRepository.saveAll(Arrays.asList(cid1, cid2, cid3));

		Cliente cli1 = new Cliente(null, "Maria Silva", "maria@email.com", "33399933366", TipoCliente.PESSOAFISICA);

		cli1.getTelefones().addAll(Arrays.asList("999999999", "333333333"));

		Endereco e1 = new Endereco(null, "Rua Flores", "300", "Apto 303", "Jardim", "38922034", cli1, cid1);
		Endereco e2 = new Endereco(null, "Avenida Matos", "105", "Sala 800", "Centro", "34534534", cli1, cid2);

		cli1.getEnderecos().addAll(Arrays.asList(e1, e2));

		clienteRepository.saveAll(Arrays.asList(cli1));
		enderecoRepository.saveAll(Arrays.asList(e1, e2));

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");

		Pedido ped1 = new Pedido(null, simpleDateFormat.parse("30/09/2022 10:30"), cli1, e1);
		Pedido ped2 = new Pedido(null, simpleDateFormat.parse("10/10/2022 19:35"), cli1, e2);

		Pagamento pagto1 = new PagamentoComCartao(null, EstadoPagamento.QUITADO, ped1, 6);
		ped1.setPagamento(pagto1);

		Pagamento pagto2 = new PagamentoComBoleto(null, EstadoPagamento.PENDENTE, ped2, simpleDateFormat.parse("20/10/2022 00:00"), null);
		ped2.setPagamento(pagto2);

		cli1.getPedidos().addAll(Arrays.asList(ped1, ped2));

		pedidoRepository.saveAll(Arrays.asList(ped1, ped2));
		pagamentoRepository.saveAll((Arrays.asList(pagto1, pagto2)));

		ItemPedido ip1 = new ItemPedido(ped1, p1, 0.00, 1, 2000.00);
		ItemPedido ip2 = new ItemPedido(ped1, p3, 0.00, 2, 80.00);
		ItemPedido ip3 = new ItemPedido(ped2, p2, 100.00, 1, 800.00);

		ped1.getItens().addAll(Arrays.asList(ip1, ip2));
		ped2.getItens().addAll(Arrays.asList(ip3));

		p1.getItens().addAll(Arrays.asList(ip1));
		p2.getItens().addAll(Arrays.asList(ip3));
		p3.getItens().addAll(Arrays.asList(ip2));

		itemPedidoRepository.saveAll(Arrays.asList(ip1, ip2, ip3));


	}

}
