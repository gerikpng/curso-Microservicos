package io.github.gerikpng.msavaliadorcredito.application;

import io.github.gerikpng.msavaliadorcredito.domain.model.DadosCliente;
import io.github.gerikpng.msavaliadorcredito.domain.model.SituacaoCliente;
import io.github.gerikpng.msavaliadorcredito.infra.clients.ClienteResourceClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class  AvaliadorCreditoService {

    private final ClienteResourceClient clienteClient;
    public SituacaoCliente obterSituacaoCliente(String cpf) {
        ResponseEntity<DadosCliente> responseCliente = clienteClient.dados(cpf);

        return SituacaoCliente
                .builder()
                .cliente(responseCliente.getBody())
                .build();
    }
}
