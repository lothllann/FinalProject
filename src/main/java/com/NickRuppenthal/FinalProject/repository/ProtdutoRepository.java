package com.NickRuppenthal.FinalProject.repository;

import com.NickRuppenthal.FinalProject.modelo.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProtdutoRepository extends JpaRepository<Produto, Integer> {

    @Query("SELECT p FROM Produto p WHERE p.price >= :bprice or p.price <= :lprice or p.name = :name ")
    List<Produto> findProdutoByPriceByName(@Param("bprice") Double biggerPrice, @Param("lprice") Double lesserPrice, @Param("name") String name );


}
