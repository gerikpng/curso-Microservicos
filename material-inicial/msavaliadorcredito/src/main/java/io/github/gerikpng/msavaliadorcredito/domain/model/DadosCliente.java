package io.github.gerikpng.msavaliadorcredito.domain.model;

import lombok.Data;

import java.util.List;

@Data
public class DadosCliente {
    private long id;
    private String nome;
    private Integer idade;
    //private List<CartoesCliente> cartoes;
}