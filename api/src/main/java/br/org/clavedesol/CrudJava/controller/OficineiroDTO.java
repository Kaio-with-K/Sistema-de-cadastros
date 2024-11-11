package br.org.clavedesol.CrudJava.controller;

import br.org.clavedesol.CrudJava.entity.Oficineiro;

import java.util.UUID;

public class OficineiroDTO {
    private UUID idOficineiro;
    private String nomeOficineiro;
    private int idadeOficineiro;
    private int tempoEmpresa;
    private String fotoOficineiro;

    // Construtor atualizado para não precisar de lista de InstrumentoDTO
    public OficineiroDTO(Oficineiro oficineiro) {
        this.idOficineiro = oficineiro.getIdOficineiro();
        this.nomeOficineiro = oficineiro.getNomeOficineiro();
        this.idadeOficineiro = oficineiro.getIdadeOficineiro();
        this.tempoEmpresa = oficineiro.getTempoEmpresa();
        this.fotoOficineiro = oficineiro.getFotoOficineiro();
    }

    // Getters e Setters
    public UUID getIdOficineiro() {
        return idOficineiro;
    }

    public void setIdOficineiro(UUID idOficineiro) {
        this.idOficineiro = idOficineiro;
    }

    public String getNomeOficineiro() {
        return nomeOficineiro;
    }

    public void setNomeOficineiro(String nomeOficineiro) {
        this.nomeOficineiro = nomeOficineiro;
    }

    public int getIdadeOficineiro() {
        return idadeOficineiro;
    }

    public void setIdadeOficineiro(int idadeOficineiro) {
        this.idadeOficineiro = idadeOficineiro;
    }

    public int getTempoEmpresa() {
        return tempoEmpresa;
    }

    public void setTempoEmpresa(int tempoEmpresa) {
        this.tempoEmpresa = tempoEmpresa;
    }

    public String getFotoOficineiro() {
        return fotoOficineiro;
    }

    public void setFotoOficineiro(String fotoOficineiro) {
        this.fotoOficineiro = fotoOficineiro;
    }
}
