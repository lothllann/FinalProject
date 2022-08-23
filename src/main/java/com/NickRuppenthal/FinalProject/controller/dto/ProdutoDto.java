package com.NickRuppenthal.FinalProject.controller.dto;

import com.NickRuppenthal.FinalProject.modelo.Produto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class ProdutoDto {

    private String description;
    private Integer id;
    private String name;
    private Double price;



    public ProdutoDto(Produto produto) {
        this.description = produto.getDescription();
        this.id = produto.getId();
        this.name = produto.getName();
        this.price = produto.getPrice();
    }


    public static List<ProdutoDto> converter(List<Produto> produtos) {
        return produtos.stream().map(ProdutoDto::new).collect(Collectors.toList());
    }
}
