package io.github.gerikpng.mscartoes.application;

import io.github.gerikpng.mscartoes.application.representation.CartaoSaveRequest;
import io.github.gerikpng.mscartoes.domain.Cartao;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cartoes")
@RequiredArgsConstructor
public class cartoesResource {


    private final CartaoService service;
    @GetMapping
    public String status(){
        return "ok";
    }

    @PostMapping
    public ResponseEntity cadastra(@RequestBody CartaoSaveRequest request){
        Cartao cartaoModel = request.toModel();
    service.save(cartaoModel);
    return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
