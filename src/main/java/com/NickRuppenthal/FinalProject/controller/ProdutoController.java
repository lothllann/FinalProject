package com.NickRuppenthal.FinalProject.controller;


import com.NickRuppenthal.FinalProject.config.exception.MethodArgumentNotValidException;
import com.NickRuppenthal.FinalProject.config.exception.NotFoundException;
import com.NickRuppenthal.FinalProject.controller.dto.DeleteDto;
import com.NickRuppenthal.FinalProject.controller.dto.ProdutoDto;
import com.NickRuppenthal.FinalProject.controller.form.ProdutoForm;
import com.NickRuppenthal.FinalProject.controller.form.SearchForm;
import com.NickRuppenthal.FinalProject.controller.form.UpdateForm;
import com.NickRuppenthal.FinalProject.modelo.Produto;
import com.NickRuppenthal.FinalProject.repository.ProtdutoRepository;

import com.NickRuppenthal.FinalProject.service.ProdutoService;
import com.fasterxml.jackson.databind.introspect.TypeResolutionContext;
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
    private ProdutoService pService;


    @GetMapping
    public ResponseEntity<List<ProdutoDto>> list(){
        List<Produto> produtos = pService.list();
        return ResponseEntity.ok(ProdutoDto.converter(produtos));
    }

    @GetMapping("/search")
    public ResponseEntity<List<ProdutoDto>> search(@RequestParam(required = false) Double max_price, @RequestParam(required = false) Double min_price, @RequestParam(required = false) String name){
        List<Produto> resultadoDaBusca = pService.search(max_price, min_price, name);
        return ResponseEntity.ok(ProdutoDto.converter(resultadoDaBusca));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProdutoDto> findById(@PathVariable Integer id){
        Produto produto = pService.findById(id);
        return ResponseEntity.ok(new ProdutoDto(produto));
    }

    @PostMapping
    @Transactional
    public ResponseEntity<ProdutoDto> create(@RequestBody  ProdutoForm pForm, UriComponentsBuilder uBuilder){
        Produto produto = pService.create(pForm);
        URI uri = uBuilder.path("/products/{id}").buildAndExpand(produto.getId()).toUri();
        return ResponseEntity.created(uri).body(new ProdutoDto(produto));
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<ProdutoDto> update(@PathVariable Integer id, @RequestBody UpdateForm uForm){
        Produto produtoAtt = pService.update(id, uForm);
        return ResponseEntity.ok(new ProdutoDto(produtoAtt));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public DeleteDto delete(@PathVariable Integer id){
        DeleteDto produto = pService.delete(id);
        return produto;
    }




}
