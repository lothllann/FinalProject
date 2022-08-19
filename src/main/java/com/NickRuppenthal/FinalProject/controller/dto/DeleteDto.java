package com.NickRuppenthal.FinalProject.controller.dto;

import com.NickRuppenthal.FinalProject.modelo.Produto;

public class DeleteDto {

    private Integer id;
    private String mensagem;
    private Produto produto;

    public DeleteDto(Integer id, String mensagem, Produto produto) {
        this.id = id;
        this.mensagem = mensagem;
        this.produto = produto;
    }

    public DeleteDto(Integer id, String mensagem) {
        this.id = id;
        this.mensagem = mensagem;
    }

    public Integer getId() {
        return id;
    }

    public String getMensagem() {
        return mensagem;
    }


}
