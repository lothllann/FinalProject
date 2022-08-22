package com.NickRuppenthal.FinalProject.controller.form;

import com.NickRuppenthal.FinalProject.controller.dto.ProdutoDto;
import com.NickRuppenthal.FinalProject.modelo.Produto;
import com.NickRuppenthal.FinalProject.repository.ProtdutoRepository;

public class UpdateForm {

    private String description;

    private String name;

    private Double price;


    public UpdateForm(String description, String name, Double price) {
        this.description = description;
        this.name = name;
        this.price = price;
    }

    public UpdateForm(){}

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }

    public Double getPrice() {
        return price;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Produto atualizar(Integer id, ProtdutoRepository pRepository){
        Produto produto = pRepository.getOne(id);

        produto.setDescription(this.description);
        produto.setPrice(this.price);
        produto.setName(this.name);

        return produto;
    }
}
