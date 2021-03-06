package com.cursomc.uml;

import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.cursomc.uml.domain.Categoria;
import com.cursomc.uml.domain.Cidade;
import com.cursomc.uml.domain.Cliente;
import com.cursomc.uml.domain.Endereco;
import com.cursomc.uml.domain.Estado;
import com.cursomc.uml.domain.ItemPedido;
import com.cursomc.uml.domain.Pagamento;
import com.cursomc.uml.domain.PagamentoComBoleto;
import com.cursomc.uml.domain.PagamentoComCartao;
import com.cursomc.uml.domain.Pedido;
import com.cursomc.uml.domain.Produto;
import com.cursomc.uml.domain.enums.EstadoPagamento;
import com.cursomc.uml.domain.enums.TipoCliente;
import com.cursomc.uml.repositories.CategoriaRepository;
import com.cursomc.uml.repositories.CidadeRepository;
import com.cursomc.uml.repositories.ClienteRepository;
import com.cursomc.uml.repositories.EnderecoRepository;
import com.cursomc.uml.repositories.EstadoRepository;
import com.cursomc.uml.repositories.ItemPedidoRepository;
import com.cursomc.uml.repositories.PagamentoRepository;
import com.cursomc.uml.repositories.PedidoRepository;
import com.cursomc.uml.repositories.ProdutoRepository;

@SpringBootApplication
public class CursoMcApplication implements CommandLineRunner {
	
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
		
	
	//Principal
	public static void main(String[] args) {
		SpringApplication.run(CursoMcApplication.class, args);
	}
	
	@Override
	public void run(String...args) throws Exception{
		
		Categoria cat1 = new Categoria(null, "Informática");
		Categoria cat2 = new Categoria(null, "Escritório");
		
		Produto p1 = new Produto(null,"Computador", 4000.00);
		Produto p2 = new Produto (null,"Impressora", 1000.00);
		Produto p3 = new Produto (null, "Mouse", 120.00);
		
		cat1.getProdutos().addAll(Arrays.asList(p1,p2,p3));
		cat2.getProdutos().addAll(Arrays.asList(p2));
		
		p1.getCategorias().addAll(Arrays.asList(cat1));
		p2.getCategorias().addAll(Arrays.asList(cat1,cat2));
		p3.getCategorias().addAll(Arrays.asList(cat1));
		
		categoriaRepository.saveAll(Arrays.asList(cat1,cat2));
		produtoRepository.saveAll(Arrays.asList(p1,p2,p3));
		
		Estado est1 = new Estado(null, "Minas Gerais");
		Estado est2 = new Estado(null, "São Paulo");
		
		Cidade c1 = new Cidade(null, "Uberlândia", est1);
		Cidade c2 = new Cidade(null, "São Paulo", est2);
		Cidade c3 = new Cidade(null, "Campinas", est2);
		
		est1.getCidades().addAll(Arrays.asList(c1));
		est2.getCidades().addAll(Arrays.asList(c2,c3));
		
		estadoRepository.saveAll(Arrays.asList(est1,est2));
		cidadeRepository.saveAll(Arrays.asList(c1,c2,c3));
		
		Cliente cli1 = new Cliente(null, "Maria Lucia Alves de Azevedo", "mrlucia@gmail.com", "123.456.789-00", TipoCliente.PESSOAFISICA);
		
		cli1.getTelefones().addAll(Arrays.asList("12312312123","12312312313"));
		
		Endereco e1 = new Endereco(null, "Rua 10", "42", "Conj. Fernando Collor", "Taicoca", "49160-000",cli1, c1);
		Endereco e2 = new Endereco(null, "Rua Nikita", "11", "Conj. Shangrila", "Pq 10", "683443453", cli1,c2);
		
		cli1.getEnderecos().addAll(Arrays.asList(e1, e2));
		
		clienteRepository.saveAll(Arrays.asList(cli1));
		enderecoRepository.saveAll(Arrays.asList(e1, e2));
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm");
		
		Pedido pd1 = new Pedido(null, sdf.parse("30/09/2017 10:32"), cli1, e1);
		Pedido pd2 = new Pedido(null, sdf.parse("10/10/2017 19:35"), cli1, e2);
		
		Pagamento pmt1 = new PagamentoComCartao(null, EstadoPagamento.QUITADO, pd1, 6);
		pd1.setPagamento(pmt1);
		
		Pagamento pmt2 = new PagamentoComBoleto(null, EstadoPagamento.PENDENTE, pd2, sdf.parse("20/10/2017 00:00"), null);
		pd2.setPagamento(pmt2);
		
		cli1.getPedidos().addAll(Arrays.asList(pd1, pd2));
		
		pedidoRepository.saveAll(Arrays.asList(pd1, pd2));
		pagamentoRepository.saveAll(Arrays.asList(pmt1,pmt2));
		
		ItemPedido ip1 = new ItemPedido(pd1, p1, 0.0, 2, 4000.00);
		ItemPedido ip2 = new ItemPedido(pd1, p3, 0.0, 1, 120.00);
		ItemPedido ip3 = new ItemPedido(pd1, p2, 100.0, 1, 1000.00);
		
		pd1.getItens().addAll(Arrays.asList(ip1,ip2));
		pd2.getItens().addAll(Arrays.asList(ip3));
		
		p1.getItens().addAll(Arrays.asList(ip1));
		p2.getItens().addAll(Arrays.asList(ip3));
		p3.getItens().addAll(Arrays.asList(ip2));
		
		itemPedidoRepository.saveAll(Arrays.asList(ip1,ip2,ip3));
		
		
		
	}
}
