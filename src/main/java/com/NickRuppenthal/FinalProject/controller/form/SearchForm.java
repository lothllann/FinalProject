package com.NickRuppenthal.FinalProject.controller.form;

import com.NickRuppenthal.FinalProject.modelo.Produto;
import com.NickRuppenthal.FinalProject.repository.ProtdutoRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public class SearchForm {

    private Double max_price;
    private Double min_price;
    private String q;

    public Double getMax_price() {
        return max_price;
    }

    public Double getMin_price() {
        return min_price;
    }

    public String getQ() {
        return q;
    }


}
