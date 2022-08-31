package com.NickRuppenthal.FinalProject.modelo.dto;

import com.NickRuppenthal.FinalProject.modelo.Produto;
import com.NickRuppenthal.FinalProject.repository.ProtdutoRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Page;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProdutoDto {
    private Integer id;
    @NotBlank(message = "A Descrição é obrigatoria")
    private String description;
    @NotBlank(message = "O Nome é obrigatorio")
    private String name;

    private Double price;

    public ProdutoDto(String description, String name, Double price) {
        this.description = description;
        this.name = name;
        this.price = price;
    }

    public ProdutoDto(Produto produto) {
        this.description = produto.getDescription();
        this.id = produto.getId();
        this.name = produto.getName();
        this.price = produto.getPrice();
    }


    public static Page<ProdutoDto> converter(Page<Produto> produtos) {
        return produtos.map(ProdutoDto::new);
             //produtos.stream().map(ProdutoDto::new).collect(Collectors.toList());
    }

    public Produto transformar(ProtdutoRepository pRepository){
        return new Produto(name, description, price);
    }

}
