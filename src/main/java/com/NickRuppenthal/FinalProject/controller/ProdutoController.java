package com.NickRuppenthal.FinalProject.controller;


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


//        Double DoubleResultMax = Double.parseDouble(sForm.getMax_price());
//        Double DoubeResultMin = Double.parseDouble(sForm.getMin_price());

        Produto resultQ = pRepository.findByName(sForm.getQ());
        Produto resultMax = pRepository.findByPriceGreaterThan(sForm.getMax_price());
        Produto resultMin = pRepository.findByPriceLessThan(sForm.getMax_price());


        List<Produto> resultadoDaBusca = new ArrayList<Produto>();

        resultadoDaBusca.add(resultQ);

        resultadoDaBusca.add(resultMax);
        resultadoDaBusca.add(resultMin);


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
    public ResponseEntity<ProdutoDto> create(@RequestBody  @Valid ProdutoForm pForm, UriComponentsBuilder uBuilder){
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
            Produto produto = uForm.atualizar(id, pRepository);
            return ResponseEntity.ok(new ProdutoDto(produto));
        }
        return ResponseEntity.notFound().build();
    }


    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<?> delete(@PathVariable Integer id){
        Optional<Produto> produto = pRepository.findById(id);
        if (produto.isPresent()){
            pRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }




}
