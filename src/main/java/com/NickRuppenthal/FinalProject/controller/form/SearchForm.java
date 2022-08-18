package com.NickRuppenthal.FinalProject.controller.form;

import com.NickRuppenthal.FinalProject.modelo.Produto;
import com.NickRuppenthal.FinalProject.repository.ProtdutoRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public class SearchForm {

    private String  max_price;
    private String min_price;
    private String q;

    public String getMax_price() {
        return  max_price;
    }

        public String getMin_price() {
        return min_price;
    }

    public String getQ() {
        return q;
    }


}
