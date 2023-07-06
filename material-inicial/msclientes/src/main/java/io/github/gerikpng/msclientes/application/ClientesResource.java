package io.github.gerikpng.msclientes.application;

import io.github.gerikpng.msclientes.Domain.Cliente;
import io.github.gerikpng.msclientes.application.representation.ClienteSaveRequest;
import io.github.gerikpng.msclientes.infra.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/clientes")
@RequiredArgsConstructor
@Slf4j
public class ClientesResource {


    private final ClienteService service;

    @GetMapping
    public String status(){
        log.info("obtendo status");
        return "ok";
    }

    @PostMapping
    public ResponseEntity salvar(@RequestBody ClienteSaveRequest request){
        log.info("salvando");
        Cliente clienteModel = request.toModel();
        service.save(clienteModel);
        URI headerLocation = ServletUriComponentsBuilder // CONSTRUIR URL DINAMICA
                .fromCurrentRequest().query("cpf={cpf}")
                .buildAndExpand(clienteModel.getCpf())
                .toUri();
                return ResponseEntity.created(headerLocation).build();
    }

    @GetMapping(params = "cpf")
    public ResponseEntity dados(@RequestParam("cpf") String cpf){
        var clienteModel = service.getByCPF(cpf);
        if(clienteModel.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(clienteModel);
    }
}
