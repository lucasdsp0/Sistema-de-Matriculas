package com.matriculas.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Representa um professor da universidade.
 * Pode ser associado a disciplinas e visualizar alunos matriculados.
 */
@Entity
@Table(name = "professores")
public class Professor extends Usuario {

    @Column(nullable = false, unique = true)
    private Long numCadastro;

    @OneToMany(mappedBy = "professor", fetch = FetchType.LAZY)
    private List<Disciplina> disciplinas = new ArrayList<>();

    // ------ Construtores ------

    public Professor() {
    }

    public Professor(String nome, String login, String senha, Long numCadastro) {
        super(nome, login, senha);
        this.numCadastro = numCadastro;
    }

    // ------ Método de negócio ------

    /**
     * Retorna a lista de alunos matriculados em uma disciplina.
     */
    public List<Aluno> visualizaAlunos(Disciplina disciplina) {
        return disciplina.getAlunosMatriculados();
    }

    // ------ Getters e Setters ------

    public Long getNumCadastro() {
        return numCadastro;
    }

    public void setNumCadastro(Long numCadastro) {
        this.numCadastro = numCadastro;
    }

    public List<Disciplina> getDisciplinas() {
        return disciplinas;
    }

    public void setDisciplinas(List<Disciplina> disciplinas) {
        this.disciplinas = disciplinas;
    }
}
