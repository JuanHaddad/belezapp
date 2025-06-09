package com.juan.belezapp.repository;

import com.juan.belezapp.entity.Atendimento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AtendimentoRepository extends JpaRepository<Atendimento, Long> {

    // Aqui você deve declarar:
    List<Atendimento> findByCliente_Id(Long clienteId);
}