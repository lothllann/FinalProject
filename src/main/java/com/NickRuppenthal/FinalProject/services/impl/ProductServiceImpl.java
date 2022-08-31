package com.NickRuppenthal.FinalProject.services.impl;

import com.NickRuppenthal.FinalProject.config.exceptions.MethodArgumentNotValidException;
import com.NickRuppenthal.FinalProject.config.exceptions.NotFoundException;
import com.NickRuppenthal.FinalProject.modelo.Produto;
import com.NickRuppenthal.FinalProject.modelo.dto.DeleteDto;
import com.NickRuppenthal.FinalProject.modelo.dto.ProdutoDto;
import com.NickRuppenthal.FinalProject.repository.ProtdutoRepository;
import com.NickRuppenthal.FinalProject.services.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    private ModelMapper mapper = new ModelMapper();
    private ProtdutoRepository pRepository;

    @Autowired
    public ProductServiceImpl(ProtdutoRepository pRepository){
        this.pRepository = pRepository;
    }


    Sort sort = Sort.by("name").ascending();
    Pageable paginacao = PageRequest.of(0,7, sort);

    @Override
    public Optional<Page<Produto>> list() {
        return Optional.ofNullable(Optional.of(pRepository.findAll(paginacao)).orElseThrow(() -> new NotFoundException("Nenhum Produto encontrado")));
    }

    @Override
    public Optional<Produto> findById(Integer id) {
        return Optional.of(pRepository.findById(id).orElseThrow(()-> new NotFoundException("Produto não encontrado")));
    }

    @Override
    public Page<Produto> search(Double max, Double min, String name) {
        Page<Produto> resultadoDaBusca = pRepository.findProdutoByPriceByName(max, min, name, paginacao);
        if(resultadoDaBusca.isEmpty()){throw new NotFoundException("Nenhum Produto encontrado");}
        return resultadoDaBusca;
    }

    @Override
    public Produto create(ProdutoDto pDto) {
        checkForm(pDto);
        Produto produto = pDto.transformar(pRepository);
        pRepository.save(produto);
        return produto;
    }

    @Override
    public Produto update(Integer id, ProdutoDto pDto) {
        checkForm(pDto);
        Optional.of(pRepository.findById(id).orElseThrow(()-> new NotFoundException("Produto não encontrado")));
        return pRepository.save(mapper.map(pDto, Produto.class));
    }

    @Override
    public DeleteDto delete(Integer id) {
        Optional.of(pRepository.findById(id).orElseThrow(()-> new NotFoundException("Produto não encontrado")));
        pRepository.deleteById(id);
        return new DeleteDto(200,"Item "+ id +" excluido com sucesso");
    }


    private void checkForm(ProdutoDto formulario){
        if (formulario.getName().isEmpty()){
            throw new MethodArgumentNotValidException("O Nome do produto é obrigatório");
        }
        if (formulario.getDescription().isEmpty()){
            throw new MethodArgumentNotValidException("A Descrição do produto é obrigatória");
        }
        if (formulario.getPrice() == null){
            throw new MethodArgumentNotValidException("O Preço do produto é obrigatório");
        }
    }


}
