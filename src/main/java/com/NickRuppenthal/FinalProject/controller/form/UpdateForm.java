package com.NickRuppenthal.FinalProject.controller.form;

import com.NickRuppenthal.FinalProject.modelo.Produto;
import com.NickRuppenthal.FinalProject.repository.ProtdutoRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateForm {

    private String description;

    private String name;

    private Double price;


    public Produto atualizar(Integer id, ProtdutoRepository pRepository){
        Produto produto = pRepository.getOne(id);

        produto.setDescription(this.description);
        produto.setPrice(this.price);
        produto.setName(this.name);

        return produto;
    }
}
