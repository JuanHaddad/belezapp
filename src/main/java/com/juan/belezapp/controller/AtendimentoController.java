package com.juan.belezapp.controller;

import com.juan.belezapp.entity.Atendimento;
import com.juan.belezapp.entity.Servico;
import com.juan.belezapp.repository.AtendimentoRepository;
import com.juan.belezapp.repository.ClienteRepository;
import com.juan.belezapp.repository.ServicoRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/atendimentos")
public class AtendimentoController {

    private final AtendimentoRepository atendimentoRepository;
    private final ClienteRepository clienteRepository;
    private final ServicoRepository servicoRepository;

    public AtendimentoController(AtendimentoRepository atendimentoRepository, ClienteRepository clienteRepository, ServicoRepository servicoRepository) {
        this.atendimentoRepository = atendimentoRepository;
        this.clienteRepository = clienteRepository;
        this.servicoRepository = servicoRepository;
    }

    @GetMapping
    public List<Atendimento> listarTodos() {
        return atendimentoRepository.findAll();
    }

    @GetMapping("/cliente/{clienteId}")
    public List<Atendimento> listarPorCliente(@PathVariable Long clienteId) {
        return atendimentoRepository.findByCliente_Id(clienteId);
    }

    @PostMapping
    public Atendimento criarAtendimento(@RequestBody Atendimento atendimento) {
        // Validar cliente
        var cliente = clienteRepository.findById(atendimento.getCliente().getId())
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));
        atendimento.setCliente(cliente);

        // Validar serviços
        List<Servico> servicosValidados = atendimento.getServicos().stream()
                .map(servico -> servicoRepository.findById(servico.getId())
                        .orElseThrow(() -> new RuntimeException("Serviço não encontrado: id " + servico.getId())))
                .toList();

        atendimento.setServicos(servicosValidados);

        // Salvar atendimento
        return atendimentoRepository.save(atendimento);
    }

    @GetMapping("/{id}")
    public Atendimento buscarPorId(@PathVariable Long id) {
        return atendimentoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Atendimento não encontrado"));
    }

    @DeleteMapping("/{id}")
    public void deletarAtendimento(@PathVariable Long id) {
        Atendimento atendimento = atendimentoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Atendimento não encontrado"));

        atendimentoRepository.delete(atendimento);
    }
}
