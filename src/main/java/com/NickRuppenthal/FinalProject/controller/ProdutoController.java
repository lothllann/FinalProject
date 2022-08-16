package com.NickRuppenthal.FinalProject.controller;


import com.NickRuppenthal.FinalProject.controller.dto.ProdutoDto;
import com.NickRuppenthal.FinalProject.controller.form.ProdutoForm;
import com.NickRuppenthal.FinalProject.controller.form.SearchForm;
import com.NickRuppenthal.FinalProject.controller.form.UpdateForm;
import com.NickRuppenthal.FinalProject.modelo.Produto;
import com.NickRuppenthal.FinalProject.repository.ProtdutoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/products")
public class ProdutoController {


    @Autowired
    private ProtdutoRepository pRepository;



    @GetMapping
    public List<ProdutoDto> list(){
        List<Produto> produtos = pRepository.findAll();
        return ProdutoDto.converter(produtos);
    }

//    @GetMapping("/search")
//    public List<ProdutoDto> search(@RequestBody SearchForm search){
//        List<Produto> produtos = pRepository.findAll();
//
//    }



    @GetMapping("/{id}")
    public ResponseEntity<ProdutoDto> findById(@PathVariable Integer id){
        Optional<Produto> produto = pRepository.findById(id);
        if (produto.isPresent()){
            return ResponseEntity.ok(new ProdutoDto(produto.get()));
        }

        return ResponseEntity.notFound().build();
    }

    @PostMapping
    @Transactional
    public ResponseEntity<ProdutoDto> create(@RequestBody  ProdutoForm pForm, UriComponentsBuilder uBuilder){
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
