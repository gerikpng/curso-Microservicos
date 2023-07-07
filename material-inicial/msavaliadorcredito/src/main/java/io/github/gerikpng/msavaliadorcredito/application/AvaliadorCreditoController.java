package io.github.gerikpng.msavaliadorcredito.application;

import io.github.gerikpng.msavaliadorcredito.domain.model.SituacaoCliente;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/avaliador")
@RequiredArgsConstructor
public class AvaliadorCreditoController {

    private final AvaliadorCreditoService avaliadorCreditoService;

    @GetMapping
    public String status(){
        return "ok";
    }

    @GetMapping(params = "cpf")
    public ResponseEntity<SituacaoCliente> consultaSituacao(@RequestParam("cpf") String cpf){
        SituacaoCliente situacaocli = avaliadorCreditoService.obterSituacaoCliente(cpf);
        if(situacaocli == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(situacaocli);
    }

}
