package com.NickRuppenthal.FinalProject.services.impl;

import com.NickRuppenthal.FinalProject.config.exceptions.MethodArgumentNotValidException;
import com.NickRuppenthal.FinalProject.config.exceptions.NotFoundException;
import com.NickRuppenthal.FinalProject.modelo.Produto;
import com.NickRuppenthal.FinalProject.modelo.dto.ProdutoDto;
import com.NickRuppenthal.FinalProject.repository.ProtdutoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;

class ProductServiceImplTest {

    public static final Integer ID       = 1;
    public static final String NAME      = "teste1";
    public static final String DESCRICAO = "teste de descrição1";
    public static final double PRICE     = 100.0;
    public static final double MAX_PRICE = 90.0;
    public static final double MIN_PRICE = 50.0;
    public static final int INDEX = 0;
    public static final Sort sort = Sort.by("name").ascending();
    public static final Pageable PAGINACAO = PageRequest.of(0,7, sort);
    @InjectMocks
    private ProductServiceImpl service;
    @Mock
    private ModelMapper mapper;
    private Produto produto;
    private ProdutoDto produtoDto;
    private Optional<Produto> optProduto;

    @Mock
    private ProtdutoRepository repositoryMock;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        this.service = new ProductServiceImpl(repositoryMock);
        produtoStarter();
    }

//    @Test
//    void deveriaBuscarTodosOsProdutos() {
//        Mockito.when(repositoryMock.findAll()).thenReturn(List.of(produto));
//        Optional<Page<Produto>> response = service.list();
//        assertNotNull(response.get());
//        assertEquals(Produto.class, response.get().get(INDEX).getClass());
//    }

    @Test
    void quandoNaoEcontrarNenhumProduto_LancarNotFoundException(){
        Mockito.when(repositoryMock.findAll(PAGINACAO)).thenThrow(new NotFoundException("Nenhum Produto encontrado"));

        try{
            service.list();
        } catch (Exception ex){
            assertEquals(NotFoundException.class, ex.getClass());
            assertEquals("Nenhum Produto encontrado",ex.getMessage());
        }
    }

    @Test
    void deveriaBuscarUmProduto_PeloId() {
        Mockito.when(repositoryMock.findById(anyInt())).thenReturn(optProduto);
        Optional<Produto> response = service.findById(ID);
        assertNotNull(response.get());
        assertEquals(Produto.class,response.get().getClass());
        assertEquals(ID, response.get().getId());
        assertEquals(NAME, response.get().getName());
        assertEquals(DESCRICAO, response.get().getDescription());
        assertEquals(Optional.of(PRICE), Optional.of(response.get().getPrice()));
    }

    @Test
    void quandoNaoEcontrarProduto_LancarNotFoundException(){
        Mockito.when(repositoryMock.findById(anyInt())).thenThrow(new NotFoundException("Nenhum Produto encontrado"));

        try{
            service.findById(ID);
        } catch (Exception ex){
            assertEquals(NotFoundException.class, ex.getClass());
            assertEquals("Nenhum Produto encontrado",ex.getMessage());
        }
    }


//    @Test
//    void deveriaBuscarUmProduto_PorPrecoOuPorNome(){
//
//        Mockito.when(repositoryMock.findProdutoByPriceByName(MAX_PRICE, MIN_PRICE, NAME, PAGINACAO)).thenReturn((Page<produto>);
//        Page<Produto> response = service.search(MAX_PRICE, MIN_PRICE, NAME);
//        assertEquals(Produto.class, response.get().getClass());
//        assertEquals(ID, response.get(INDEX).getId());
//        assertEquals(NAME, response.get(INDEX).getName());
//        assertEquals(DESCRICAO, response.get(INDEX).getDescription());
//        assertEquals(Optional.of(PRICE), Optional.of(response.get(INDEX).getPrice()));
//        verify(repositoryMock).findProdutoByPriceByName(MAX_PRICE, MIN_PRICE, NAME, PAGINACAO);
//    }

    @Test
    void quandoNaoEncontrarProduto_PorPrecoOuPorNome(){
        Mockito.when(repositoryMock.findProdutoByPriceByName(MAX_PRICE, MIN_PRICE, NAME, PAGINACAO)).thenThrow(new NotFoundException("Nenhum Produto encontrado"));

        try{
            service.search(MAX_PRICE, MIN_PRICE, NAME);
        } catch (Exception ex){
            assertEquals(NotFoundException.class, ex.getClass());
            assertEquals("Nenhum Produto encontrado",ex.getMessage());
        }
    }

    @Test
    void deveriaCriarUmProduto(){
        Produto produtot = produtoDto.transformar(repositoryMock);
        Mockito.when(repositoryMock.save(produtot)).thenReturn(produto);
        Produto response = service.create(produtoDto);
        assertNotNull(response);
        assertNotNull(produtoDto);
        assertEquals(Produto.class, response.getClass());
        verify(repositoryMock).save(produtot);
    }

    @Test
    void deveDevolverMethodArgumentNotValidException_SeTiverUmCampoEmBranco_QuandoForCriarUmProduto(){
        Mockito.when(repositoryMock.save(any())).thenThrow(new MethodArgumentNotValidException("Não deixe campo em branco"));

        try {
            service.create(produtoDto);
        } catch (Exception ex){
            assertEquals(MethodArgumentNotValidException.class, ex.getClass());
            assertEquals("Não deixe campo em branco",ex.getMessage());
        }
    }

    @Test
    void deveriaAtualizarUmProduto_PeloId(){
        Mockito.when(repositoryMock.findById(ID)).thenReturn(optProduto);
        Mockito.when(repositoryMock.save(any())).thenReturn(produto);
        Produto response = service.update(ID, produtoDto);
        assertNotNull(response);
        assertEquals(Produto.class, response.getClass() );
        assertEquals(ID, response.getId());
        assertEquals(NAME, response.getName());
        assertEquals(DESCRICAO, response.getDescription());
        assertEquals(Optional.of(PRICE), Optional.of(response.getPrice()));


    }

    @Test
    void deveDevolverNotFoundException_SeIdNaoForEncontrado_QuandoAtualizarUmProduto(){
        Mockito.when(repositoryMock.findById(ID)).thenThrow(new NotFoundException("Produto não encontrado"));

        try {
            service.update(ID,produtoDto);
        } catch (Exception ex){
            assertEquals(NotFoundException.class, ex.getClass());
            assertEquals("Produto não encontrado",ex.getMessage());
        }

    }

    @Test
    void deveDevolverMethodArgumentNotValidException_SeTiverUmCampoEmBranco_QuandoForAtualizarUmProduto(){
        Mockito.when(repositoryMock.findById(ID)).thenReturn(optProduto);
        Mockito.when(repositoryMock.save(any())).thenThrow(new MethodArgumentNotValidException("Não deixe campo em branco"));

        try {
            service.update(ID,produtoDto);
        } catch (Exception ex){
            assertEquals(MethodArgumentNotValidException.class, ex.getClass());
            assertEquals("Não deixe campo em branco",ex.getMessage());
        }

    }

    @Test
    void deveriaExcluirUmProduto_PeloId(){
        Mockito.when(repositoryMock.findById(ID)).thenReturn(optProduto);
        service.delete(ID);
        assertNotNull(ID);
        assertEquals(ID, produto.getId());
        verify(repositoryMock).deleteById(ID);
    }

    @Test
    void quandoNaoEcontrarProduto_LancarNotFoundExceptio_AoExlcuirUmProduto(){
        Mockito.when(repositoryMock.findById(anyInt())).thenThrow(new NotFoundException("Produto não encontrado"));

        try{
            service.delete(ID);
        } catch (Exception ex){
            assertEquals(NotFoundException.class, ex.getClass());
            assertEquals("Produto não encontrado",ex.getMessage());
        }
    }

    private void produtoStarter(){
        produto = new Produto(ID, NAME, DESCRICAO, PRICE);
        optProduto = Optional.of(new Produto(ID, NAME, DESCRICAO, PRICE));
        produtoDto = new ProdutoDto(NAME, DESCRICAO, PRICE);
    }
}