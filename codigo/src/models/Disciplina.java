package src.models;

import java.util.ArrayList;
import java.util.List;
import src.models.enums.StatusDisciplina;

public class Disciplina {

    private String nome;
    private boolean obrigatorio;
    private StatusDisciplina status;
    private int numMaxAlunos = 60;
    private int numMinAlunos = 3;
    private Professor professor;
    private List<Aluno> alunos = new ArrayList<>();

    // Construtores
    public Disciplina() {
    }

    public Disciplina(String nome, boolean obrigatorio, StatusDisciplina status) {
        this.nome = nome;
        this.obrigatorio = obrigatorio;
        this.status = status;
    }

    // Métodos de negócio (stubs)

    public void encerrarPeriodoMatricula() {
        // TODO: encerrar o período de matrícula da disciplina
    }

    public void addAluno(Aluno aluno) {
        // TODO: adicionar aluno à disciplina
    }

    public void removeAluno(Aluno aluno) {
        // TODO: remover aluno da disciplina
    }

    // Getters e Setters

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public boolean isObrigatorio() {
        return obrigatorio;
    }

    public void setObrigatorio(boolean obrigatorio) {
        this.obrigatorio = obrigatorio;
    }

    public StatusDisciplina getStatus() {
        return status;
    }

    public void setStatus(StatusDisciplina status) {
        this.status = status;
    }

    public int getNumMaxAlunos() {
        return numMaxAlunos;
    }

    public void setNumMaxAlunos(int numMaxAlunos) {
        this.numMaxAlunos = numMaxAlunos;
    }

    public int getNumMinAlunos() {
        return numMinAlunos;
    }

    public void setNumMinAlunos(int numMinAlunos) {
        this.numMinAlunos = numMinAlunos;
    }

    public Professor getProfessor() {
        return professor;
    }

    public void setProfessor(Professor professor) {
        this.professor = professor;
    }

    public List<Aluno> getAlunos() {
        return alunos;
    }

    public void setAlunos(List<Aluno> alunos) {
        this.alunos = alunos;
    }
}
