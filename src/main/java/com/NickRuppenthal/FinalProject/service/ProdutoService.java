package com.NickRuppenthal.FinalProject.service;

import com.NickRuppenthal.FinalProject.config.exception.MethodArgumentNotValidException;
import com.NickRuppenthal.FinalProject.config.exception.NotFoundException;
import com.NickRuppenthal.FinalProject.controller.dto.ProdutoDto;
import com.NickRuppenthal.FinalProject.controller.form.ProdutoForm;
import com.NickRuppenthal.FinalProject.controller.form.SearchForm;
import com.NickRuppenthal.FinalProject.controller.form.UpdateForm;
import com.NickRuppenthal.FinalProject.modelo.Produto;
import com.NickRuppenthal.FinalProject.repository.ProtdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class ProdutoService {

    @Autowired
    private ProtdutoRepository pRepository;


    public List<Produto> list() {
        List<Produto> produtos = pRepository.findAll();
        return produtos;
    }

    public List<Produto> search(SearchForm sForm){
        List<Produto> resultadoDaBusca = pRepository.findProdutoByPriceByName(Double.parseDouble(sForm.getMax_price()), Double.parseDouble(sForm.getMin_price()), sForm.getQ());
        if(resultadoDaBusca.isEmpty()){
            throw new NotFoundException("Nenhum Produto encontrado");
        }
        return resultadoDaBusca;
    }

    public Produto findById(Integer id){
        Optional<Produto> produto = pRepository.findById(id);
        if (produto.isPresent()){return produto.get();}
        throw new NotFoundException("Produto não encontrado");
    }

    public Produto create(ProdutoForm pForm){
        if (pForm.getName().isEmpty() || pForm.getDescription().isEmpty() || pForm.getPrice() == null){
            throw new MethodArgumentNotValidException("Não deixe campo em branco");
        }
        Produto produto = pForm.converter(pRepository);
        pRepository.save(produto);
        return produto;
    }

    public Produto update(Integer id, UpdateForm uForm){
        Optional<Produto> opt = pRepository.findById(id);
        if (opt.isPresent()){
            if (uForm.getName().isEmpty() || uForm.getDescription().isEmpty() || uForm.getPrice() == null){
                throw new MethodArgumentNotValidException("Não deixe campo em branco");
            }
            Produto produto = uForm.atualizar(id, pRepository);
            return produto;
        }
        throw new NotFoundException("Produto não encontrado");
    }


    public ResponseEntity<?> delete(Integer id){
        Optional<Produto> produto = pRepository.findById(id);
        if (produto.isPresent()){
            pRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }
        throw new NotFoundException("Produto não encontrado");
    }
}

























