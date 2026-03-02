package src.models;

import java.util.ArrayList;
import java.util.List;

public class Curso {

    private String nome;
    private int creditos;
    private List<Disciplina> disciplinas = new ArrayList<>();

    // Construtores
    public Curso() {
    }

    public Curso(String nome, int creditos) {
        this.nome = nome;
        this.creditos = creditos;
    }

    // Métodos de negócio (stubs)

    public void addDisciplina(Disciplina disciplina) {
        // TODO: adicionar disciplina ao curso
    }

    public void removeDisciplina(Disciplina disciplina) {
        // TODO: remover disciplina do curso
    }

    // Getters e Setters

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getCreditos() {
        return creditos;
    }

    public void setCreditos(int creditos) {
        this.creditos = creditos;
    }

    public List<Disciplina> getDisciplinas() {
        return disciplinas;
    }

    public void setDisciplinas(List<Disciplina> disciplinas) {
        this.disciplinas = disciplinas;
    }
}
