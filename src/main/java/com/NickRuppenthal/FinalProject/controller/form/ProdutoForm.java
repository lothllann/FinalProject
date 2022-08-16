package com.NickRuppenthal.FinalProject.controller.form;

import com.NickRuppenthal.FinalProject.modelo.Produto;
import com.NickRuppenthal.FinalProject.repository.ProtdutoRepository;

public class ProdutoForm {

    private String description;

    private String name;

    private Double price;



    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Produto converter(ProtdutoRepository pRepository){
        return new Produto(name, description, price);
    }
}
