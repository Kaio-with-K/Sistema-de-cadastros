package br.org.clavedesol.CrudJava.controller;

import br.org.clavedesol.CrudJava.entity.Instrumento;

import java.util.UUID;

public class InstrumentoDTO {
    private UUID idInstrumento;
    private String nomeInstrumento;
    private int tempoAprendizado;
    private String fotoInstrumento;
    private OficineiroDTO oficineiro;

    // Construtor corrigido
    public InstrumentoDTO(Instrumento instrumento) {
        this.idInstrumento = instrumento.getIdInstrumento();
        this.nomeInstrumento = instrumento.getNomeInstrumento();
        this.tempoAprendizado = instrumento.getTempoAprendizado();
        this.fotoInstrumento = instrumento.getFotoInstrumento();
        this.oficineiro = new OficineiroDTO(instrumento.getOficineiro());  // Atribuindo o OficineiroDTO corretamente
    }

    // Métodos getters e setters

    public UUID getIdInstrumento() {
        return idInstrumento;
    }

    public void setIdInstrumento(UUID idInstrumento) {
        this.idInstrumento = idInstrumento;
    }

    public String getNomeInstrumento() {
        return nomeInstrumento;
    }

    public void setNomeInstrumento(String nomeInstrumento) {
        this.nomeInstrumento = nomeInstrumento;
    }

    public int getTempoAprendizado() {
        return tempoAprendizado;
    }

    public void setTempoAprendizado(int tempoAprendizado) {
        this.tempoAprendizado = tempoAprendizado;
    }

    public String getFotoInstrumento() {
        return fotoInstrumento;
    }

    public void setFotoInstrumento(String fotoInstrumento) {
        this.fotoInstrumento = fotoInstrumento;
    }

    public OficineiroDTO getOficineiro() {
        return oficineiro;
    }

    public void setOficineiro(OficineiroDTO oficineiro) {
        this.oficineiro = oficineiro;
    }
}
