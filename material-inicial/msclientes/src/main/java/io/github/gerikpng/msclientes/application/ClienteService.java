package io.github.gerikpng.msclientes.application;

import io.github.gerikpng.msclientes.infra.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import io.github.gerikpng.msclientes.Domain.Cliente;

import java.util.Optional;
import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor //RESUME A CRIAÇÃO DE UM CONSTRUTOR PARA O repository APENAS NESSE COMANDO
public class ClienteService {


    private final ClienteRepository repository;

    @Transactional
    public Cliente save(Cliente cliente){
        return repository.save(cliente);
    }

    public Optional<Cliente> getByCPF(String cpf){
        return repository.findByCpf(cpf);
    }
}
