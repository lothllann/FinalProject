package com.NickRuppenthal.FinalProject.resources;


import com.NickRuppenthal.FinalProject.modelo.Produto;
import com.NickRuppenthal.FinalProject.modelo.dto.DeleteDto;
import com.NickRuppenthal.FinalProject.modelo.dto.ProdutoDto;
import com.NickRuppenthal.FinalProject.services.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductResource {


    @Autowired
    private ProductService service;
    private ModelMapper mapper = new ModelMapper();

    @GetMapping
    public ResponseEntity<List<ProdutoDto>> list(){
        return ResponseEntity.ok(ProdutoDto.converter(service.list().get()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProdutoDto> findById(@PathVariable Integer id){
        return ResponseEntity.ok(new ProdutoDto(service.findById(id).get()));
    }

    @GetMapping("/search")
    public ResponseEntity<List<ProdutoDto>> search(@RequestParam(required = false) Double max_price,
                                                   @RequestParam(required = false) Double min_price,
                                                   @RequestParam(required = false) String name){

        List<Produto> resultadoDaBusca = service.search(max_price, min_price, name);
        return ResponseEntity.ok(ProdutoDto.converter(resultadoDaBusca));
    }

    @PostMapping
    @Transactional
    public ResponseEntity<ProdutoDto> create(@RequestBody ProdutoDto pDto, UriComponentsBuilder uBuilder ){
        Produto produto = service.create(pDto);
        return ResponseEntity.created(uBuilder.path("/products/{id}")
                             .buildAndExpand(produto.getId()).toUri())
                             .body(new ProdutoDto(produto));
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<ProdutoDto> update(@PathVariable Integer id, @RequestBody ProdutoDto pDto){
        pDto.setId(id);
        return ResponseEntity.ok().body(mapper.map(service.update(id, pDto), ProdutoDto.class));
    }


    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<DeleteDto> delete(@PathVariable Integer id){
        return ResponseEntity.ok(service.delete(id));
    }


}
