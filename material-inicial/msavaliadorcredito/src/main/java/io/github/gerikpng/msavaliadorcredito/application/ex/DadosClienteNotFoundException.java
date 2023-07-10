package io.github.gerikpng.msavaliadorcredito.application.ex;

public class DadosClienteNotFoundException extends Exception{
    public DadosClienteNotFoundException(){
        super ("NENHUM CLIENTE VINCULADO AO CPF");
    }
}
