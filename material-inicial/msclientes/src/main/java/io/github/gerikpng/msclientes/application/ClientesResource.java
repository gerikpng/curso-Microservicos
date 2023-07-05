package io.github.gerikpng.msclientes.application;

import io.github.gerikpng.msclientes.Domain.Cliente;
import io.github.gerikpng.msclientes.application.representation.ClienteSaveRequest;
import io.github.gerikpng.msclientes.infra.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/clientes")
@RequiredArgsConstructor
public class ClientesResource {


    private final ClienteService service;

    @GetMapping
    public String status(){
        return "ok";
    }

    @PostMapping
    public ResponseEntity salvar(@RequestBody ClienteSaveRequest request){
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
        var clienteModel = service.getByCpf(cpf);
        if(clienteModel.isEmpty()){
            return ResponseEntity.NotFound().build();
        }
        return ResponseEntity.ok(clienteModel);
    }
}
