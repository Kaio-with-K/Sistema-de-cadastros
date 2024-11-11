package br.org.clavedesol.CrudJava.controller;

public class UpdateOficineiroDto {
    private String nomeOficineiro;
    private int idadeOficineiro;
    private int tempoEmpresa;
    private String fotoOficineiro;


    // Getters and Setters
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
