package src.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import src.models.enums.StatusMatricula;

public class Matricula {

    private StatusMatricula status;
    private Date dataMatricula;
    private Aluno aluno;
    private List<Disciplina> disciplinas = new ArrayList<>();

    // Construtores
    public Matricula() {
    }

    public Matricula(Aluno aluno, StatusMatricula status) {
        this.aluno = aluno;
        this.status = status;
        this.dataMatricula = new Date();
    }

    // Métodos de negócio (stubs)

    public void notificarCobranca() {
        // TODO: notificar o sistema de cobrança sobre a matrícula
    }

    // Getters e Setters

    public StatusMatricula getStatus() {
        return status;
    }

    public void setStatus(StatusMatricula status) {
        this.status = status;
    }

    public Date getDataMatricula() {
        return dataMatricula;
    }

    public void setDataMatricula(Date dataMatricula) {
        this.dataMatricula = dataMatricula;
    }

    public Aluno getAluno() {
        return aluno;
    }

    public void setAluno(Aluno aluno) {
        this.aluno = aluno;
    }

    public List<Disciplina> getDisciplinas() {
        return disciplinas;
    }

    public void setDisciplinas(List<Disciplina> disciplinas) {
        this.disciplinas = disciplinas;
    }
}
