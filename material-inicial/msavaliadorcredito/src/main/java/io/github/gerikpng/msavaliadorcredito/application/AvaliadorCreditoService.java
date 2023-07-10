package io.github.gerikpng.msavaliadorcredito.application;

import feign.FeignException;
import io.github.gerikpng.msavaliadorcredito.application.ex.DadosClienteNotFoundException;
import io.github.gerikpng.msavaliadorcredito.application.ex.ErroComunicacaoMicroservicesException;
import io.github.gerikpng.msavaliadorcredito.domain.model.*;
import io.github.gerikpng.msavaliadorcredito.infra.clients.CartoesResourceClient;
import io.github.gerikpng.msavaliadorcredito.infra.clients.ClienteResourceClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class  AvaliadorCreditoService {

    private final ClienteResourceClient clienteClient;

    private final CartoesResourceClient cartaoClient;
    public SituacaoCliente obterSituacaoCliente(String cpf) throws DadosClienteNotFoundException,ErroComunicacaoMicroservicesException {


        try {


            ResponseEntity<DadosCliente> responseCliente = clienteClient.dados(cpf);
            ResponseEntity<List<CartoesCliente>> responseCartao = cartaoClient.getCartoesByCliente(cpf);


            return SituacaoCliente
                    .builder()
                    .cliente(responseCliente.getBody())
                    .cartoes(responseCartao.getBody())
                    .build();
        }catch (FeignException.FeignClientException e){
            int status = e.status();

            if(HttpStatus.NOT_FOUND.value() == status){
                throw new DadosClienteNotFoundException();
            }
            throw new ErroComunicacaoMicroservicesException(e.getMessage(),e.status());
        }
    }
    public RetornoAvaliacaoCliente realizarAvaliacao(String cpf, Long renda) throws DadosClienteNotFoundException, ErroComunicacaoMicroservicesException {
        try {


            ResponseEntity<DadosCliente> responseCliente = clienteClient.dados(cpf);

            ResponseEntity<List<Cartao>> cartoesRendaAte = cartaoClient.getCartoesRendaAte(renda);
            List<Cartao> cartoes = cartoesRendaAte.getBody();
            var listaCartoesAprovados = cartoes.stream().map(cartao -> {
                DadosCliente dadosCliente = responseCliente.getBody();


                BigDecimal limiteBasico = cartao.getLimiteBasico();
                BigDecimal idadeBD = BigDecimal.valueOf(dadosCliente.getIdade());

                var fator = idadeBD.divide(BigDecimal.valueOf(10));
                BigDecimal limiteAprovado = fator.multiply(limiteBasico);

                CartaoAprovado aprovado = new CartaoAprovado();
                aprovado.setLimiteAprovado(limiteAprovado);
                aprovado.setBandeira(cartao.getBandeira());
                aprovado.setCartao(aprovado.getCartao());
                return aprovado;

            }).collect(Collectors.toList());
            return new RetornoAvaliacaoCliente(listaCartoesAprovados);
        } catch (FeignException.FeignClientException e) {
            int status = e.status();

            if (HttpStatus.NOT_FOUND.value() == status) {
                throw new DadosClienteNotFoundException();
            }
            throw new ErroComunicacaoMicroservicesException(e.getMessage(), e.status());
        }
    }
}
