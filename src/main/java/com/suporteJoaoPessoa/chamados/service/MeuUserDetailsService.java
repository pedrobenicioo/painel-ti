package com.suporteJoaoPessoa.chamados.service;

import com.suporteJoaoPessoa.chamados.model.entity.AgenteDeTi;
import com.suporteJoaoPessoa.chamados.repository.AgenteDeTiRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MeuUserDetailsService implements UserDetailsService {

    private final AgenteDeTiRepository agenteRepository;

    public MeuUserDetailsService(AgenteDeTiRepository agenteRepository) {
        this.agenteRepository = agenteRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String matricula) throws UsernameNotFoundException {
        AgenteDeTi agente = agenteRepository.findByMatricula(matricula)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado: " + matricula));

        // Defina as roles do usuário aqui — exemplo padrão ROLE_USER
        List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_USER"));

        // Retorna um UserDetails do Spring Security com matricula, senha (criptografada) e roles
        return new User(agente.getMatricula(), agente.getSenha(), authorities);
    }
}
