package src;

import java.util.List;
import src.models.Aluno;
import src.models.Curriculo;
import src.models.Disciplina;
import src.models.Professor;
import src.models.Secretaria;
import src.models.enums.StatusDisciplina;

public class App {
    public static void main(String[] args) {
        System.out.println("Iniciando Sistema de Matrículas...");

        // Secretaria gera o currículo
        Secretaria secretaria = new Secretaria("Joana", "joana.sec", "senha123");
        System.out.println("\n--- Ações da Secretaria ---");
        Curriculo curriculo = secretaria.geraCurriculo();

        // Criando disciplinas
        Disciplina d1 = new Disciplina("Cálculo 1", true, StatusDisciplina.ATIVA);
        Disciplina d2 = new Disciplina("Programação Orientada a Objetos", true, StatusDisciplina.ATIVA);
        d2.setNumMaxAlunos(2); // Definindo um número baixo para teste
        Disciplina d3 = new Disciplina("Física Quântica", false, StatusDisciplina.INATIVA);

        // Adicionando disciplinas ao currículo
        curriculo.addDisciplina(d1);
        curriculo.addDisciplina(d2);
        curriculo.addDisciplina(d3);
        System.out.println("Disciplinas adicionadas ao currículo.");

        // 2. Professor é associado a uma disciplina (História de Usuário 2)
        System.out.println("\n--- Ações do Professor ---");
        Professor professor = new Professor("Dr. Brown", "brown.prof", "senha456", 101L);
        d2.setProfessor(professor);
        System.out.println("Professor " + professor.getNome() + " associado à disciplina " + d2.getNome());

        // Aluno se matricula
        System.out.println("\n--- Ações do Aluno ---");
        Aluno aluno1 = new Aluno("Marty", "marty.alu", "senha789", 12345L);
        Aluno aluno2 = new Aluno("Jennifer", "jen.alu", "senhaabc", 12346L);
        Aluno aluno3 = new Aluno("Biff", "biff.alu", "senhabiff", 12347L);

        // Simulação de matrícula
        aluno1.realizarMatricula(d2);
        aluno2.realizarMatricula(d2);
        aluno3.realizarMatricula(d2); // Tentativa de matricular em disciplina cheia

        // Professor visualiza alunos matriculados
        System.out.println("\n--- Verificação do Professor ---");
        List<Aluno> alunosMatriculados = professor.visualizaAlunos(d2);
        System.out.println("Professor " + professor.getNome() + " visualizando alunos em " + d2.getNome() + ":");
        for (Aluno aluno : alunosMatriculados) {
            System.out.println("- " + aluno.getNome());
        }

        // Login de usuário
        System.out.println("\n--- Teste de Login ---");
        System.out.println("Testando login para " + aluno1.getNome() + " com credenciais corretas...");
        boolean loginSucesso = aluno1.doLogin("marty.alu", "senha789");
        System.out.println("Login bem-sucedido: " + loginSucesso);

        System.out.println("Testando login para " + professor.getNome() + " com senha incorreta...");
        boolean loginFalha = professor.doLogin("brown.prof", "senhaerrada");
        System.out.println("Login bem-sucedido: " + loginFalha);

        System.out.println("\n...Fim da simulação.");
    }
}
