package com.NickRuppenthal.FinalProject.controller;


import com.NickRuppenthal.FinalProject.config.exception.MethodArgumentNotValidException;
import com.NickRuppenthal.FinalProject.config.exception.NotFoundException;
import com.NickRuppenthal.FinalProject.controller.dto.ProdutoDto;
import com.NickRuppenthal.FinalProject.controller.form.ProdutoForm;
import com.NickRuppenthal.FinalProject.controller.form.SearchForm;
import com.NickRuppenthal.FinalProject.controller.form.UpdateForm;
import com.NickRuppenthal.FinalProject.modelo.Produto;
import com.NickRuppenthal.FinalProject.repository.ProtdutoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import javax.validation.Valid;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/products")
public class ProdutoController {


    @Autowired
    private ProtdutoRepository pRepository;



    @GetMapping
    public ResponseEntity<List<ProdutoDto>> list(){
        List<Produto> produtos = pRepository.findAll();
        return ResponseEntity.ok(ProdutoDto.converter(produtos));
    }

    @GetMapping("/search")
    public ResponseEntity<List<ProdutoDto>> search(@RequestBody SearchForm sForm){

        Produto resultQ = pRepository.findByName(sForm.getQ());
        List<Produto> resultMax = pRepository.findByPrice(sForm.getMax_price());
        List<Produto> resultMin = pRepository.findByPriceLessThan(sForm.getMin_price());


        List<Produto> resultadoDaBusca = new ArrayList<Produto>();


        resultMax.forEach( e ->{
            resultadoDaBusca.add(e);
        });

        resultMin.forEach( e ->{
            resultadoDaBusca.add(e);
        });

        resultadoDaBusca.add(resultQ);

        return ResponseEntity.ok(ProdutoDto.converter(resultadoDaBusca));

    }



    @GetMapping("/{id}")
    public ResponseEntity<ProdutoDto> findById(@PathVariable @Valid Integer id){
        Optional<Produto> produto = pRepository.findById(id);
        if (produto.isPresent()){
            return ResponseEntity.ok(new ProdutoDto(produto.get()));
        }

        return ResponseEntity.notFound().build();
    }

    @PostMapping
    @Transactional
    public ResponseEntity<ProdutoDto> create(@RequestBody  ProdutoForm pForm, UriComponentsBuilder uBuilder){

        if (pForm.getName().isEmpty() || pForm.getDescription().isEmpty() || pForm.getPrice() == null){
            throw new MethodArgumentNotValidException("Não deixe campo em branco");
        }

        Produto produto = pForm.converter(pRepository);
        pRepository.save(produto);

        URI uri = uBuilder.path("/products/{id}").buildAndExpand(produto.getId()).toUri();
        return ResponseEntity.created(uri).body(new ProdutoDto(produto));
    }


    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<ProdutoDto> update(@PathVariable Integer id, @RequestBody UpdateForm uForm){
        Optional<Produto> opt = pRepository.findById(id);
        if (opt.isPresent()){
            if (uForm.getName().isEmpty() || uForm.getDescription().isEmpty() || uForm.getPrice() == null){
                throw new MethodArgumentNotValidException("Não deixe campo em branco");
            }
            Produto produto = uForm.atualizar(id, pRepository);
            return ResponseEntity.ok(new ProdutoDto(produto));
        }
        throw new NotFoundException("Produto não encontrado");
    }


    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<?> delete(@PathVariable Integer id){
        Optional<Produto> produto = pRepository.findById(id);
        if (produto.isPresent()){
            pRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }
        throw new NotFoundException("Produto não encontrado");
    }




}
