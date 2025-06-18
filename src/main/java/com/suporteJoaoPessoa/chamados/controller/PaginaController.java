package com.suporteJoaoPessoa.chamados.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PaginaController {

    @GetMapping("/abrir-chamado")
    public String abrirChamado() {
        return "formulario-chamado";
    }
    @GetMapping("/cadastro")
    public String cadastro() {
        return "cadastro";
    }
}
