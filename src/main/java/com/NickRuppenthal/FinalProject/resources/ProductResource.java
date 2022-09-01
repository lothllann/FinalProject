package com.NickRuppenthal.FinalProject.resources;


import com.NickRuppenthal.FinalProject.modelo.Produto;
import com.NickRuppenthal.FinalProject.modelo.dto.DeleteDto;
import com.NickRuppenthal.FinalProject.modelo.dto.ProdutoDto;
import com.NickRuppenthal.FinalProject.services.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;

@RestController
@RequestMapping("/products")
public class ProductResource {

    @Autowired
    private ProductService service;
    private ModelMapper mapper = new ModelMapper();

    @GetMapping
    @Cacheable(value="lista_de_produtos")
    public ResponseEntity<Page<ProdutoDto>> list(){
        return ResponseEntity.ok(ProdutoDto.converter(service.list().get()));
    }

    @GetMapping("/{id}")
    @Cacheable(value="lista_de_produtos")
    public ResponseEntity<ProdutoDto> findById(@PathVariable Integer id){
            return ResponseEntity.ok(new ProdutoDto(service.findById(id).get()));
    }

    @GetMapping("/search")
    @Cacheable(value="lista_de_produtos")
    public ResponseEntity<Page<ProdutoDto>> search(@RequestParam(required = false) Double max_price,
                                                   @RequestParam(required = false) Double min_price,
                                                   @RequestParam(required = false) String name){

        Page<Produto> resultadoDaBusca = service.search(max_price, min_price, name);
        return ResponseEntity.ok(ProdutoDto.converter(resultadoDaBusca));
    }

    @PostMapping
    @Transactional
    @CacheEvict(value = "lista_de_produtos", allEntries = true)
    public ResponseEntity<ProdutoDto> create(@RequestBody  ProdutoDto pDto, UriComponentsBuilder uBuilder ){
        Produto produto = service.create(pDto);
        return ResponseEntity.created(uBuilder.path("/products/{id}")
                             .buildAndExpand(produto.getId()).toUri())
                             .body(new ProdutoDto(produto));
    }

    @PutMapping("/{id}")
    @Transactional
    @CacheEvict(value = "lista_de_produtos", allEntries = true)
    public ResponseEntity<ProdutoDto> update(@PathVariable Integer id, @RequestBody  ProdutoDto pDto){
        pDto.setId(id);
        return ResponseEntity.ok().body(mapper.map(service.update(id, pDto), ProdutoDto.class));
    }


    @DeleteMapping("/{id}")
    @Transactional
    @CacheEvict(value = "lista_de_produtos", allEntries = true)
    public ResponseEntity<DeleteDto> delete(@PathVariable Integer id){
        return ResponseEntity.ok(service.delete(id));
    }



}
