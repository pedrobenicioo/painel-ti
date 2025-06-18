package com.suporteJoaoPessoa.chamados.controller;

import com.suporteJoaoPessoa.chamados.dto.AgenteDeTiDTO;
import com.suporteJoaoPessoa.chamados.model.entity.AgenteDeTi;
import com.suporteJoaoPessoa.chamados.repository.AgenteDeTiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/agentes")
public class AgenteDeTiController {

    @Autowired
    private AgenteDeTiRepository agenteRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping
    public ResponseEntity<?> criarAgente(@RequestBody AgenteDeTi agente) {
        // Verifica se já existe agente com a mesma matrícula
        boolean existe = agenteRepository.existsByMatricula(agente.getMatricula());
        if (existe) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Matrícula já cadastrada");
        }

        // Criptografa a senha antes de salvar
        String senhaCriptografada = passwordEncoder.encode(agente.getSenha());
        agente.setSenha(senhaCriptografada);

        AgenteDeTi salvo = agenteRepository.save(agente);
        return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
    }

    @GetMapping
    public List<AgenteDeTiDTO> listarAgentes() {
        List<AgenteDeTi> agentes = agenteRepository.findAll();
        return agentes.stream().map(this::converterParaDTO).toList();
    }

    private AgenteDeTiDTO converterParaDTO(AgenteDeTi agente) {
        AgenteDeTiDTO dto = new AgenteDeTiDTO();
        dto.setId(agente.getId());
        dto.setNomeCompleto(agente.getNomeCompleto());
        dto.setMatricula(agente.getMatricula());
        dto.setTelefone(agente.getTelefone());
        dto.setEmail(agente.getEmail());
        return dto;
    }
}