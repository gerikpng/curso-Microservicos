package io.github.gerikpng.msavaliadorcredito.infra.clients;


import io.github.gerikpng.msavaliadorcredito.domain.model.Cartao;
import io.github.gerikpng.msavaliadorcredito.domain.model.CartoesCliente;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(value = "mscartoes", path = "/cartoes")
public interface CartoesResourceClient {

    @GetMapping(params = "cpf")
    ResponseEntity<List<CartoesCliente>> getCartoesByCliente(@RequestParam("cpf") String cpf);

    @GetMapping(params="renda")
    ResponseEntity<List<Cartao>> getCartoesRendaAte(@RequestParam("renda") Long renda);
}
