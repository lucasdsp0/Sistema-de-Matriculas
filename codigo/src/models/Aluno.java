package src.models;

import java.util.ArrayList;
import java.util.List;

public class Aluno extends Usuario {
    private long numMatricula;
    private List<Matricula> matriculas = new ArrayList<>();

    public void realizarMatricula(Disciplina disciplina) {

    }

    public void cancelarMatricula(Disciplina disciplina) {

    }

    public boolean verificarRegrasOpcoes() {

        return false;
    }

    public long getNumMatricula() {
        return numMatricula;
    }

    public void setNumMatricula(long numMatricula) {
        this.numMatricula = numMatricula;
    }

    public List<Matricula> getMatriculas() {
        return matriculas;
    }

    public void setMatriculas(List<Matricula> matriculas) {
        this.matriculas = matriculas;
    }
}