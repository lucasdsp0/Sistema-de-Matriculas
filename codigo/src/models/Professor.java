package src.models;

import java.util.List;

public class Professor extends Usuario {
    private long numCadastro;

    public Professor(String nome, String login, String senha, long numCadastro) {
        super(nome, login, senha);
        this.numCadastro = numCadastro;
    }

    public List<Aluno> visualizaAlunos(Disciplina disciplina) {
        return disciplina.getAlunos();
    }

    public long getNumCadastro() {
        return numCadastro;
    }

    public void setNumCadastro(long numCadastro) {
        this.numCadastro = numCadastro;
    }
}
