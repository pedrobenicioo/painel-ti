package com.suporteJoaoPessoa.chamados.model.entity;

import com.suporteJoaoPessoa.chamados.model.enums.StatusChamado;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "chamados")
public class Chamado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;

    @Column(columnDefinition = "TEXT")
    private String descricao;


    private LocalDateTime dataCriacao;
    private LocalDateTime dataAtualizacao;
    private LocalDateTime dataConclusao;
    private LocalDateTime dataAssumido;


    @Enumerated(EnumType.STRING)
    private StatusChamado status;
    private String prioridade;     // Baixa, Média, Alta
    private String tipoChamado;    // Suporte, Infraestrutura, etc.


    private String nomeSolicitante;
    private String emailSolicitante;
    private String numeroSolicitante;
    private String setorOrigem;
    private String AgenciaSolicitante;

    @ManyToOne
    @JoinColumn(name = "agente_de_ti_id")
    private AgenteDeTi agenteDeTi;


    private String responsavel;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public LocalDateTime getDataAtualizacao() {
        return dataAtualizacao;
    }

    public void setDataAtualizacao(LocalDateTime dataAtualizacao) {
        this.dataAtualizacao = dataAtualizacao;
    }

    public LocalDateTime getDataConclusao() {
        return dataConclusao;
    }

    public void setDataConclusao(LocalDateTime dataConclusao) {
        this.dataConclusao = dataConclusao;
    }

    public LocalDateTime getDataAssumido() {
        return dataAssumido;
    }

    public void setDataAssumido(LocalDateTime dataAssumido) {
        this.dataAssumido = dataAssumido;
    }

    public StatusChamado getStatus() {
        return status;
    }

    public void setStatus(StatusChamado status) {
        this.status = status;
    }

    public String getPrioridade() {
        return prioridade;
    }

    public void setPrioridade(String prioridade) {
        this.prioridade = prioridade;
    }

    public String getTipoChamado() {
        return tipoChamado;
    }

    public void setTipoChamado(String tipoChamado) {
        this.tipoChamado = tipoChamado;
    }

    public String getNomeSolicitante() {
        return nomeSolicitante;
    }

    public void setNomeSolicitante(String nomeSolicitante) {
        this.nomeSolicitante = nomeSolicitante;
    }

    public String getEmailSolicitante() {
        return emailSolicitante;
    }

    public void setEmailSolicitante(String emailSolicitante) {
        this.emailSolicitante = emailSolicitante;
    }

    public String getSetorOrigem() {
        return setorOrigem;
    }

    public void setSetorOrigem(String setorOrigem) {
        this.setorOrigem = setorOrigem;
    }

    public String getNumeroSolicitante() {
        return numeroSolicitante;
    }

    public void setNumeroSolicitante(String numeroSolicitante) {
        this.numeroSolicitante = numeroSolicitante;
    }

    public String getAgenciaSolicitante() {
        return AgenciaSolicitante;
    }

    public void setAgenciaSolicitante(String agenciaSolicitante) {
        AgenciaSolicitante = agenciaSolicitante;
    }

    public AgenteDeTi getAgenteDeTi() {
        return agenteDeTi;
    }

    public void setAgenteDeTi(AgenteDeTi agenteDeTi) {
        this.agenteDeTi = agenteDeTi;
    }

    public String getResponsavel() {
        return responsavel;
    }

    public void setResponsavel(String responsavel) {
        this.responsavel = responsavel;
    }
}
