package com.matriculas.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Representa o currículo de um semestre, contendo as disciplinas ofertadas.
 * Gerado pela Secretaria.
 */
@Entity
@Table(name = "curriculos")
public class Curriculo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDate dataInicio;

    private LocalDate dataFim;

    @Column
    private String semestre; // ex: "2024/1"

    @ManyToMany
    @JoinTable(
        name = "curriculo_disciplina",
        joinColumns = @JoinColumn(name = "curriculo_id"),
        inverseJoinColumns = @JoinColumn(name = "disciplina_id")
    )
    private List<Disciplina> disciplinas = new ArrayList<>();

    // ------ Construtores ------

    public Curriculo() {}

    public Curriculo(String semestre, LocalDate dataInicio) {
        this.semestre = semestre;
        this.dataInicio = dataInicio;
    }

    // ------ Métodos de negócio ------

    public void addDisciplina(Disciplina disciplina) {
        this.disciplinas.add(disciplina);
    }

    public void removeDisciplina(Disciplina disciplina) {
        this.disciplinas.remove(disciplina);
    }

    // ------ Getters e Setters ------

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public LocalDate getDataInicio() { return dataInicio; }
    public void setDataInicio(LocalDate dataInicio) { this.dataInicio = dataInicio; }

    public LocalDate getDataFim() { return dataFim; }
    public void setDataFim(LocalDate dataFim) { this.dataFim = dataFim; }

    public String getSemestre() { return semestre; }
    public void setSemestre(String semestre) { this.semestre = semestre; }

    public List<Disciplina> getDisciplinas() { return disciplinas; }
    public void setDisciplinas(List<Disciplina> disciplinas) { this.disciplinas = disciplinas; }
}
