package com.NickRuppenthal.FinalProject.services;

import com.NickRuppenthal.FinalProject.modelo.Produto;
import com.NickRuppenthal.FinalProject.modelo.dto.DeleteDto;
import com.NickRuppenthal.FinalProject.modelo.dto.ProdutoDto;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    Optional<List<Produto>> list();
    Optional<Produto> findById(Integer id);
    List<Produto> search(Double max, Double min, String name);
    Produto create(ProdutoDto dDto);
    Produto update(Integer id, ProdutoDto pDto);
    DeleteDto delete(Integer id);


}
