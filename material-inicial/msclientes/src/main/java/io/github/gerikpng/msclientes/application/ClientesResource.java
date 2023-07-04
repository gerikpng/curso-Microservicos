package io.github.gerikpng.msclientes.application;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/clientes")
public class ClientesResource {

    @GetMapping
    public String status(){
        return "ok";
    }

    @PostMapping
    public String salvar(){
    return "";
    }
}
