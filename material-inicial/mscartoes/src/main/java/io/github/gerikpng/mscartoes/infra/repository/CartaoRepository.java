package io.github.gerikpng.mscartoes.infra.repository;

import io.github.gerikpng.mscartoes.domain.Cartao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface CartaoRepository extends JpaRepository<Cartao,Long> {

    List<Cartao> findByRendaLessThanEqual(BigDecimal renda);

    List<Cartao> findByNome(String nome);
}
