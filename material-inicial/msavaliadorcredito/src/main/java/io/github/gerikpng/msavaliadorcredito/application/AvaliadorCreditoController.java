package io.github.gerikpng.msavaliadorcredito.application;

import io.github.gerikpng.msavaliadorcredito.application.ex.DadosClienteNotFoundException;
import io.github.gerikpng.msavaliadorcredito.application.ex.ErroComunicacaoMicroservicesException;
import io.github.gerikpng.msavaliadorcredito.domain.model.DadosAvaliacao;
import io.github.gerikpng.msavaliadorcredito.domain.model.RetornoAvaliacaoCliente;
import io.github.gerikpng.msavaliadorcredito.domain.model.SituacaoCliente;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


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
    public ResponseEntity consultaSituacao(@RequestParam("cpf") String cpf){

        try {
            SituacaoCliente situacaocli = avaliadorCreditoService.obterSituacaoCliente(cpf);
            return ResponseEntity.ok(situacaocli);
        } catch (DadosClienteNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (ErroComunicacaoMicroservicesException e) {
            return ResponseEntity.status(HttpStatus.resolve(e.getStatus())).body(e.getMessage());
        }

    }
    @PostMapping
    public ResponseEntity realizarAvaliacao(@RequestBody DadosAvaliacao dados){
        try {
            RetornoAvaliacaoCliente retorno = avaliadorCreditoService.realizarAvaliacao(dados.getCpf(),dados.getRenda());
            return ResponseEntity.ok(retorno);
        } catch (DadosClienteNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (ErroComunicacaoMicroservicesException e) {
            return ResponseEntity.status(HttpStatus.resolve(e.getStatus())).body(e.getMessage());
        }

    }

}
