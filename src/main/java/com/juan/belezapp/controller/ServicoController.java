package com.juan.belezapp.controller;

import com.juan.belezapp.entity.Servico;
import com.juan.belezapp.repository.ServicoRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/servicos")
public class ServicoController {

    private final ServicoRepository servicoRepository;

    public ServicoController(ServicoRepository servicoRepository) {
        this.servicoRepository = servicoRepository;
    }

    @GetMapping
    public List<Servico> listarTodos() {
        return servicoRepository.findAll();
    }

    @PostMapping
    public Servico criarServico(@RequestBody Servico servico) {
        return servicoRepository.save(servico);
    }

    @GetMapping("/{id}")
    public Servico buscarPorId(@PathVariable Long id) {
        return servicoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Serviço não encontrado"));
    }

    @DeleteMapping("/{id}")
    public void deletarServico(@PathVariable Long id) {
        Servico servico = servicoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Serviço não encontrado"));

        servicoRepository.delete(servico);
    }
}
