package com.matriculas.model.viewmodel;

public class CurriculoViewModel {

    private String semestre;
    private String dataInicioFormatted;
    private String dataFimFormatted;
    private int disciplinasCount;

    public CurriculoViewModel(String semestre, String dataInicioFormatted, String dataFimFormatted, int disciplinasCount) {
        this.semestre = semestre;
        this.dataInicioFormatted = dataInicioFormatted;
        this.dataFimFormatted = dataFimFormatted;
        this.disciplinasCount = disciplinasCount;
    }

    // Getters
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
