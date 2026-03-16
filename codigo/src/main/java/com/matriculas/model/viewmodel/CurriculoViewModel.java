package com.matriculas.model.viewmodel;

public class CurriculoViewModel {

    private Long id;
    private String semestre;
    private String dataInicioFormatted;
    private String dataFimFormatted;
    private int disciplinasCount;

    public CurriculoViewModel(Long id, String semestre, String dataInicioFormatted, String dataFimFormatted, int disciplinasCount) {
        this.id = id;
        this.semestre = semestre;
        this.dataInicioFormatted = dataInicioFormatted;
        this.dataFimFormatted = dataFimFormatted;
        this.disciplinasCount = disciplinasCount;
    }

    // Getters
    public Long getId() {
        return id;
    }

    public String getSemestre() {
        return semestre;
    }

    public String getDataInicioFormatted() {
        return dataInicioFormatted;
    }

    public String getDataFimFormatted() {
        return dataFimFormatted;
    }

    public int getDisciplinasCount() {
        return disciplinasCount;
    }
}
