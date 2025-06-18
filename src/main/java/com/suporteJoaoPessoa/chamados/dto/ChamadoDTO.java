package com.suporteJoaoPessoa.chamados.dto;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
public class ChamadoDTO {

    private Long id;
    private String titulo;
    private String descricao;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime dataCriacao;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime dataConclusao;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime dataAssumido;
    private String status;
    private String nomeSolicitante;
    private String emailSolicitante;
    private String numeroSolicitante;
    private String agenciaSolicitante;
    private String agenteDeTi;  // só o nome do agente

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public String getNumeroSolicitante() {
        return numeroSolicitante;
    }

    public void setNumeroSolicitante(String numeroSolicitante) {
        this.numeroSolicitante = numeroSolicitante;
    }


    public String getAgenciaSolicitante() {
        return agenciaSolicitante;
    }

    public void setAgenciaSolicitante(String agenciaSolicitante) {
        this.agenciaSolicitante = agenciaSolicitante;
    }

    public String getAgenteDeTi() {
        return agenteDeTi;
    }

    public void setAgenteDeTi(String agenteDeTi) {
        this.agenteDeTi = agenteDeTi;
    }
}