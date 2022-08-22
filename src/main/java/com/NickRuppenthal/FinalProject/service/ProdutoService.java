package com.NickRuppenthal.FinalProject.service;

import com.NickRuppenthal.FinalProject.config.exceptions.MethodArgumentNotValidException;
import com.NickRuppenthal.FinalProject.config.exceptions.NotFoundException;
import com.NickRuppenthal.FinalProject.controller.dto.DeleteDto;
import com.NickRuppenthal.FinalProject.controller.form.ProdutoForm;
import com.NickRuppenthal.FinalProject.controller.form.UpdateForm;
import com.NickRuppenthal.FinalProject.modelo.Produto;
import com.NickRuppenthal.FinalProject.repository.ProtdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProdutoService {


    private ProtdutoRepository pRepository;
    @Autowired
    public ProdutoService(ProtdutoRepository pRepository){
        this.pRepository = pRepository;
    }


    public List<Produto> list() {
        return pRepository.findAll();
    }

    public List<Produto> search(Double max, Double min, String name){
        List<Produto> resultadoDaBusca = pRepository.findProdutoByPriceByName(max, min, name);
        if(resultadoDaBusca.isEmpty()){throw new NotFoundException("Nenhum Produto encontrado");}
        return resultadoDaBusca;
    }

    public Optional<Produto> findById(Integer id){
       return Optional.of(pRepository.findById(id).orElseThrow(()-> new NotFoundException("Produto não encontrado")));
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
        Optional.of(pRepository.findById(id).orElseThrow(()-> new NotFoundException("Produto não encontrado")));
        if (uForm.getName().isEmpty() || uForm.getDescription().isEmpty() || uForm.getPrice() == null){
            throw new MethodArgumentNotValidException("Não deixe campo em branco");
        }
        return uForm.atualizar(id, pRepository);
    }


    public DeleteDto delete(Integer id){
        Optional.of(pRepository.findById(id).orElseThrow(()-> new NotFoundException("Produto não encontrado")));
        pRepository.deleteById(id);
        return new DeleteDto(200,"Item "+ id +" excluido com sucesso");
    }
}

























