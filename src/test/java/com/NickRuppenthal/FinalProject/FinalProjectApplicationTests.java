package com.NickRuppenthal.FinalProject;

import com.NickRuppenthal.FinalProject.controller.form.ProdutoForm;
import com.NickRuppenthal.FinalProject.controller.form.UpdateForm;
import com.NickRuppenthal.FinalProject.modelo.Produto;
import com.NickRuppenthal.FinalProject.repository.ProtdutoRepository;

import com.NickRuppenthal.FinalProject.service.ProdutoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.Assert;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
class FinalProjectApplicationTests {


	private ProdutoService pService;
	@Mock
	private ProtdutoRepository repositoryMock;

	@BeforeEach
	public void beforeEach(){
		MockitoAnnotations.initMocks(this);
		this.pService = new ProdutoService(repositoryMock);
	}

	@Test
	void deveriaBuscarTodosOsProdutos(){
		List<Produto> produtos = produtos();
		Mockito.when(repositoryMock.findAll()).thenReturn(produtos);
		pService.list();
		Assert.assertNotNull(produtos);
	}

	@Test
	void deveriaBuscarUmProdutoPeloId(){
		List<Produto> produtos = produtos();
		Produto produto = produtos.get(0);
		Integer id = 1;
		Mockito.when(repositoryMock.findById(id)).thenReturn(Optional.ofNullable(produto));
		pService.findById(id);
		Assert.assertNotNull(produto);
		Assert.assertEquals(id, produto.getId());
	}

	@Test
	void deveriaBuscarUmProdutoPorPrecoOuPorNome(){
		List<Produto> produtos = produtos();
		String nome = "teste2";
		Double menoPreco = 50.00, maiorPreco = 200.00;
		Mockito.when(repositoryMock.findProdutoByPriceByName(maiorPreco, menoPreco, nome)).thenReturn(produtos);
		pService.search(maiorPreco, menoPreco, nome);
	}

	@Test
	void deveriaCriarUmProduto(){
		ProdutoForm form = formulario();
		Produto produto = form.converter(repositoryMock);

		Mockito.when(repositoryMock.save(produto)).thenReturn(produto);
		pService.create(form);
		Assert.assertNotNull(form);
		Mockito.verify(repositoryMock).save(produto);
	}

//	@Test
//	void deveriaAtualizarUmProduto(){
//		Integer id = 2;
//		UpdateForm att = att();
//
//		List<Produto> produtos = produtos();
//		Produto produto = produtos.get(1);
//		Mockito.when(repositoryMock.findById(id)).thenReturn(Optional.ofNullable(produto));
//		pService.findById(id);
//
//
//
//		Produto produtoAtualizado = att.atualizar(id, repositoryMock);
//
//		Mockito.when(att.atualizar(id, repositoryMock)).thenReturn(produtoAtualizado);
//		Assert.assertNotNull(att);
//		Assert.assertEquals(id,produto.getId());
//		Mockito.verify(att).atualizar(id, repositoryMock);
//	}



	private List<Produto> produtos(){
		List<Produto> lista = new ArrayList<>();

		Produto produto1 = new Produto(1,"teste1","teste de descrição1",100.0);
		Produto produto2 = new Produto(2,"teste2","teste de descrição2",200.0);
		Produto produto3 = new Produto(3,"teste3","teste de descrição3",300.0);

		lista.add(produto1);
		lista.add(produto2);
		lista.add(produto3);

		return lista;
	}

	private ProdutoForm formulario(){
		ProdutoForm form = new ProdutoForm("descrição do teste","teste",100.0);
		return form;
	}

	private UpdateForm att(){
		UpdateForm att = new UpdateForm("descrição do teste de att","teste de att",101.0);
		return att;
	}

}
