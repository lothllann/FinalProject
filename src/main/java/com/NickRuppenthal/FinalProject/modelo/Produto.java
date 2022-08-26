package com.NickRuppenthal.FinalProject.modelo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Table
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data // => @Getter, @Setter, @ToString, @EqualsAndHashCode
public class Produto {


    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String description;
    private Double price;


    public Produto(String name, String description, Double price) {
        this.name = name;
        this.description = description;
        this.price = price;
    }








}


