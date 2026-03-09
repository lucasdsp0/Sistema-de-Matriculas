package com.matriculas.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Representa um aluno da universidade.
 * Possui número de matrícula único e pode se inscrever em disciplinas via Matricula.
 */
@Entity
@Table(name = "alunos")
public class Aluno extends Usuario {

    @Column(nullable = false, unique = true)
    private Long numMatricula;

    @OneToMany(mappedBy = "aluno", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Matricula> matriculas = new ArrayList<>();

    // ------ Construtores ------

    public Aluno() {}

    public Aluno(String nome, String login, String senha, Long numMatricula) {
        super(nome, login, senha);
        this.numMatricula = numMatricula;
    }

    // ------ Getters e Setters ------

    public Long getNumMatricula() {
        return numMatricula;
    }

    public void setNumMatricula(Long numMatricula) {
        this.numMatricula = numMatricula;
    }

    public List<Matricula> getMatriculas() {
        return matriculas;
    }

    public void setMatriculas(List<Matricula> matriculas) {
        this.matriculas = matriculas;
    }
}
