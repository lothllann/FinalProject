package com.NickRuppenthal.FinalProject.controller.form;

import com.NickRuppenthal.FinalProject.modelo.Produto;
import com.NickRuppenthal.FinalProject.repository.ProtdutoRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public class SearchForm {

    private String max_price;
    private String min_price;
    private String q;

    public Double getMax_price() {
        return  Double.parseDouble(max_price);
    }

    public Double getMin_price() {
        return Double.parseDouble(min_price);
    }

    public String getQ() {
        return q;
    }


}
