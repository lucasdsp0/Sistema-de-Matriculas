package com.matriculas.model;

import com.matriculas.model.enums.StatusMatricula;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Representa o vínculo de um Aluno com um conjunto de Disciplinas num semestre.
 * Uma Matrícula pode ser efetivada ou cancelada.
 */
@Entity
@Table(name = "matriculas")
public class Matricula {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusMatricula status;

    @Column(nullable = false)
    private LocalDateTime dataMatricula;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "aluno_id", nullable = false)
    private Aluno aluno;

    @ManyToMany
    @JoinTable(name = "matricula_disciplina", joinColumns = @JoinColumn(name = "matricula_id"), inverseJoinColumns = @JoinColumn(name = "disciplina_id"))
    private List<Disciplina> disciplinas = new ArrayList<>();

    // ------ Construtores ------

    public Matricula() {
    }

    public Matricula(Aluno aluno, StatusMatricula status) {
        this.aluno = aluno;
        this.status = status;
        this.dataMatricula = LocalDateTime.now();
    }

    // ------ Método de negócio ------

    /**
     * Simula a notificação ao sistema de cobrança após efetivação ou cancelamento.
     */
    public void notificarCobranca() {
        System.out.println("[SistemaCobranca] Matrícula ID=" + id
                + " do aluno " + (aluno != null ? aluno.getNome() : "?")
                + " atualizada para status: " + status);
    }

    // ------ Getters e Setters ------

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public StatusMatricula getStatus() {
        return status;
    }

    public void setStatus(StatusMatricula status) {
        this.status = status;
    }

    public LocalDateTime getDataMatricula() {
        return dataMatricula;
    }

    public void setDataMatricula(LocalDateTime dataMatricula) {
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
