package com.suporteJoaoPessoa.chamados.controller;


import com.suporteJoaoPessoa.chamados.model.entity.AgenteDeTi;
import com.suporteJoaoPessoa.chamados.model.entity.Chamado;
import com.suporteJoaoPessoa.chamados.repository.AgenteDeTiRepository;
import com.suporteJoaoPessoa.chamados.repository.ChamadoRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
public class AuthController {

    @Autowired
    private AgenteDeTiRepository agenteRepository;
    @Autowired
    private ChamadoRepository chamadoRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/login")
    public String exibirLogin() {
        return "login";
    }

    @PostMapping("/doLogin")
    public String realizarLogin(@RequestParam String matricula,
                                @RequestParam String senha,
                                Model model,
                                HttpSession session) {
        Optional<AgenteDeTi> optionalAgente = agenteRepository.findByMatricula(matricula);

        if (optionalAgente.isPresent()) {
            AgenteDeTi agente = optionalAgente.get();
            if (passwordEncoder.matches(senha, agente.getSenha())) {
                session.setAttribute("agenteLogado", agente);
                return "redirect:/painel";
            } else {
                // Matrícula encontrada, senha incorreta
                model.addAttribute("erro", "Senha incorreta");
                return "login";
            }
        }

        // Matrícula não encontrada
        model.addAttribute("erro", "Matrícula inválida");
        return "login";
    }


}
