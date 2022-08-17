package com.NickRuppenthal.FinalProject.repository;

import com.NickRuppenthal.FinalProject.modelo.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProtdutoRepository extends JpaRepository<Produto, Integer> {

    @Query("SELECT p FROM Produto p WHERE p.name = :name")
    Produto findByName(@Param("name") String produtoName);


    Produto findByPriceGreaterThan(@Param("price") Double produtoPrice);


    Produto findByPriceLessThan(@Param("price") Double produtoPrice);
}
