package com.daniloalalmeida.estudosjava.repositories;

import com.daniloalalmeida.estudosjava.domain.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnderecoRepository extends JpaRepository<Endereco, Integer> {
}
