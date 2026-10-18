package com.talentosemissoes.talentos.entity;

import java.time.OffsetDateTime;
import java.util.UUID;

public class talentos {
    UUID id;
    UUID idAuth;
    String nome;
    String sobrenome;
    String sobre;
    OffsetDateTime dataNascimento;
    String habilidades;
    String eperiencias;
    String linkPortifolio;

    public talentos() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getIdAuth() {
        return idAuth;
    }

    public void setIdAuth(UUID idAuth) {
        this.idAuth = idAuth;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    public String getSobre() {
        return sobre;
    }

    public void setSobre(String sobre) {
        this.sobre = sobre;
    }

    public OffsetDateTime getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(OffsetDateTime dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getHabilidades() {
        return habilidades;
    }

    public void setHabilidades(String habilidades) {
        this.habilidades = habilidades;
    }

    public String getEperiencias() {
        return eperiencias;
    }

    public void setEperiencias(String eperiencias) {
        this.eperiencias = eperiencias;
    }

    public String getLinkPortifolio() {
        return linkPortifolio;
    }

    public void setLinkPortifolio(String linkPortifolio) {
        this.linkPortifolio = linkPortifolio;
    }
}