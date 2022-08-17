package com.NickRuppenthal.FinalProject.repository;

import com.NickRuppenthal.FinalProject.modelo.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProtdutoRepository extends JpaRepository<Produto, Integer> {

    @Query("SELECT p FROM Produto p WHERE p.name = :name")
    Produto findByName(@Param("name") String produtoName);


    @Query("SELECT p FROM Produto p WHERE p.price >= :price")
    List<Produto> findByPrice(@Param("price") Double produtoPrice);


    @Query("SELECT p FROM Produto p WHERE p.price <= :price")
    List<Produto> findByPriceLessThan(@Param("price") Double produtoPrice);
}
