package com.matriculas.model;

import com.matriculas.model.enums.StatusDisciplina;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Representa uma disciplina oferecida pela universidade.
 * Possui limite de vagas, professor responsável e status de oferta.
 */
@Entity
@Table(name = "disciplinas")
public class Disciplina {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    private boolean obrigatorio;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusDisciplina status;

    private int numMaxAlunos = 60;
    private int numMinAlunos = 3;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "professor_id")
    private Professor professor;

    // Alunos que estão matriculados nesta disciplina (via Matricula)
    @ManyToMany(mappedBy = "disciplinas", fetch = FetchType.LAZY)
    private List<Matricula> matriculas = new ArrayList<>();

    // ------ Construtores ------

    public Disciplina() {
    }

    public Disciplina(String nome, boolean obrigatorio, StatusDisciplina status) {
        this.nome = nome;
        this.obrigatorio = obrigatorio;
        this.status = status;
    }

    // ------ Métodos de negócio ------

    /**
     * Retorna a quantidade de alunos efetivados na disciplina.
     */
    public int getNumAlunosMatriculados() {
        return (int) matriculas.stream()
                .filter(m -> m.getStatus() == com.matriculas.model.enums.StatusMatricula.EFETIVADA)
                .count();
    }

    /**
     * Verifica se ainda há vagas disponíveis.
     */
    public boolean temVagas() {
        return getNumAlunosMatriculados() < numMaxAlunos;
    }

    /**
     * Retorna os alunos efetivamente matriculados nesta disciplina.
     */
    public List<Aluno> getAlunosMatriculados() {
        List<Aluno> alunos = new ArrayList<>();
        for (Matricula m : matriculas) {
            if (m.getStatus() == com.matriculas.model.enums.StatusMatricula.EFETIVADA) {
                alunos.add(m.getAluno());
            }
        }
        return alunos;
    }

    // ------ Getters e Setters ------

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public List<Matricula> getMatriculas() {
        return matriculas;
    }

    public void setMatriculas(List<Matricula> matriculas) {
        this.matriculas = matriculas;
    }
}
