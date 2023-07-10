package io.github.gerikpng.mscartoes.application;

import io.github.gerikpng.mscartoes.application.representation.CartaoSaveRequest;
import io.github.gerikpng.mscartoes.domain.Cartao;
import io.github.gerikpng.mscartoes.domain.CartoesPorClienteResponse;
import io.github.gerikpng.mscartoes.domain.ClienteCartao;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/cartoes")
@RequiredArgsConstructor
public class cartoesResource {


    private final CartaoService cartaoService;
    private final ClienteCartaoService clientecartaoService;
    @GetMapping
    public String status(){
        return "ok";
    }

    @PostMapping
    public ResponseEntity cadastra(@RequestBody CartaoSaveRequest request){
        Cartao cartaoModel = request.toModel();
        cartaoService.save(cartaoModel);
    return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    @GetMapping(params = "cpf")
    public ResponseEntity<List<CartoesPorClienteResponse>> getCartoesByCliente(@RequestParam("cpf") String cpf){
        List<ClienteCartao> cartaoModel = clientecartaoService.listCartoesByCpf(cpf);
        List<CartoesPorClienteResponse> resultList = cartaoModel.stream().map(CartoesPorClienteResponse::fromModel).collect(Collectors.toList());
       // if(cartaoModel.isEmpty()){
         //   return ResponseEntity.notFound().build();
        //}
        return ResponseEntity.ok(resultList);
    }
    @GetMapping(params="renda")
    public ResponseEntity<List<Cartao>> getCartoesRendaAte(@RequestParam("renda") Long renda){
        List<Cartao> lista = cartaoService.getCartoesRendaMenorIgual(renda);
        return ResponseEntity.ok(lista);
    }
}
