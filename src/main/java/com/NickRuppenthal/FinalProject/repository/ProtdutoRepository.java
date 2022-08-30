package com.NickRuppenthal.FinalProject.repository;

import com.NickRuppenthal.FinalProject.modelo.Produto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProtdutoRepository extends JpaRepository<Produto, Integer> {

    @Query("SELECT p FROM Produto p WHERE p.price >= :bprice or p.price <= :lprice or p.name = :name ")
    Page<Produto> findProdutoByPriceByName(@Param("bprice") Double biggerPrice,
                                           @Param("lprice") Double lesserPrice,
                                           @Param("name") String name,
                                           Pageable pageable);

    Page<Produto> findAll(Pageable pageable);
}
